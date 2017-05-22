package com.auri.ironam.SpiritCapability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;


/**
 * Created by 1800855 on 4/19/17.
 */
public class SpiritProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(ISpirit.class)
    public static Capability<ISpirit> SPIRIT_CAPABILITY = null;

    private ISpirit instance = SPIRIT_CAPABILITY.getDefaultInstance();
    private Spirit spirit = new Spirit();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing side) {
        try {
            return capability == SPIRIT_CAPABILITY;
        } catch (java.lang.NullPointerException excep) {
            return false;
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
            return capability == SPIRIT_CAPABILITY ? SPIRIT_CAPABILITY.<T>cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        //return this.spirit.serializeNBT();
            return SPIRIT_CAPABILITY.getStorage().writeNBT(SPIRIT_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        //this.spirit.deserializeNBT(nbt);
        SPIRIT_CAPABILITY.getStorage().readNBT(SPIRIT_CAPABILITY, this.instance, null, nbt);
    }

}
