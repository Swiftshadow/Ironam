package com.auri.ironam;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by 1800855 on 4/4/17.
 */
public class CustomMessage implements IMessage
{
    public int anInt;

    public CustomMessage() {}

    public CustomMessage(int a) {
        this.anInt = a;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(anInt);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.anInt = buf.readInt();
    }
}
