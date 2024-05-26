package cn.evole.mods.pb.common.block;

import cn.evole.mods.pb.Const;
import cn.evole.mods.pb.init.ModRegistries;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * BaseBlock
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/26 下午5:22
 */
public class BaseBlock extends Block {
    public BaseBlock(String name) {
        super(Material.ANVIL);
        setTranslationKey("pb."+ name);
        setRegistryName(Const.rl(name));
        setCreativeTab(ModRegistries.tab);
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

}
