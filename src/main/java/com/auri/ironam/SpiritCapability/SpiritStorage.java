package com.auri.ironam.SpiritCapability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by 1800855 on 4/19/17.
 */
public class SpiritStorage implements Capability.IStorage<ISpirit>
{

    @Override
    public NBTBase writeNBT(Capability<ISpirit> capability, ISpirit instance, EnumFacing side) {
        return new NBTTagFloat(instance.getSpiritPoints());
    }

    @Override
    public void readNBT(Capability<ISpirit> capability, ISpirit instance, EnumFacing side, NBTBase nbt) {
        try {
            instance.setSpiritPoints(((NBTPrimitive) nbt).getFloat());
            System.out.println("READ NBT " + nbt.toString());
        } catch (java.lang.NullPointerException excep) {
           System.out.println("READ NBT NULL PTR CAUGHT");
        }
    }

}
