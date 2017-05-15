package com.auri.ironam.SpiritCapability;


/**
 * Created by 1800855 on 4/10/17.
 */
public interface ISpirit {
    float getSpiritPoints();

    void setSpiritPoints(float points);

    void addSpiritPoints(float points);

    void subSpiritPoints(float points);

    void setHittable(boolean hittable);

    //True is not hittable, false is hittable
    boolean getHittable();

    void setCleared(boolean cleared);

    boolean getCleared();

}
