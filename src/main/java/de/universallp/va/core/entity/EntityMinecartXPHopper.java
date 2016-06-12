package de.universallp.va.core.entity;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.ClientProxy;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.container.ContainerXPHopper;
import de.universallp.va.core.container.handler.XPHopperCartInvWrapper;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.libs.LibGuiIDs;
import de.universallp.va.core.util.libs.LibLocalization;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

/**
 * Created by universallp on 10.04.2016 11:44.
 */
public class EntityMinecartXPHopper extends EntityMinecartHopper implements IEntryProvider {

    public XPHopperCartInvWrapper itemHandler = new XPHopperCartInvWrapper(this);
    private int progress = 0;

    public EntityMinecartXPHopper(World w) {
        super(w);
    }

    public EntityMinecartXPHopper(World world, double posX, double posY, double posZ) {
        super(world, posX, posY, posZ);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("progress", progress);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("progress"))
            progress = compound.getInteger("progress");
    }

    @Override
    public int getSizeInventory() {
        return 6;
    }

    @Override
    public Type getType() {
        return Type.HOPPER;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }

    @Override
    public void killMinecart(DamageSource source) {
        this.setDead();

        if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
            ItemStack cart = new ItemStack(Items.minecart, 1);
            ItemStack xpHopper = new ItemStack(VABlocks.xpHopper, 1);

            this.entityDropItem(xpHopper, 0.0F);
            this.entityDropItem(cart, 0.0F);
            for (int i = 0; i < this.getSizeInventory(); i++)
                if (this.getStackInSlot(i) != null) {
                    System.out.println("STACK " + i + ": " + this.getStackInSlot(i));
                    this.entityDropItem(this.getStackInSlot(i), 0.0F);
                }
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerXPHopper(playerInventory, this);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) itemHandler;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack != null && stack.getItem() != null && stack.getItem().equals(Items.glass_bottle)) {
            ItemStack bottles = getStackInSlot(5);
            if (getStackInSlot(index) == null && index == 5)
                return true;

            if (bottles != null && bottles.stackSize < bottles.getMaxStackSize() && index == 5)
                return true;

            if (bottles != null && bottles.stackSize >= bottles.getMaxStackSize() && index != 5)
                return false;
            return false;
        }
        return true;
    }

    @Override
    public ITextComponent getDisplayName() {
        if (hasCustomName())
            return super.getDisplayName();
        else
            return new TextComponentString(I18n.format(LibLocalization.GUI_XPHOPPER));
    }

    @Override
    public IBlockState getDisplayTile() {
        return VABlocks.xpHopper.getDefaultState();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand) {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, player, stack, hand)))
            return true;


        if (Utils.getCarriedItem(player) != null && Utils.getCarriedItem(player).getItem().equals(VAItems.itemGuide)) {
            if (worldObj.isRemote) {
                ClientProxy.hoveredEntry = getEntry();
                player.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_GUIDE, this.worldObj, 0, 0, 0);
            }
            return true;
        }

        if (!this.worldObj.isRemote) {
            player.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_XPHOPPER_CART, this.worldObj, 0, 0, 0);
        }

        return true;
    }

    public int getProgress() {
        return progress;
    }

    // Hopper stuff

    @Override
    public void onUpdate() {
        super.onUpdate();

        System.out.println("" + getBlocked());
        if (!getBlocked()) {
            BlockPos overHopper = getPosition().up();
            List<EntityXPOrb> orbs = getWorld().getEntitiesWithinAABB(EntityXPOrb.class, new AxisAlignedBB(overHopper).expandXyz(1));

            if (orbs != null && orbs.size() > 0)
                for (EntityXPOrb orb : orbs) {
                    int slot = TileXPHopper.getBottleSlot(this);
                    int resultXP = orb.xpValue + progress;
                    ItemStack bottles = getStackInSlot(5);
                    if (bottles != null && bottles.stackSize > 0)
                        if (resultXP >= TileXPHopper.xpPerBottle && slot > -1) { // If there's space for a new bottle and adding the xp of the current orb will result in a new bottle
                            ItemStack xpBottle = new ItemStack(Items.experience_bottle, 1);
                            getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).insertItem(slot, xpBottle, false);
                            setInventorySlotContents(5, Utils.decreaseStack(bottles, 1));
                            markDirty();
                            progress = resultXP - TileXPHopper.xpPerBottle;
                        } else {
                            if (slot == -1 && resultXP > TileXPHopper.xpPerBottle) // If theres no space for a new bottle and adding the xp of the current orb would result in a new bottle
                                break;

                            // If there's no space for a new bottle but adding the xp of the current orb won't result in  a new bottle
                            progress = resultXP;
                            getWorld().removeEntity(orb);
                        }
                }
        }
    }


    @Override
    public void setField(int id, int value) {
        if (id == 0 && value <= TileXPHopper.xpPerBottle)
            progress = value;
        markDirty();
    }

    @Override
    public int getField(int id) {
        if (id == 0)
            return progress;
        return super.getField(id);
    }

    @Override
    public VisualRecipe getRecipe() {
        return null;
    }

    @Override
    public EnumEntry getEntry() {
        return EnumEntry.XPHOPPER;
    }

    @Override
    public void addRecipe() {
        // NO-OP
    }
}
