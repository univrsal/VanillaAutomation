package de.universallp.va.core.tile;

import de.universallp.va.core.block.BlockPlacer;
import de.universallp.va.core.util.ICustomField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
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
public class TileAutoTrader extends TileVA implements ICustomField {

    public MerchantRecipeList list;
    private int tradeID = 0;
    private int maxTrades = 0;
    private EntityVillager villager;
    private String villagerName;
    private boolean[] tradeStatuses;
    private boolean isTradingPossible;

    public TileAutoTrader() {
        super(6);
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0 && id >= 0)
            tradeID = value;
        else if (id == 1) {
            maxTrades = value;
            if (worldObj.isRemote) {
                tradeStatuses = new boolean[maxTrades]; // Only client side
            }
        } else if (id == 2) {
            isTradingPossible = value == 1;
        } else if (worldObj.isRemote) {
            if (tradeStatuses != null && id - 3 >= 0 && id - 3 < tradeStatuses.length) {
                tradeStatuses[id - 3] = value == 1;
            }
        }

        scanForVillager();
    }


    public boolean getIsTradePossible(int id) {
        if (worldObj.isRemote) {
            if (tradeStatuses != null)
                if (id >= 0 && id < tradeStatuses.length)
                    return tradeStatuses[id];
            return false;
        } else {
            if (id >= 0 && list != null && id < list.size()) {
                return list.get(id).isRecipeDisabled();
            }
            return false;
        }
    }

    @Override
    public int getField(int id) {
        scanForVillager();
        int i = 0;
        if (id == 0)
            i = tradeID;
        else if (id == 1)
            i = maxTrades;
        else if (id == 2)
            i = isTradingPossible ? 1 : 0;
        else if (list != null && id - 3 < list.size() && id - 3 >= 0) {
            boolean isDisabled = list.get(id - 3).isRecipeDisabled();
            i = isDisabled ? 0 : 1;
        }


        scanForVillager();
        return i;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    public boolean isTradingPossible() {
        return isTradingPossible;
    }

    public void scanForVillager() {
        if (worldObj.isRemote)
            return; // No need to to this on the client side

        IBlockState bS = worldObj.getBlockState(getPos());
        BlockPos p = getPos().add(BlockPlacer.getFacingFromState(bS).getDirectionVec());

        List<EntityVillager> villagers = worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB(p));

        if (villagers.size() > 0) {
            villager = villagers.get(0);
            list = villager.getRecipes(null);
            maxTrades = list.size();
            MerchantRecipe recipe = list.get(tradeID);
            setInventorySlotContents(0, recipe.getItemToBuy());
            setInventorySlotContents(1, recipe.getSecondItemToBuy());
            setInventorySlotContents(2, recipe.getItemToSell());
            villagerName = villager.getDisplayName().getUnformattedText();
            isTradingPossible = true;
        } else {
            isTradingPossible = false;
            villager = null;
            villagerName = null;
            list = null;
            tradeID = 0;
            maxTrades = 0;
            setInventorySlotContents(0, null);
            setInventorySlotContents(1, null);
            setInventorySlotContents(2, null);
        }
    }


    @Override
    public void setStringField(int id, String val) {
        if (id == 0) {
            villagerName = val;
        }
    }

    @Override
    public String getStringField(int id) {
        if (id == 0 && villagerName != null) {
            return villagerName;
        }
        return "No Villager";
    }
}
