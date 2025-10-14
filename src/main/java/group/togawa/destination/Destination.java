package group.togawa.destination;

import com.mojang.logging.LogUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Destination.ID)
public class Destination {

    public static final String ID = "tacz_destination";
    public static final String NAME = "Destination";
    public static final String VERSION = "1.0.0 SNAPSHOT";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Destination(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup); // 注册通用设置事件监听器
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // 初始化Destination时随机输出一点维护文本 :P
        List<String> messagesOnSetup = Arrays.asList(
            "The Light cannot save you. Seek us out on Minecraft.",
            "The final cakes are being placed. No peeking.",
            "Our end begins. Hope hides in a city set against the lava.",
            "We seek an end to The Ender Dragon. Why do you resist..."
        );

        Random rd = new Random();
        String rdMessage = messagesOnSetup.get(
            rd.nextInt(messagesOnSetup.size())
        );
        LOGGER.info(rdMessage);
        LOGGER.info("Thank you for using TaCZ: Destination!");
    }
}
