package group.togawa.destination.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import group.togawa.destination.Destination;
import group.togawa.destination.api.AttachmentPerkDataAccessor;
import group.togawa.destination.api.GunPerkDataAccessor;
import group.togawa.destination.api.perk.PerkItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Command to remove a perk from the item (gun or attachment) held in the main hand of target entities.
 *
 * Usage: /removePerk <targets> <perk>
 */
public class RemovePerkCommand {

    private static final String NAME = "removePerk";
    private static final String ENTITY = "target";
    private static final String PERK = "perk";

    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        LiteralArgumentBuilder<CommandSourceStack> command =
            LiteralArgumentBuilder.literal(NAME);
        RequiredArgumentBuilder<CommandSourceStack, EntitySelector> entities =
            Commands.argument(ENTITY, EntityArgument.entities());
        RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> perk =
            Commands.argument(PERK, ResourceLocationArgument.id());

        command.then(entities.then(perk.executes(RemovePerkCommand::removePerk)));

        return command;
    }

    private static int removePerk(CommandContext<CommandSourceStack> context)
        throws CommandSyntaxException {
        var entities = EntityArgument.getEntities(context, ENTITY);
        int count = 0;
        ResourceLocation perk = ResourceLocationArgument.getId(context, PERK);
        if (perk.getNamespace().equals("minecraft")) {
            // 如果是minecraft的命名空间，改成tacz_destination
            perk = ResourceLocation.fromNamespaceAndPath(
                Destination.ID,
                perk.getPath()
            );
        }
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living) {
                ItemStack stack = living.getMainHandItem();
                if (stack.getItem() instanceof IGun) {
                    // 手持枪械：仅在该枪上存在该Perk时才执行移除并计数
                    PerkItem existing = GunPerkDataAccessor.getPerk(stack, perk);
                    if (existing != null) {
                        GunPerkDataAccessor.removePerk(stack, perk);
                        count++;
                    }
                } else if (stack.getItem() instanceof IAttachment) {
                    // 手持配件：仅在该配件上存在该Perk时才执行移除并计数
                    PerkItem existing = AttachmentPerkDataAccessor.getPerk(stack, perk);
                    if (existing != null) {
                        AttachmentPerkDataAccessor.removePerk(stack, perk);
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
