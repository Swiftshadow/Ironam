package com.auri.ironam;

import com.auri.ironam.SpiritCapability.ISpirit;
import com.auri.ironam.proxy.ClientProxy;
import com.auri.ironam.proxy.CommonProxy;
import com.auri.ironam.recipies.ModRecipies;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


@Mod(modid = ironam.MODID, version = ironam.VERSION)
public class ironam
{
    @SidedProxy(serverSide = "com.auri.ironam.proxy.CommonProxy", clientSide = "com.auri.ironam.proxy.ClientProxy")
    public static CommonProxy proxy;
    public static final String MODID = "ironam";
    public static final String VERSION = "1.0";
    public static final String name = "Ironam";
    public static SimpleNetworkWrapper snw;
    public static ISpirit spirit;


    //@Mod.Instance(MODID)
    //public static ironam instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        CommonProxy.init();
        ClientProxy.init();
        ModItems.init();
        ModRecipies.init();
        ModBlocks.init();
        //snw = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        //snw.registerMessage(CustomMessageHandler.class, CustomMessage.class, 0, Side.CLIENT);
        //CapabilityManager.INSTANCE.register(ISpirit, storage, default implementation factory);
        System.out.print("[" + Minecraft.getSystemTime() + "] [Ironam] Loading!\n");
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {


    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
