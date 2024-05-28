package cn.evole.mods.pb.mixin;

import cn.evole.mods.pb.events.ForgeEventBusHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * InteractionManagerMixin
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/27 下午11:34
 */
@Mixin(PlayerControllerMP.class)
public abstract class PlayerControllerMPMixin {


    @Shadow @Final private Minecraft mc;

    @Inject(method = "clickBlock", at = @At(
            value = "HEAD"
    ))
    public void onPlayerMove(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        if (ForgeEventBusHandler.onBlockBreak(mc.player, posBlock)) cir.cancel();
    }

}
