package com.auri.ironam;

import com.auri.ironam.BlockBase.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by 1800855 on 10/20/16.
 */
public class ModBlocks {

    public static BlockBase blockEcto;

    public static void init()
    {
        blockEcto = register(new BlockBase(Material.GROUND, "blockEcto").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
    }

    private static <T extends Block> T register (T block, ItemBlock itemBlock)
    {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);

        if (block instanceof BlockBase)
        {
            ((BlockBase)block).registerItemModel(itemBlock);
        }

        return block;
    }

    private static <T extends Block> T register(T block)
    {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }
}
