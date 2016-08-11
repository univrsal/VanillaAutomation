package de.universallp.va.core.tile;

import de.universallp.va.core.block.BlockPlacer;
import de.universallp.va.core.util.ICustomField;
import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
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
public class TileAutoTrader extends TileVA implements ICustomField, IMerchant {

    public MerchantRecipeList list;
    private int tradeID = 0;
    private int maxTrades = 0;
    private EntityVillager villager;
    private String villagerName;
    private boolean[] tradeStatuses;
    private boolean isTradingPossible;
    private ItemStack tradeResult;

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

    public boolean performTrade() {
        if (list == null && !worldObj.isRemote)
            return false;
        if (worldObj.isRemote)
            return isTradingPossible() && getIsTradePossible(tradeID);

        MerchantRecipe trade = list.get(tradeID);

        if (trade != null) {
            ItemStack itemstack = getStackInSlot(3);
            ItemStack itemstack1 = getStackInSlot(4);

            if (this.doTrade(trade, itemstack, itemstack1) || this.doTrade(trade, itemstack1, itemstack)) {
                villager.useRecipe(trade);

                if (itemstack != null && itemstack.stackSize <= 0) {
                    itemstack = null;
                }

                if (itemstack1 != null && itemstack1.stackSize <= 0) {
                    itemstack1 = null;
                }

                setInventorySlotContents(0, itemstack);
                setInventorySlotContents(1, itemstack1);
                scanForVillager();
                return true;
            }
        }
        return false;
    }

    private ItemStack checkTrade(ItemStack firstItem, ItemStack secondItem) {
        MerchantRecipe trade = list.get(tradeID);
        ItemStack itemstack = trade.getItemToBuy();
        ItemStack itemstack1 = trade.getSecondItemToBuy();

        if (firstItem != null && firstItem.getItem() == itemstack.getItem() && firstItem.stackSize >= itemstack.stackSize)
            if (itemstack1 == null && secondItem == null || itemstack1 != null && secondItem != null && itemstack1.getItem() == secondItem.getItem() && secondItem.stackSize >= itemstack1.stackSize)
                return trade.getItemToSell();
        return null;
    }

    /**
     * @see net.minecraft.inventory.SlotMerchantResult
     */
    private boolean doTrade(MerchantRecipe trade, ItemStack firstItem, ItemStack secondItem) {
        ItemStack itemstack = trade.getItemToBuy();
        ItemStack itemstack1 = trade.getSecondItemToBuy();

        if (firstItem != null && firstItem.getItem() == itemstack.getItem() && firstItem.stackSize >= itemstack.stackSize) {
            if (itemstack1 != null && secondItem != null && itemstack1.getItem() == secondItem.getItem() && secondItem.stackSize >= itemstack1.stackSize) {
                firstItem.stackSize -= itemstack.stackSize;
                secondItem.stackSize -= itemstack1.stackSize;
                return true;
            }

            if (itemstack1 == null && secondItem == null) {
                firstItem.stackSize -= itemstack.stackSize;
                return true;
            }
        }

        return false;
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
            setInventorySlotContents(3, recipe.getItemToBuy());
            setInventorySlotContents(4, recipe.getSecondItemToBuy());
            setInventorySlotContents(5, recipe.getItemToSell());
            villagerName = villager.getDisplayName().getUnformattedText();
            isTradingPossible = true;
        } else {
            isTradingPossible = false;
            villager = null;
            villagerName = null;
            list = null;
            tradeID = 0;
            maxTrades = 0;
            setInventorySlotContents(3, null);
            setInventorySlotContents(4, null);
            setInventorySlotContents(5, null);
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        super.setInventorySlotContents(index, stack);
//        if (!worldObj.isRemote && list != null && index == 3 || index == 4 && isTradingPossible()) {
//            ItemStack itemstack = getStackInSlot(3);
//            ItemStack itemstack1 = getStackInSlot(4);
//
//            ItemStack s = checkTrade(itemstack, itemstack1);
//
//            if (s == null)
//                s = checkTrade(itemstack1, itemstack);
//
//            setInventorySlotContents(5, s);
//            tradeResult = s;
//        }
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

    public ItemStack getTradeResult() {
        return tradeResult;
    }

    public void setTradeResult(ItemStack tradeResult) {
        this.tradeResult = tradeResult;
    }

    @Override
    public EntityPlayer getCustomer() {
        return VAFakePlayer.instance(worldObj);
    }

    @Override
    public void setCustomer(EntityPlayer player) {
        // NO-OP
    }

    @Override
    public MerchantRecipeList getRecipes(EntityPlayer player) {
        return list;
    }

    @Override
    public void setRecipes(MerchantRecipeList recipeList) {
        list = recipeList;
    }

    @Override
    public void useRecipe(MerchantRecipe recipe) {
        if (villager != null)
            villager.useRecipe(recipe);
    }

    @Override
    public void verifySellingItem(ItemStack stack) {
        if (!this.worldObj.isRemote && villager.livingSoundTime > -villager.getTalkInterval() + 20) {
            villager.livingSoundTime = -villager.getTalkInterval();

            if (stack != null) {
                villager.playSound(SoundEvents.ENTITY_VILLAGER_YES, 1.0F, 1.0F);
            } else {
                villager.playSound(SoundEvents.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
            }
        }
    }
}
