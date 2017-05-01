package com.auri.ironam.SpiritCapability;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by 1800855 on 4/10/17.
 */
public class Spirit implements ISpirit {

    private float spiritPoints = 0.0f;


    public void setSpiritPoints(float points) {
        this.spiritPoints = points;
    }

    public float getSpiritPoints() {
        return this.spiritPoints;
    }

    public void addSpiritPoints(float points) {
        this.spiritPoints += points;
    }

    public void subSpiritPoints(float points) {
        this.spiritPoints -= points;

        if (this.spiritPoints < 0) this.spiritPoints = 0;
    }


}
