package cn.evole.mods.pb.events;

import cn.evole.mods.pb.api.security.ISecurity;
import cn.evole.mods.pb.init.ModRegistries;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
        World world = event.getWorld();
        Entity player1 = event.getEntity();
        Block placeBlock = world.getBlockState(event.getPos()).getBlock();
        int x = event.getPos().getX();
        int z = event.getPos().getZ();
        int y = event.getPos().getY();

        //if (world.isRemote) return;
        if (player1 instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) player1;
            if (placeBlock.isAssociatedBlock(ModRegistries.playerDenyBlock) || placeBlock.isAssociatedBlock(ModRegistries.playerAllowBlock)) {
                TileEntity tileEntity = world.getTileEntity(event.getPos());
                if (tileEntity instanceof ISecurity) {
                    ISecurity security = (ISecurity) tileEntity;
                    security.getSecurityProfile().setOwner(player);
                    tileEntity.markDirty();
                }
            } else {
                for (int i = 0; i < y; i++) {
                    BlockPos pos = new BlockPos(x, i, z);
                    Block eachBock = world.getBlockState(pos).getBlock();
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if (eachBock.isAssociatedBlock(ModRegistries.playerDenyBlock)) {
                        if (tileEntity instanceof ISecurity) {
                            ISecurity security = (ISecurity) tileEntity;
                            if (player.isCreative()) {
                                return;
                            } else if (security.getSecurityProfile().hasOwner() && !security.getSecurityProfile().isOwner(player.getGameProfile().getName())) {
                                if (isAllowBlock(i, y, x, z, world, player)) {
                                    event.setCanceled(true);
                                }
                            }
                        }
                    } else if (eachBock.isAssociatedBlock(ModRegistries.denyBlock)) {
                        if (player.isCreative()) {
                            return;
                        } else {
                            if (isAllowBlock(i, y, x, z, world, player)) {
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreakEvent(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        EntityPlayer player1 = event.getEntityPlayer();
        Block breakBlock = world.getBlockState(event.getPos()).getBlock();
        int x = event.getPos().getX();
        int z = event.getPos().getZ();
        int y = event.getPos().getY();

        if (player1 instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) player1;
            if (player.interactionManager.getGameType().hasLimitedInteractions()) event.setCanceled(true);
            if (breakBlock == ModRegistries.allowBlock || breakBlock == ModRegistries.denyBlock) {
                if (!player.isCreative()) event.setCanceled(true);
            } else if (breakBlock == ModRegistries.playerAllowBlock || breakBlock == ModRegistries.playerDenyBlock) {
                TileEntity tileEntity = world.getTileEntity(event.getPos());
                if (tileEntity instanceof ISecurity) {
                    ISecurity security = (ISecurity) tileEntity;
                    if (player.isCreative()) {
                        return;
                    } else if (security.getSecurityProfile().hasOwner() && !security.getSecurityProfile().isOwner(player.getGameProfile().getName())) {
                        event.setCanceled(true);
                    }
                }
            } else {
                for (int i = 0; i < y; i++) {
                    BlockPos pos1 = new BlockPos(x, i, z);
                    Block eachBock = world.getBlockState(pos1).getBlock();
                    TileEntity tileEntity = world.getTileEntity(pos1);
                    if (eachBock.isAssociatedBlock(ModRegistries.playerDenyBlock)) {
                        if (tileEntity instanceof ISecurity) {
                            ISecurity security = (ISecurity) tileEntity;
                            if (player.isCreative()) {
                                return;
                            } else if (security.getSecurityProfile().hasOwner() && !security.getSecurityProfile().isOwner(player.getGameProfile().getName())) {
                                if (isAllowBlock(i, y, x, z, world, player)) {
                                    event.setCanceled(true);
                                }
                            }
                        }
                    } else if (eachBock.isAssociatedBlock(ModRegistries.denyBlock)) {
                        if (player.isCreative()) {
                            return;
                        } else {
                            if (isAllowBlock(i, y, x, z, world, player)) {
                                event.setCanceled(true);
                            }
                        }
                    }
                }

            }
        }
    }
    public static boolean onBlockBreak(EntityPlayer entity, BlockPos pos) {
        World world = entity.getEntityWorld();
        EntityPlayer player = entity;
        Block breakBlock = world.getBlockState(pos).getBlock();
        int x = pos.getX();
        int z = pos.getZ();
        int y = pos.getY();

        if (world.isRemote) return true;

            if (breakBlock == ModRegistries.allowBlock || breakBlock == ModRegistries.denyBlock){
                return !player.isCreative();
            } else if (breakBlock == ModRegistries.playerAllowBlock || breakBlock == ModRegistries.playerDenyBlock){
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof ISecurity) {
                    ISecurity security = (ISecurity) tileEntity;
                    if (player.isCreative()) {
                        return false;
                    } else if (security.getSecurityProfile().hasOwner() && !security.getSecurityProfile().isOwner(player.getGameProfile().getName())) {
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < y; i++){
                    BlockPos pos1 = new BlockPos(x, i, z);
                    Block eachBock = world.getBlockState(pos1).getBlock();
                    TileEntity tileEntity = world.getTileEntity(pos1);
                    if (eachBock.isAssociatedBlock(ModRegistries.playerDenyBlock)){
                        if (tileEntity instanceof ISecurity) {
                            ISecurity security = (ISecurity) tileEntity;
                            if (player.isCreative()) {
                                return false;
                            } else if (security.getSecurityProfile().hasOwner() && !security.getSecurityProfile().isOwner(player.getGameProfile().getName())) {
                                return isAllowBlock(i, y , x, z, world, player);
                            }
                        }
                    } else if (eachBock.isAssociatedBlock(ModRegistries.denyBlock)){
                        if (player.isCreative()) {
                            return false;
                        } else {
                            return isAllowBlock(i, y , x, z, world, player);
                        }
                    }
                }

            }


        return false;
    }

    private static boolean isAllowBlock(int start, int end, int posX, int posZ, World world, EntityPlayer player){
        for (int j = start; j < end; j++) {
            BlockPos pos2 = new BlockPos(posX, j, posZ);
            Block eachBock2 = world.getBlockState(pos2).getBlock();
            TileEntity tileEntity2 = world.getTileEntity(pos2);
            if (eachBock2.isAssociatedBlock(ModRegistries.playerAllowBlock)){
                if (tileEntity2 instanceof ISecurity) {
                    ISecurity security2 = (ISecurity) tileEntity2;
                    if (player.isCreative()) {
                        return false;
                    } else if (security2.getSecurityProfile().hasOwner() && !security2.getSecurityProfile().isOwner(player.getGameProfile().getName())) {
                        return false;
                    }
                }
            } else if (world.getBlockState(pos2).getBlock().isAssociatedBlock(ModRegistries.allowBlock)){
                return false;
            }
        }
        return true;
    }

}
