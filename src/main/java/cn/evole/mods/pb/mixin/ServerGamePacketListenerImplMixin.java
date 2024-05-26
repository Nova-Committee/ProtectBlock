package cn.evole.mods.pb.mixin;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.ITickable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * ServerGamePacketListenerImplMixin
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/26 下午11:51
 */
@Mixin(NetHandlerPlayServer.class)
public abstract class ServerGamePacketListenerImplMixin implements INetHandlerPlayServer, ITickable {


    @Inject(method = "processPlayer", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
            shift = At.Shift.AFTER
    ), cancellable = true)
    public void onPlayerMove(CPacketPlayer packetIn, CallbackInfo ci) {

    }
}
