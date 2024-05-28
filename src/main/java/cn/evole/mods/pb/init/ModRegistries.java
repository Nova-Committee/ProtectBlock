package cn.evole.mods.pb.init;

import cn.evole.mods.pb.Const;
import cn.evole.mods.pb.common.block.BaseBlock;
import cn.evole.mods.pb.common.block.BaseTileBlock;
import cn.evole.mods.pb.common.tile.SecurityTile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * ModBlocks
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/5/26 下午3:33
 */
@Mod.EventBusSubscriber(modid = Const.MOD_ID)
public class ModRegistries {

    public static Block denyBlock;
    public static Block allowBlock;
    public static Block playerDenyBlock;
    public static Block playerAllowBlock;
    public static Item denyBlockItem;
    public static Item allowBlockItem;
    public static Item denyBlockItemItem;
    public static Item playerAllowBlockItem;

    public static CreativeTabs tab = new CreativeTabs("pb") {
        @Override
        public ItemStack createIcon() {
            return denyBlockItem.getDefaultInstance();
        }
    };
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {

        event.getRegistry().registerAll(
                denyBlock = new BaseBlock("deny").setHardness(6000000.0F),
                allowBlock = new BaseBlock("allow").setHardness(6000000.0F),
                playerDenyBlock = new BaseTileBlock("player_deny"),
                playerAllowBlock = new BaseTileBlock("player_allow")

        );
        GameRegistry.registerTileEntity(SecurityTile.class, Const.rl("security"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {

        event.getRegistry().registerAll(
                denyBlockItem = new ItemBlock(denyBlock).setRegistryName(Const.rl("deny")),
                allowBlockItem = new ItemBlock(allowBlock).setRegistryName(Const.rl("allow")),
                denyBlockItemItem = new ItemBlock(playerDenyBlock).setRegistryName(Const.rl("player_deny")),
                playerAllowBlockItem = new ItemBlock(playerAllowBlock).setRegistryName(Const.rl("player_allow"))
        );
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // 因为一些奇怪的原因，原版不会主动去搜索 Mod 的物品的模型的位置。我们需要手动指定一个。
        ModelLoader.setCustomModelResourceLocation(denyBlockItem, 0, new ModelResourceLocation(Const.rl("deny"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(allowBlockItem, 0, new ModelResourceLocation(Const.rl("allow"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(denyBlockItemItem, 0, new ModelResourceLocation(Const.rl("player_deny"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(playerAllowBlockItem, 0, new ModelResourceLocation(Const.rl("player_allow"), "inventory"));
    }
}
