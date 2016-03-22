package de.universallp.va.core.network.messages;

import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.tile.TilePlacer;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by universallp on 20.03.2016 18:45.
 */
public class MessageSyncClient implements IMessage, IMessageHandler<MessageSyncClient, IMessage> {

    public BlockPos placer;
    public byte reachDistance;
    public EnumFacing facing;

    public MessageSyncClient() {
    }

    public MessageSyncClient(BlockPos placerPos, int reachDistance, EnumFacing f) {
        this.placer = placerPos;
        this.reachDistance = (byte) reachDistance;
        this.facing = f;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        reachDistance = buf.readByte();
        facing = EnumFacing.values()[buf.readByte()];
        placer = PacketHandler.readBlockPos(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(reachDistance);
        buf.writeByte(facing.ordinal());
        PacketHandler.writeBlockPos(buf, placer);
    }

    @Override
    public IMessage onMessage(MessageSyncClient message, MessageContext ctx) {
        World w = FMLClientHandler.instance().getWorldClient();
        TileEntity te = w.getTileEntity(message.placer);

        if (te != null && te instanceof TilePlacer) {
            ((TilePlacer) te).reachDistance = message.reachDistance;
            ((TilePlacer) te).placeFace = message.facing;
        }

        return null;
    }
}
