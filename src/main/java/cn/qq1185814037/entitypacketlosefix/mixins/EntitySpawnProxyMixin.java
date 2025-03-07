package cn.qq1185814037.entitypacketlosefix.mixins;

import cn.qq1185814037.entitypacketlosefix.EntitySpawnProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityTrackerEntry.class)
public abstract class EntitySpawnProxyMixin {

    @Redirect(
        method = "func_151260_c()Lnet/minecraft/network/Packet;",
        at = @At(
            value = "INVOKE",
            target = "Lcpw/mods/fml/common/network/internal/FMLNetworkHandler;getEntitySpawningPacket(Lnet/minecraft/entity/Entity;)Lnet/minecraft/network/Packet;",
            ordinal = 0))
    public Packet handleProxy(Entity entity)
    {
        return EntitySpawnProxy.getEntitySpawningPacket(entity);
    }

    //This is for old mixin mod
    //@Redirect(
    //    method = "func_73117_b(Lnet/minecraft/entity/player/EntityPlayerMP;)V",
    //    at = @At(
    //        value = "INVOKE",
    //        target = "Lnet/minecraft/network/NetHandlerPlayServer;func_147359_a(Lnet/minecraft/network/Packet;)V",
    //        ordinal = 0), remap = false)

    @Redirect(
        method = "tryStartWachingThis(Lnet/minecraft/entity/player/EntityPlayerMP;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/NetHandlerPlayServer;sendPacket(Lnet/minecraft/network/Packet;)V",
            ordinal = 0))
    public void handleSend(NetHandlerPlayServer instance, Packet packet)
    {
        //Do not send the error packet (不发送错误的数据包到客户端)
        if (packet != null && packet == EntitySpawnProxy.errorPacket) return;
        instance.sendPacket(packet);
    }

}
