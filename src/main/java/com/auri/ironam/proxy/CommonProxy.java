package com.auri.ironam.proxy;

import com.auri.ironam.*;
import com.auri.ironam.SpiritCapability.CapabilityHandler;
import com.auri.ironam.SpiritCapability.ISpirit;
import com.auri.ironam.SpiritCapability.Spirit;
import com.auri.ironam.SpiritCapability.SpiritStorage;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by 1800855 on 10/17/16.
 */
public class CommonProxy {
    public static void init()
    {

        //Spirit capability regestration
        MinecraftForge.EVENT_BUS.register((new CapabilityHandler()));
        try {
            CapabilityManager.INSTANCE.register(ISpirit.class, new SpiritStorage(), Spirit.class);
        } catch (java.lang.IllegalStateException excep) {
            System.out.println("INSTANCE EXCEPTION");
        }

        //Normal event regestration
        MinecraftForge.EVENT_BUS.register(new EventHandlerCommon());
        EventHandlerCommon handler = new EventHandlerCommon();
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        ModItems.initModels();
    }
    public void registerItemRenderer(Item item, int i, String name) {}
}
