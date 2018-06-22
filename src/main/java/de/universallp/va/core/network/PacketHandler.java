package de.universallp.va.core.network;

import de.universallp.va.core.network.messages.MessagePlaySound;
import de.universallp.va.core.network.messages.MessageSetFieldClient;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.util.ICustomField;
import de.universallp.va.core.util.libs.LibNames;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by universallp on 20.03.2016 15:45 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibNames.MOD_ID);
    private static int ID = 0;

    public static void register() {
        INSTANCE.registerMessage(MessageSetFieldClient.class, MessageSetFieldClient.class, ID++, Side.CLIENT);
        INSTANCE.registerMessage(MessageSetFieldServer.class, MessageSetFieldServer.class, ID++, Side.SERVER);
        INSTANCE.registerMessage(MessagePlaySound.class, MessagePlaySound.class, ID++, Side.CLIENT);
    }

    public static void sendTo(IMessage m, EntityPlayerMP p) {
        INSTANCE.sendTo(m, p);
    }

    public static void sendTo(IMessage m) {
        INSTANCE.sendToServer(m);
    }

    public static void sendTo(IMessage m, int range, int dim, BlockPos center) {
        INSTANCE.sendToAllAround(m, new NetworkRegistry.TargetPoint(dim, center.getX(), center.getY(), center.getZ(), range));
    }

    /**
     * Syncs all field values from a server TileEntity with the client
     * TileEntity must implement IInventory/ILockableContainer
     * Field values must fit in a byte
     * @param pl            The target player for the message
     * @param te            The tileentity to sync
     * @param startField    Starting field value
     * @param endField      Ending field value
     */
    public static void syncByteFieldsClient(EntityPlayer pl, TileEntity te, int startField, int endField) {
        if (!(te instanceof IInventory))
            return;

        byte[] values = new byte[endField - startField + 1];
        int[] fields = new int[endField - startField + 1];

        int index = 0;
        for (int i = startField; i <= endField; i++) {
            fields[index] = i;
            values[index] = (byte) ((IInventory) te).getField(i);
            index++;
        }
        sendTo(new MessageSetFieldClient(fields, values, te.getPos()), (EntityPlayerMP) pl);
    }


    /**
     * Syncs all field values from a server TileEntity with the client
     * TileEntity must implement IInventory/ILockableContainer
     * Field values must fit in an integer
     * @param pl            The target player for the message
     * @param te            The tileentity to sync
     * @param startField    Starting field value
     * @param endField      Ending field value
     */
    public static void syncIntFieldsClient(EntityPlayer pl, TileEntity te, int startField, int endField) {
        if (!(te instanceof IInventory))
            return;

        int[] values = new int[endField - startField + 1];
        int[] fields = new int[endField - startField + 1];

        int index = 0;
        for (int i = startField; i <= endField; i++) {
            fields[index] = i;
            values[index] = ((IInventory) te).getField(i);
            index++;
        }
        sendTo(new MessageSetFieldClient(fields, values, te.getPos()), (EntityPlayerMP) pl);
    }

    public static void syncStringFieldClient(EntityPlayer pl, TileEntity te, int fieldID) {
        if (!(te instanceof ICustomField))
            return;

        String value = ((ICustomField) te).getStringField(fieldID);
        sendTo(new MessageSetFieldClient(fieldID, value, te.getPos()), (EntityPlayerMP) pl);
    }

    public static void sendToServer(IMessage msg) {
        INSTANCE.sendToServer(msg);
    }

    public static void writeBlockPos(ByteBuf to, BlockPos pos) {
        to.writeInt(pos.getX());
        to.writeInt(pos.getY());
        to.writeInt(pos.getZ());
    }

    public static BlockPos readBlockPos(ByteBuf from) {
        return new BlockPos(from.readInt(), from.readInt(), from.readInt());
    }
}
