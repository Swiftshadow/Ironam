package com.auri.ironam.SpiritCapability;

import com.auri.ironam.ironam;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by 1800855 on 4/19/17.
 */
public class CapabilityHandler {

    private static final ResourceLocation SPIRIT_CAPABILITY = new ResourceLocation(ironam.MODID, "spirit");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof EntityPlayer)) return;

        //System.out.println("PRE-ADDDING " + SPIRIT_CAPABILITY.toString());
        try {
        e.addCapability(SPIRIT_CAPABILITY, new SpiritProvider()); }
        catch (java.lang.IllegalStateException excep) {
            //System.out.println("DUPLICATE KEY CAUGHT");
        }
        //System.out.println("POST-ADD");
    }
}
