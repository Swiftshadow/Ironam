package com.auri.ironam;

/**
 * Created by 1800855 on 10/17/16.
 */
import com.auri.ironam.Items.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {


    public static ItemBase materialEctoplasm;
    public static ItemBase materialEctoball;
    public static ItemBase itemBinder;;
    public static ItemBase materialPureEctoplasm;
    public static ItemBase materialSpiritDiamond;
    public static ItemBase materialSpiritIron;
    public static ItemBase materialSpiritGold;
    public static ItemBase weaponGravitySword;
    public static ItemBase itemGlowTorch;

    public static ItemModSword swordSpiritDiamond;
    public static ItemModPickaxe pickSpiritDiamond;
    public static ItemModHoe hoeSpiritDiamond;
    public static ItemModAxe axeSpiritDiamond;
    public static ItemModShovel shovelSpiritDiamond;

    public static ItemModSword swordSpiritIron;
    public static ItemModPickaxe pickSpiritIron;
    public static ItemModHoe hoeSpiritIron;
    public static ItemModAxe axeSpiritIron;
    public static ItemModShovel shovelSpiritIron;

    public static ItemModSword swordSpiritGold;
    public static ItemModPickaxe pickSpiritGold;
    public static ItemModHoe hoeSpiritGold;
    public static ItemModAxe axeSpiritGold;
    public static ItemModShovel shovelSpiritGold;

    public static SoundEvent soundEvent;

    public static ItemArmor.ArmorMaterial EctoArmor = EnumHelper.addArmorMaterial("Ectoplasm", "ironam:ironam", 0, new int[]{4, 7, 5, 4}, 20, soundEvent, 8);
    public static Item.ToolMaterial spiritIron = EnumHelper.addToolMaterial("spiritIron", 3, 500, 8.0F, 2.5F, 15);
    public static Item.ToolMaterial spiritGold = EnumHelper.addToolMaterial("spiritGold", 1, 100, 20.0F, 1.0F, 25);
    public static Item.ToolMaterial spiritDiamond = EnumHelper.addToolMaterial("spiritDiamond", 4, 2551, 15.0F, 5.0F, 13);


    public static void init()
    {
        materialEctoplasm = register(new ItemBase("materialEctoplasm").setCreativeTab(CreativeTabs.MATERIALS));
        materialEctoball = register(new ItemBase("materialEctoball").setCreativeTab(CreativeTabs.MATERIALS));
        itemBinder = register(new ItemBase("itemBinder").setCreativeTab(CreativeTabs.TOOLS));
        materialPureEctoplasm = register(new ItemBase("materialPureEctoplasm").setCreativeTab(CreativeTabs.MATERIALS));
        materialSpiritDiamond = register(new ItemBase("materialSpiritDiamond").setCreativeTab(CreativeTabs.MATERIALS));
        materialSpiritIron = register(new ItemBase("materialSpiritIron").setCreativeTab(CreativeTabs.MATERIALS));
        materialSpiritGold = register(new ItemBase("materialSpiritGold").setCreativeTab(CreativeTabs.MATERIALS));
        weaponGravitySword = register(new ItemBase("weaponGravitySword").setCreativeTab(CreativeTabs.COMBAT));
        itemGlowTorch = register(new ItemBase("itemGlowTorch").setCreativeTab(CreativeTabs.TOOLS));
        GameRegistry.register(new ModArmor("ectoHelmet", EctoArmor, "ectoHelmet", EntityEquipmentSlot.HEAD));
        GameRegistry.register(new ModArmor("ectoChest", EctoArmor, "ectoChest", EntityEquipmentSlot.CHEST));
        GameRegistry.register(new ModArmor("ectoLegs", EctoArmor, "ectoLegs", EntityEquipmentSlot.LEGS));
        GameRegistry.register(new ModArmor("ectoBoots", EctoArmor, "ectoBoots", EntityEquipmentSlot.FEET));

        //Spirit Iron tools
        swordSpiritIron = register(new ItemModSword("swordSpiritIron", spiritIron));
        pickSpiritIron = register(new ItemModPickaxe("pickSpiritIron", spiritIron));
        axeSpiritIron = register(new ItemModAxe("axeSpiritIron", spiritIron, 10, -3f));
        hoeSpiritIron = register(new ItemModHoe("hoeSpiritIron", spiritIron));
        shovelSpiritIron = register(new ItemModShovel("shovelSpiritIron", spiritIron));

        //Spirit Gold tools
        swordSpiritGold = register(new ItemModSword("swordSpiritGold", spiritGold));
        pickSpiritGold = register(new ItemModPickaxe("pickSpiritGold", spiritGold));
        axeSpiritGold = register(new ItemModAxe("axeSpiritGold", spiritGold, 9, -3f));
        hoeSpiritGold = register(new ItemModHoe("hoeSpiritGold", spiritGold));
        shovelSpiritGold = register(new ItemModShovel("shovelSpiritGold", spiritGold));

        //Spirit Diamond tools
        swordSpiritDiamond = register(new ItemModSword("swordSpiritDiamond", spiritDiamond));
        pickSpiritDiamond = register(new ItemModPickaxe("pickSpiritDiamond", spiritDiamond));
        axeSpiritDiamond = register(new ItemModAxe("axeSpiritDiamond", spiritDiamond, 12, -3f));
        hoeSpiritDiamond = register(new ItemModHoe("hoeSpiritDiamond", spiritDiamond));
        shovelSpiritDiamond = register(new ItemModShovel("shovelSpiritDiamond", spiritDiamond));


    }

    //@SideOnly(Side.CLIENT)
    public static void initModels()
    {
        ModItems.weaponGravitySword.registerItemModel();
        ModItems.swordSpiritDiamond.registerItemModel();
    }

    private static <T extends Item> T register(T item)
    {
        GameRegistry.register(item);

        if (item instanceof ItemBase)
        {
            ((ItemBase)item).registerItemModel();
        }


        return item;
    }
}
