package de.universallp.va.core.container.handler;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.wrapper.InvWrapper;

/**
 * Created by universallp on 13.04.2016 18:20.
 */
public class XPHopperCartInvWrapper extends InvWrapper {

    public XPHopperCartInvWrapper(IInventory inv) {
        super(inv);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot == 5) // prevent pipes from taking out the bottles
            return null;
        System.out.println("noobs");
        return super.extractItem(slot, amount, simulate);
    }
}
