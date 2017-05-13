package com.auri.ironam.Items;

import com.auri.ironam.ironam;
import net.minecraft.item.ItemAxe;

/**
 * Created by 1800855 on 4/6/17.
 */
public class ItemModAxe extends ItemAxe {
    protected String name;
    public ItemModAxe(String unlocalName, ToolMaterial material, float dam, float spd) {
        super(material, dam, spd);
        this.name = unlocalName;
        this.setUnlocalizedName(unlocalName);
        this.setRegistryName(unlocalName);
        registerItemModel();
    }

    public void registerItemModel()
    {
        ironam.proxy.registerItemRenderer(this, 0, name);
        System.out.println("One axe loaded named " + name);
    }
}
