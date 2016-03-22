package de.universallp.va.core.network.messages;

import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.tile.TilePlacer;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by universallp on 20.03.2016 17:22.
 */
public class MessageSyncPlacer implements IMessage, IMessageHandler<MessageSyncPlacer, IMessage> {

    byte reachDistance;
    EnumFacing facing;
    BlockPos placerPos;

    public MessageSyncPlacer() { }

    public MessageSyncPlacer(BlockPos placer, EnumFacing f, byte reachdist) {
        this.placerPos = placer;
        this.facing = f;
        this.reachDistance = reachdist;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.reachDistance = buf.readByte();
        this.facing = EnumFacing.values()[buf.readByte()];
        this.placerPos = PacketHandler.readBlockPos(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(reachDistance);
        buf.writeByte(facing.ordinal());
        PacketHandler.writeBlockPos(buf, placerPos);
    }

    @Override
    public IMessage onMessage(MessageSyncPlacer message, MessageContext ctx) {
        World w = ctx.getServerHandler().playerEntity.worldObj;

        if(w != null) {
            TileEntity teP = w.getTileEntity(message.placerPos);
            if (teP != null && teP instanceof TilePlacer) {

                ((TilePlacer) teP).reachDistance = (message.reachDistance > 0 && message.reachDistance < 17 ? message.reachDistance : 1);
                ((TilePlacer) teP).placeFace = message.facing;
            }
        }
        return null;
    }
}
