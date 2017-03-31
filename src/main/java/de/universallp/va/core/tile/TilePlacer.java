package de.universallp.va.core.tile;

import de.universallp.va.core.handler.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Created by universallp on 19.03.2016 12:33 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class TilePlacer extends TileVA {

    public byte reachDistance = 1;
    public EnumFacing placeFace = EnumFacing.NORTH; // Rhymes, yo
    public boolean isTriggered = false;
    public boolean useRedstone = false;

    public TilePlacer() {
        super(9);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setByte("distance", reachDistance);
        compound.setBoolean("isTriggered", isTriggered);
        compound.setByte("facing", (byte) placeFace.ordinal());
        compound.setBoolean("useRedstone", useRedstone);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        reachDistance = compound.getByte("distance") > ConfigHandler.BLOCK_PLACER_REACH ? ConfigHandler.BLOCK_PLACER_REACH : compound.getByte("distance");
        isTriggered = compound.getBoolean("isTriggered");
        placeFace = EnumFacing.values()[compound.getByte("facing")];
        useRedstone = compound.getBoolean("useRedstone");
    }

    public int getNextPlaceable() {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack s = getStackInSlot(i);

            if (s != null && !s.isEmpty())
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
        else if (id == 2)
            useRedstone = value == 1;
        markDirty();
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public int getField(int id) {
        if (id == 0)
            return reachDistance;
        else if (id == 1)
            return placeFace.ordinal();
        else if (id == 2)
            return useRedstone ? 1 : 0;

        return super.getField(id);
    }
}
