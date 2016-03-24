package de.universallp.va.core.network;

import de.universallp.va.core.network.messages.MessageSyncClient;
import de.universallp.va.core.network.messages.MessageSyncPlacer;
import de.universallp.va.core.util.References;
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
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.MOD_ID);
    private static int ID = 0;

    public static void register() {
        INSTANCE.registerMessage(MessageSyncPlacer.class, MessageSyncPlacer.class, ID++, Side.SERVER);
        INSTANCE.registerMessage(MessageSyncClient.class, MessageSyncClient.class, ID++, Side.CLIENT);
    }

    public static void sendTo(IMessage m, EntityPlayerMP p) {
        INSTANCE.sendTo(m, p);
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
