package com.auri.ironam.ItemBase;

import com.auri.ironam.ironam;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
