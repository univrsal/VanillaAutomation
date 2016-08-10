package de.universallp.va.core.network.messages;

import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.tile.TileAutoTrader;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by universallp on 10.08.2016 02:33.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class MessageSyncTradeResults implements IMessage, IMessageHandler<MessageSyncTradeResults, IMessage> {

    public BlockPos traderPos;
    public ItemStack tradeResult;

    public MessageSyncTradeResults() {
    }

    public MessageSyncTradeResults(BlockPos traderPos, ItemStack s) {
        this.tradeResult = s;
        this.traderPos = traderPos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tradeResult = ByteBufUtils.readItemStack(buf);
        traderPos = PacketHandler.readBlockPos(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, tradeResult);
        PacketHandler.writeBlockPos(buf, traderPos);
    }

    @Override
    public IMessage onMessage(MessageSyncTradeResults message, MessageContext ctx) {
        World w = FMLClientHandler.instance().getWorldClient();
        TileEntity te = w.getTileEntity(message.traderPos);

        if (te == null || !(te instanceof TileAutoTrader))
            return null;

        ((TileAutoTrader) te).setTradeResult(message.tradeResult);
        return null;
    }
}
