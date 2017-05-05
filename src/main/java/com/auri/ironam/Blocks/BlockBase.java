package com.auri.ironam.Blocks;

import com.auri.ironam.ironam;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

/**
 * Created by 1800855 on 10/18/16.
 */
public class BlockBase extends Block {
    protected  String name;

    public BlockBase(Material material, String name)
    {
        super(material);

        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public void registerItemModel(ItemBlock itemBlock)
    {
        ironam.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }
}
