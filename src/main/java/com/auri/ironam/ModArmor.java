package com.auri.ironam;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import javax.swing.text.html.parser.Entity;

/**
 * Created by 1800855 on 3/22/17.
 */

public class ModArmor extends ItemArmor {
    public String textureName;
    public ModArmor(String unlocalName, ArmorMaterial material, String textureName, EntityEquipmentSlot slot) {
        super (material, 0, slot);
        this.textureName = textureName;
        this.setUnlocalizedName(unlocalName);
        this.setRegistryName(ironam.MODID + ":" + unlocalName);
        registerItemModel();
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return ironam.MODID + ":textures/armor/" + this.textureName + "_" + (this.armorType == EntityEquipmentSlot.LEGS ? "2" : "1") + ".png";
    }

    public void registerItemModel()
    {
        ironam.proxy.registerItemRenderer(this, 0, textureName);
        System.out.println("One sword loaded named " + textureName);
    }
}
