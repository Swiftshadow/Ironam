package com.auri.ironam;

import com.jcraft.jogg.Packet;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by 1800855 on 4/4/17.
 */
public class CustomMessageHandler implements IMessageHandler<CustomMessage, IMessage> {

    @Override
    public IMessage onMessage(CustomMessage message, MessageContext ctx) {
        System.out.println(message.anInt);
        ctx.getClientHandler().sendPacket(null);
        return null;
    }
}
