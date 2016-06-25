package de.universallp.va.core.container;

import de.universallp.va.client.gui.GuiAdvancedAnvil;
import de.universallp.va.core.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by universallp on 25.06.2016 13:55.
 */
public class SlotAnvilInput extends Slot {

    private IInventory anvil;

    public SlotAnvilInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.anvil = inventoryIn;
    }

    @Override
    public void putStack(@Nullable ItemStack stack) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            GuiScreen s = Minecraft.getMinecraft().currentScreen;

            if (s != null && s instanceof GuiAdvancedAnvil) {
                GuiAdvancedAnvil anvilGui = (GuiAdvancedAnvil) s;
                if (stack != null) {
                    anvilGui.toggleTextBoxes(true);
                    anvilGui.setItemName(stack.getDisplayName());
                    List<String> desc = Utils.readDescFromStack(stack);
                    System.out.println(desc);
                    if (desc != null)
                        anvilGui.setItemDesc(Utils.readDescFromStack(stack));

                } else {
                    anvilGui.setItemName("");
                    anvilGui.setItemDesc(new ArrayList<String>());
                    anvilGui.toggleTextBoxes(false);
                }
            }
        }
        super.putStack(stack);
    }
}
