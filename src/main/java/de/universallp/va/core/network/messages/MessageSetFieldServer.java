package de.universallp.va.core.network.messages;

import de.universallp.va.core.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by universallp on 28.03.2016 13:42.
 */
public class MessageSetFieldServer implements IMessage, IMessageHandler<MessageSetFieldServer, IMessage> {

    public int[] integers;
    public byte[] bytes;
    public int[] fields;

    public BlockPos pos;
    public EnumMessageType type;

    public MessageSetFieldServer() {
    }

    public MessageSetFieldServer(int fieldID, int val, BlockPos tePos) {
        this.type = EnumMessageType.INT;
        this.integers = new int[] { val };
        this.fields = new int[] { fieldID };
        this.pos = tePos;
    }

    public MessageSetFieldServer(int[] fieldID, int[] val, BlockPos tePos) {
        this.type = EnumMessageType.BYTE;
        this.integers = val;
        this.fields = fieldID;
        this.pos = tePos;
    }

    public MessageSetFieldServer(int fieldID, byte val, BlockPos tePos) {
        this.type = EnumMessageType.BYTE;
        this.bytes = new byte[]{val};
        this.fields = new int[]{fieldID};
        this.pos = tePos;
    }

    public MessageSetFieldServer(int[] fieldID, byte[] val, BlockPos tePos) {
        this.type = EnumMessageType.BYTE;
        this.bytes = val;
        this.fields = fieldID;
        this.pos = tePos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = EnumMessageType.values()[buf.readByte()];

        switch (type) {
            case BYTE:
                int l = buf.readByte();

                fields = new int[l];
                bytes = new byte[l];

                for (int i = 0; i < l; i++)
                    bytes[i] = buf.readByte();

                for (int i = 0; i < l; i++)
                    fields[i] = buf.readInt();
                break;
            case INT:
                l = buf.readByte();
                fields = new int[l];
                integers = new int[l];

                for (int i = 0; i < l; i++)
                    integers[i] = buf.readInt();

                for (int i = 0; i < l; i++)
                    fields[i] = buf.readInt();
                break;
        }
        this.pos = PacketHandler.readBlockPos(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(type.ordinal());

        switch (type) {
            case BYTE:
                buf.writeByte(bytes.length);
                for (Byte b : bytes)
                    buf.writeByte(b);
                for (Integer i : fields)
                    buf.writeInt(i);
                break;
            case INT:
                buf.writeByte(integers.length);
                for (Integer i : integers)
                    buf.writeInt(i);
                for (Integer i : fields)
                    buf.writeInt(i);
                break;
        }

        PacketHandler.writeBlockPos(buf, pos);
    }

    @Override
    public IMessage onMessage(MessageSetFieldServer message, MessageContext ctx) {
        World w = ctx.getServerHandler().playerEntity.worldObj;

        TileEntity te = w.getTileEntity(message.pos);

        if (te == null || !(te instanceof IInventory))
            return null;
        IInventory inv = (IInventory) te;
        System.out.println("MODE FROM CLIENT: " + message.fields[0] + " : " + message.integers[0]);
        switch (message.type) {
            case BYTE:
                if (message.fields != null && message.bytes != null)
                    for (int i = 0; i < message.fields.length; i++)
                        inv.setField(message.fields[i], message.bytes[i]);
                break;
            case INT:
                if (message.fields != null && message.integers != null)
                    for (int i = 0; i < message.fields.length; i++)
                        inv.setField(message.fields[i], message.integers[i]);
                break;
        }

        System.out.println(inv.getField(0));
        inv.markDirty();
        return null;
    }

    private enum EnumMessageType {
        BYTE,
        INT
    }
}
