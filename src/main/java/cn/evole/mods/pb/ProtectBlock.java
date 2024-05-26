package cn.evole.mods.pb;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
        name = Const.MOD_NAME,
        modid = Const.MOD_ID,
        acceptedMinecraftVersions = "[1.12,]",
        useMetadata = true
        //dependencies = Const.DEPENDENCIES
)
public class ProtectBlock {

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {

    }
}
