package cn.evole.mods.pb.api.security;

import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * SecurityProfile
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/19 下午8:37
 */
@Getter
public class SecurityProfile {
    private String ownerName = "";

    public void setOwner (EntityPlayer player) {
        ownerName = player.getName();
    }

    public boolean hasOwner () {
        return ownerName.isEmpty();
    }

    public boolean isOwner (String ownerName) {
        return this.ownerName.equalsIgnoreCase(ownerName);
    }

    public void readFromNBT (NBTTagCompound nbt) {

        if (!nbt.getString("ownerName").isEmpty()) {
            ownerName = nbt.getString("ownerName");
        }
    }

    public void writeToNBT (NBTTagCompound nbt) {

        if (!ownerName.isEmpty()) {
            nbt.setString("ownerName", ownerName);
        }
    }
}
