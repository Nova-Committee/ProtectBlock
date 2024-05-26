package cn.evole.mods.pb.common.tile;

import cn.evole.mods.pb.api.security.ISecurity;
import cn.evole.mods.pb.api.security.SecurityProfile;
import cn.evole.mods.pb.api.tile.ModBaseTile;
import net.minecraft.nbt.NBTTagCompound;

/**
 * TileEntityModBase
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/19 下午8:43
 */
public class SecurityTile extends ModBaseTile implements ISecurity{
    public boolean enable;
    public SecurityProfile securityProfile;

    public SecurityTile() {
        super();
        enable = true;
        securityProfile= new SecurityProfile();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        securityProfile.readFromNBT(nbt);

        enable = nbt.getBoolean("enable");
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        securityProfile.writeToNBT(nbt);
        nbt.setBoolean("enable", enable);
        return super.writeToNBT(nbt);
    }

    @Override
    public SecurityProfile getSecurityProfile() {
        return securityProfile;
    }
}
