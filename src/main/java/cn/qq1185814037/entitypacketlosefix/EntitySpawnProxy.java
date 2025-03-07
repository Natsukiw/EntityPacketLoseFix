package cn.qq1185814037.entitypacketlosefix;

import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.registry.EntityRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Method;

public class EntitySpawnProxy {

    public static Method toBytes;
    public static Packet errorPacket;

    public static void init()
    {
        try
        {
            toBytes = FMLMessage.EntitySpawnMessage.class.getDeclaredMethod("toBytes", ByteBuf.class);
            toBytes.setAccessible(true);
        } catch (NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }

        errorPacket = new Packet() {
            @Override
            public void readPacketData(PacketBuffer data) {}

            @Override
            public void writePacketData(PacketBuffer data) {}

            @Override
            public void processPacket(INetHandler handler) {}
        };
    }

    public static Packet getEntitySpawningPacket(Entity entity)
    {
        final EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
        if (er == null) return null;
        if (er.usesVanillaSpawning()) return null;
        final FMLMessage.EntitySpawnMessage message = new FMLMessage.EntitySpawnMessage(er, entity, er.getContainer());
        final ByteBuf buf = Unpooled.buffer();
        buf.writeByte(2);//数据包鉴别器, 2 = FMLMessage.EntitySpawnMessage.class
        try
        {
            toBytes.invoke(message, buf);
        } catch (Throwable e)
        {
            EntityPacketLoseFix.LOG.log(Level.WARN, "Packet writing error, an error packet has been returned, and this packet will not be sent to the player", e);
            return errorPacket;
        }
        return new FMLProxyPacket(buf, "FML");
    }

}
