package group.togawa.destination.registry;

import group.togawa.destination.command.Command;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class CommandRegistry {

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher());
    }
}
