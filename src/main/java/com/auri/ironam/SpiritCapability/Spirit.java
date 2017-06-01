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

    private boolean hittable = true;

    private boolean cleared = false;

    private double boundX = 0;

    private double boundZ = 0;

    private double boundY = 0;

    private boolean isBound = false;

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

    public void setHittable(boolean hittable) {
        this.hittable = hittable;
    }

    public boolean getHittable() {
        return this.hittable;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    public boolean getCleared() {
        return this.cleared;
    }

    public void setBoundX(double x) {
        this.boundX = x;
    }

    public void setBoundZ(double z) {
        this.boundZ = z;
    }

    public void setBoundY(double y) {
        this.boundY = y;
    }

    public double getBoundX() {
        return this.boundX;
    }

    public double getBoundZ() {
        return this.boundZ;
    }

    public double getBoundY() {
        return this.boundY;
    }

    public boolean getBound() {
        return this.isBound;
    }

    //true is bound, false is not bound
    public void setBound(boolean bound) {
        this.isBound = bound;
    }

}
