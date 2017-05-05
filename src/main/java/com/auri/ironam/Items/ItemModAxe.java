package com.auri.ironam.Items;

import net.minecraft.item.ItemAxe;

/**
 * Created by 1800855 on 4/6/17.
 */
public class ItemModAxe extends ItemAxe {
    public ItemModAxe(String unlocalName, ToolMaterial material, float dam, float spd) {
        super(material, dam, spd);
        this.setUnlocalizedName(unlocalName);
        this.setRegistryName(unlocalName);
    }
}
