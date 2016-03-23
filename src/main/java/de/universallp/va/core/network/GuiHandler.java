package de.universallp.va.core.network;

import de.universallp.va.client.ClientProxy;
import de.universallp.va.client.gui.GuiGuide;
import de.universallp.va.client.gui.GuiPlacer;
import de.universallp.va.core.network.messages.MessageSyncClient;
import de.universallp.va.core.tile.TilePlacer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by universallp on 19.03.2016 13:54.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (te == null)
            return null;
        if (ID == 0 && te instanceof TilePlacer) {
            PacketHandler.sendTo(new MessageSyncClient(te.getPos(), ((TilePlacer) te).reachDistance, ((TilePlacer) te).placeFace), (EntityPlayerMP) player);
            return new ContainerDispenser(player.inventory, (IInventory) te);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (ID == 1)
            if (ClientProxy.hoveredEntry == null)
                return new GuiGuide();
            else
                return new GuiGuide(ClientProxy.hoveredEntry);

        if (te == null)
            return null;

        if (ID == 0 && te instanceof TilePlacer) {
            return new GuiPlacer(player.inventory, (TilePlacer) te, ((TilePlacer) te).reachDistance, ((TilePlacer) te).placeFace);
        }

        return null;
    }
}
