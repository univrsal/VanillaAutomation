package de.universallp.va.core.network.messages;

import de.universallp.va.core.entity.EntityMinecartCarriage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by universallp on 16.04.2016 19:53.
 */
public class MessageSyncMinecartCarriage implements IMessage, IMessageHandler<MessageSyncMinecartCarriage, IMessage> {

    public int cartID;

    public ItemStack cartCarriage;

    public MessageSyncMinecartCarriage() { }

    public MessageSyncMinecartCarriage(int u, ItemStack carriage) {
        this.cartCarriage = carriage;
        this.cartID = u;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        cartID = buf.readInt();
        cartCarriage = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(cartID);
        ByteBufUtils.writeItemStack(buf, cartCarriage);
    }

    @Override
    public IMessage onMessage(MessageSyncMinecartCarriage message, MessageContext ctx) {
        Entity e = FMLClientHandler.instance().getWorldClient().getEntityByID(message.cartID);

        if (e != null && e instanceof EntityMinecartCarriage) {
            ((EntityMinecartCarriage) e).setCarriedBlock(message.cartCarriage);
        }
        return null;
    }
}
