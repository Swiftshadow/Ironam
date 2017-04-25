package com.auri.ironam.ItemBase;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import com.auri.ironam.ironam;

/**
 * Created by 1800855 on 10/17/16.
 */
public class ItemBase extends Item {
    protected String name;

    public ItemBase (String name)
    {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }


    public void registerItemModel()
    {
        ironam.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemBase setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }
}
