package de.universallp.va.core.network;

import de.universallp.va.client.ClientProxy;
import de.universallp.va.client.gui.GuiGuide;
import de.universallp.va.client.gui.GuiPlacer;
import de.universallp.va.client.gui.GuiXPHopper;
import de.universallp.va.core.container.ContainerXPHopper;
import de.universallp.va.core.network.messages.MessageSetFieldClient;
import de.universallp.va.core.tile.TilePlacer;
import de.universallp.va.core.tile.TileXPHopper;
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
            PacketHandler.sendTo(new MessageSetFieldClient(new int[]{0, 1}, new byte[]{((TilePlacer) te).reachDistance, (byte) ((TilePlacer) te).placeFace.ordinal()}, te.getPos()), (EntityPlayerMP) player);
            return new ContainerDispenser(player.inventory, (IInventory) te);
        } else if (ID == 2 && te instanceof TileXPHopper) {
            PacketHandler.sendTo(new MessageSetFieldClient(0, ((TileXPHopper) te).getProgress(), te.getPos()), (EntityPlayerMP) player);
            return new ContainerXPHopper(player.inventory, (IInventory) te);
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

        if (ID == 0 && te instanceof TilePlacer)
            return new GuiPlacer(player.inventory, (TilePlacer) te, ((TilePlacer) te).reachDistance, ((TilePlacer) te).placeFace);
        else if (ID == 2)
            return new GuiXPHopper(player.inventory, (IInventory) te);

        return null;
    }
}
