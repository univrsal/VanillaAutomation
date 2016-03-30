package de.universallp.va.core.network;

import de.universallp.va.core.network.messages.MessagePlaySound;
import de.universallp.va.core.network.messages.MessageSetFieldClient;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.util.libs.LibNames;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by universallp on 20.03.2016 15:45.
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

    public static void sendFieldMsgTo(EntityPlayerMP p, int field1, int field2, byte val1, byte val2, BlockPos pos) {
        sendTo(new MessageSetFieldClient(new int[] { field1, field2 }, new byte[] { val1, val2 }, pos), p);
    }

    public static void sendFieldMsgTo(EntityPlayerMP p, int field1, byte val1, BlockPos pos) {
        sendTo(new MessageSetFieldClient(field1, val1, pos), p);
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
