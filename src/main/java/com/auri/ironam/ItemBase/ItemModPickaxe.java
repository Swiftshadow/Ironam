package com.auri.ironam.ItemBase;

import net.minecraft.item.ItemPickaxe;

/**
 * Created by 1800855 on 4/6/17.
 */
public class ItemModPickaxe extends ItemPickaxe {
    public ItemModPickaxe(String unlocalName, ToolMaterial material) {
        super(material);
        this.setUnlocalizedName(unlocalName);
        this.setRegistryName(unlocalName);
    }
}
