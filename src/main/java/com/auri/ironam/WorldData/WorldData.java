package com.auri.ironam.WorldData;

import com.auri.ironam.ironam;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

/**
 * Created by 1800855 on 5/22/17.
 */
public class WorldData extends WorldSavedData {
    private static final String DATA_NAME = ironam.MODID + "_ironam";

    public WorldData() {
        super(DATA_NAME);
    }

    public WorldData(String s) {
        super (s);
    }

    public void readFromNBT(NBTTagCompound nbt) {

    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return nbt;
    }

    public static WorldData get(World world) {
        MapStorage storage = world.getMapStorage();
        WorldData instance = (WorldData) storage.getOrLoadData(WorldData.class, DATA_NAME);

        if (instance == null) {
            instance = new WorldData();
            storage.setData(DATA_NAME, instance);
        }

        return instance;
    }
}
