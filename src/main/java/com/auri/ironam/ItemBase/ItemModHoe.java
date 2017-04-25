package com.auri.ironam.ItemBase;

import net.minecraft.item.ItemHoe;

/**
 * Created by 1800855 on 4/6/17.
 */
public class ItemModHoe extends ItemHoe {
    public ItemModHoe(String unlocalName, ToolMaterial material) {
        super(material);
        this.setUnlocalizedName(unlocalName);
        this.setRegistryName(unlocalName);
    }
}
