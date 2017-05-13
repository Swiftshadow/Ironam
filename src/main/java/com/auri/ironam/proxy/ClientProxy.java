package com.auri.ironam.proxy;

import com.auri.ironam.ModItems;
import com.auri.ironam.ironam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by 1800855 on 10/17/16.
 */
public class ClientProxy extends CommonProxy {

    public void registerItemRenderer(Item item, int meta, String id){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ironam.MODID + ":" + id, "inventory"));
        System.out.println("One item loaded named " + id);
    }
}
