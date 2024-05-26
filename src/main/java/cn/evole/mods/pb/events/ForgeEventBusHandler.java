package cn.evole.mods.pb.events;

import cn.evole.mods.pb.api.security.ISecurity;
import cn.evole.mods.pb.init.ModRegistries;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * ForgeEventBusHandler
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/19 下午8:40
 */
@Mod.EventBusSubscriber
public class ForgeEventBusHandler {
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {

        TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());

        if (event.getEntity() instanceof EntityPlayer && tileEntity instanceof ISecurity) {

            ISecurity security = (ISecurity) tileEntity;

            security.getSecurityProfile().setOwner((EntityPlayer) event.getEntity());
            tileEntity.markDirty();
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.getWorld();
        EntityPlayer player = event.getPlayer();
        Block breakBlock = event.getState().getBlock();
        int x = event.getPos().getX();
        int z = event.getPos().getZ();
        int y = event.getPos().getY();
        boolean flag = false;

        if (breakBlock == ModRegistries.allowBlock || breakBlock == ModRegistries.denyBlock){
            if (!player.isCreative()) flag = true;
        } else if (breakBlock == ModRegistries.playerAllowBlock || breakBlock == ModRegistries.playerDenyBlock){
            TileEntity tileEntity = world.getTileEntity(event.getPos());
            if (tileEntity instanceof ISecurity) {
                ISecurity security = (ISecurity) tileEntity;
                if (security.getSecurityProfile().hasOwner() && !security.getSecurityProfile().isOwner(player.getGameProfile().getName())) flag = true;
            }
        } else {
            for (int i = 0; i < y; i++){
                BlockPos pos = new BlockPos(x, i, z);
                Block eachBock = world.getBlockState(pos).getBlock();
                TileEntity tileEntity = world.getTileEntity(pos);
                if (eachBock.isAssociatedBlock(ModRegistries.playerDenyBlock)){
                    if (tileEntity instanceof ISecurity) {
                        ISecurity security = (ISecurity) tileEntity;
                        if (player.isCreative()) {
                            flag = false;
                        } else if (security.getSecurityProfile().hasOwner() && !security.getSecurityProfile().isOwner(player.getGameProfile().getName())) {
                            flag = isAllowBlock(i, y , x, z, world, player);
                        }
                    }
                } else if (eachBock.isAssociatedBlock(ModRegistries.denyBlock)){
                    flag = isAllowBlock(i, y , x, z, world, player);

                }
            }

        }
        event.setCanceled(flag);
    }

    private static boolean isAllowBlock(int start, int end, int posX, int posZ, World world, EntityPlayer player){
        boolean flag = true;
        for (int j = start + 1; j < end; j++) {
            BlockPos pos2 = new BlockPos(posX, j, posZ);
            Block eachBock2 = world.getBlockState(pos2).getBlock();
            TileEntity tileEntity2 = world.getTileEntity(pos2);
            if (eachBock2.isAssociatedBlock(ModRegistries.playerAllowBlock)){
                if (tileEntity2 instanceof ISecurity) {
                    ISecurity security2 = (ISecurity) tileEntity2;
                    if (player.isCreative()) {
                        flag = false;
                    } else if (security2.getSecurityProfile().hasOwner() && !security2.getSecurityProfile().isOwner(player.getGameProfile().getName())) {
                        flag = false;
                    }
                }
            } else if (world.getBlockState(pos2).getBlock().isAssociatedBlock(ModRegistries.allowBlock)){
                flag = false;
            }
        }
        return flag;
    }

}
