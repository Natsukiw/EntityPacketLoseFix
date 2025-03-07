package cn.qq1185814037.entitypacketlosefix;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Mod(modid = EntityPacketLoseFix.MODID, version = Tags.VERSION, name = "EntityPacketLoseFix", acceptedMinecraftVersions = "[1.7.10]")
public class EntityPacketLoseFix {

    public static final String MODID = "entitypacketlosefix";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        EntitySpawnProxy.init();
    }

    @NetworkCheckHandler
    public final boolean networkCheck(Map<String, String> remoteVersions, Side side)
    {
        if (side.isClient())
            return true;
        return remoteVersions.containsKey(MODID);
    }

}
