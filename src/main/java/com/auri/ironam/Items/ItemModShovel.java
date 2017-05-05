package com.auri.ironam.Items;

import net.minecraft.item.ItemSpade;

/**
 * Created by 1800855 on 4/6/17.
 */
public class ItemModShovel extends ItemSpade {
    public ItemModShovel(String unlocalName, ToolMaterial material) {
        super(material);
        this.setUnlocalizedName(unlocalName);
        this.setRegistryName(unlocalName);
    }
}
