package cn.evole.mods.pb.common.block;

import cn.evole.mods.pb.common.tile.SecurityTile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * AllowBlock
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/19 下午8:09
 */
public class BaseTileBlock extends BaseBlock {
    public BaseTileBlock(String name) {
        super(name);
        setHarvestLevel("pickaxe", 2);
        setHardness(3.0F);
    }


    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new SecurityTile();
    }
}