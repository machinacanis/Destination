package group.togawa.destination.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class Command {

    private static final String COMMAND_NAME = "destination";

    public static void register(
        CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        // 注册根命令
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal(
            COMMAND_NAME
        ).requires((source -> source.hasPermission(2))); // 默认需要OP

        // 这里注册子命令
        command.then(AddPerkCommand.get());
        command.then(RemovePerkCommand.get());

        dispatcher.register(command);
    }
}
