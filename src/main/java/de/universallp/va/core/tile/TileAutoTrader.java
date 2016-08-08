package de.universallp.va.core.tile;

import de.universallp.va.core.block.BlockPlacer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.List;

/**
 * Created by universallp on 08.08.2016 17:45.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class TileAutoTrader extends TileVA implements ITickable {

    public MerchantRecipeList list;
    private int tradeID = 0;
    private int maxTrades = 0;
    private EntityVillager villager;

    public TileAutoTrader() {
        super(6);
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0 && id >= 0 && list != null && id < list.size())
            tradeID = value;
        else
            super.setField(id, value);
    }

    @Override
    public int getField(int id) {
        if (id == 0)
            return tradeID;
        else if (id == 1 && list != null)
            return list.size();
        else
            return super.getField(id);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    public boolean isTradePossible() {
        if (villager == null)
            return false;
        return true;
    }

    public void scanForVillager() {
        IBlockState bS = worldObj.getBlockState(getPos());
        BlockPos p = getPos().add(BlockPlacer.getFacingFromState(bS).getDirectionVec());

        List<EntityVillager> villagers = worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB(p));

        if (villagers.size() > 0) {
            villager = villagers.get(0);
        }
        list = villager.getRecipes(null);
    }

    @Override
    public void update() {
        if (villager == null) {
            scanForVillager();
        } else {
            MerchantRecipe recipe = list.get(tradeID);
            setInventorySlotContents(0, recipe.getItemToBuy());
            setInventorySlotContents(1, recipe.getSecondItemToBuy());
            setInventorySlotContents(2, recipe.getItemToSell());
        }
    }
}
