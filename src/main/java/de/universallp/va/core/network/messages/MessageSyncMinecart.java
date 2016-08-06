package de.universallp.va.core.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by universallp on 12.04.2016 20:16.
 */
public class MessageSyncMinecart implements IMessage, IMessageHandler<MessageSyncMinecart, IMessage> {

    public int minecartUUID;

    public MessageSyncMinecart() {
    }

    public MessageSyncMinecart(int minecart) {
        this.minecartUUID = minecart;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        minecartUUID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(minecartUUID);
    }

    @Override
    public IMessage onMessage(MessageSyncMinecart message, MessageContext ctx) {
        return null;
    }
}
