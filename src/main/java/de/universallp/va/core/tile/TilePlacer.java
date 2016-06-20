package de.universallp.va.core.tile;

import de.universallp.va.core.handler.ConfigHandler;
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
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setByte("distance", reachDistance);
        compound.setBoolean("isTriggered", isTriggered);
        compound.setByte("facing", (byte) placeFace.ordinal());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        reachDistance = compound.getByte("distance") > ConfigHandler.BLOCK_PLACER_REACH ? ConfigHandler.BLOCK_PLACER_REACH : compound.getByte("distance");
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

    @Override
    public void setField(int id, int value) {
        if (id == 0 && value < ConfigHandler.BLOCK_PLACER_REACH + 1)
            reachDistance = (byte) value;
        else if (id == 1 && value < EnumFacing.values().length)
            placeFace = EnumFacing.values()[value];

        markDirty();
    }

    @Override
    public int getField(int id) {
        if (id == 0)
            return reachDistance;
        else if (id == 1)
            return placeFace.ordinal();
        return super.getField(id);
    }
}
