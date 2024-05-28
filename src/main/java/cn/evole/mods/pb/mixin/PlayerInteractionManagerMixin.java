package cn.evole.mods.pb.mixin;

import cn.evole.mods.pb.events.ForgeEventBusHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * InteractionManagerMixin
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/27 下午11:34
 */
@Mixin(PlayerInteractionManager.class)
public abstract class PlayerInteractionManagerMixin {


    @Shadow public EntityPlayerMP player;

    @Inject(method = "onBlockClicked", at = @At(
            value = "HEAD"
    ), cancellable = true)
    public void onPlayerMove(BlockPos pos, EnumFacing side, CallbackInfo ci) {
        //if (ForgeEventBusHandler.onBlockBreak(player, pos)) ci.cancel();
    }

}
