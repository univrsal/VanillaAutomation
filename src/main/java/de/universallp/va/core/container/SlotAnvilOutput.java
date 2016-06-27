package de.universallp.va.core.container;

import de.universallp.va.client.gui.GuiAdvancedAnvil;
import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.tile.TileAdvancedAnvil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

/**
 * Created by universallp on 25.06.2016 18:28.
 */
public class SlotAnvilOutput extends Slot {

    private TileAdvancedAnvil anvilInv;

    public SlotAnvilOutput(TileAdvancedAnvil inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        anvilInv = inventoryIn;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        boolean result = super.canTakeStack(playerIn);

        if (result)
            if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                GuiScreen s = Minecraft.getMinecraft().currentScreen;

                if (s != null && s instanceof GuiAdvancedAnvil) {
                    GuiAdvancedAnvil anvilGui = (GuiAdvancedAnvil) s;

                    List<String> origText = anvilGui.getItemDesc();
                    int[] fieldIds = new int[origText.size()];
                    for (int i = 0; i < fieldIds.length; i++)
                        fieldIds[i] = i;
                    PacketHandler.sendToServer(new MessageSetFieldServer(fieldIds, origText.toArray(new String[0]), anvilInv.getPos()));
                }
            }
        return result;
    }
}
