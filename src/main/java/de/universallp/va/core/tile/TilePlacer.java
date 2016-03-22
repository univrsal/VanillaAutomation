package de.universallp.va.core.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Created by universallp on 19.03.2016 12:33.
 */
public class TilePlacer extends TileVA {

    public byte reachDistance = 1;
    public EnumFacing placeFace = EnumFacing.NORTH; // Rhymes, yo
    public boolean isTriggered = false;

    public TilePlacer() {
        super(9);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("distance", reachDistance);
        compound.setBoolean("isTriggered", isTriggered);
        compound.setByte("facing", (byte) placeFace.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        reachDistance = compound.getByte("distance");
        isTriggered = compound.getBoolean("isTriggered");
        placeFace = EnumFacing.values()[compound.getByte("facing")];
    }

    public int getNextPlaceable() {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack s = getStackInSlot(i);

            if (s != null && s.getItem() != null)
                    return i;
        }
        return -1;
    }
}
