package com.auri.ironam.Items;

import com.auri.ironam.ironam;
import net.minecraft.item.ItemSpade;

/**
 * Created by 1800855 on 4/6/17.
 */
public class ItemModShovel extends ItemSpade {
    protected String name;
    public ItemModShovel(String unlocalName, ToolMaterial material) {
        super(material);
        this.name = unlocalName;
        this.setUnlocalizedName(unlocalName);
        this.setRegistryName(unlocalName);
        registerItemModel();
    }

    public void registerItemModel()
    {
        ironam.proxy.registerItemRenderer(this, 0, name);
        System.out.println("One shovel loaded named " + name);
    }
}
