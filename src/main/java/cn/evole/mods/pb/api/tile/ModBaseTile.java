package cn.evole.mods.pb.api.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

/**
 * ModBaseTile
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/20 上午12:03
 */
public abstract class ModBaseTile extends TileEntity{
    public void markForUpdate () {
        if (world != null) {
            markDirty();
            IBlockState iblockstate = this.world.getBlockState(this.pos);
            world.addBlockEvent(getPos(), getBlockType(), 1, 1);
            world.notifyBlockUpdate(getPos(), iblockstate, iblockstate, 0);
            world.notifyNeighborsOfStateChange(getPos(), getBlockType(), false);
        }
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeToNBT(nbtTagCompound);
        int tileEntityType = 64;
        return new SPacketUpdateTileEntity(getPos(), tileEntityType, nbtTagCompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return nbt;
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return nbt;
    }
}
