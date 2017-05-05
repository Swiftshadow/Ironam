package com.auri.ironam.Items;

import com.auri.ironam.ironam;
import net.minecraft.item.ItemSword;

/**
 * Created by 1800855 on 4/6/17.
 */
public class ItemModSword extends ItemSword {
    protected String name;
    public ItemModSword(String unlocalName, ToolMaterial material) {
        super(material);
        this.name = unlocalName;
        this.setUnlocalizedName(unlocalName);
        this.setRegistryName(unlocalName);
        registerItemModel();
    }


    public void registerItemModel()
    {
        ironam.proxy.registerItemRenderer(this, 0, name);
        System.out.println("One sword loaded named " + name);
    }

}
