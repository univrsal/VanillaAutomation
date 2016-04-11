package de.universallp.va.core.entity;

import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.container.ContainerXPHopper;
import de.universallp.va.core.util.libs.LibGuiIDs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by universallp on 10.04.2016 11:44.
 */
public class EntityXPHopperMinecart extends EntityMinecartHopper {

    public EntityXPHopperMinecart(World w) {
        super(w);
    }

    public EntityXPHopperMinecart(World world, double posX, double posY, double posZ) {
        super(world, posX, posY, posZ);
    }

    @Override
    public Type getType() {
        return Type.RIDEABLE;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }

    @Override
    public void killMinecart(DamageSource source) {
        this.setDead();

        if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
            ItemStack itemstack = new ItemStack(Items.minecart, 1);

            if (this.getName() != null)
                itemstack.setStackDisplayName(this.getName());

            this.entityDropItem(itemstack, 0.0F);

            InventoryHelper.dropInventoryItems(this.worldObj, this, this);
            dropItemWithOffset(Item.getItemFromBlock(VABlocks.xpHopper), 1, 0.0F);
        }
    }

    @Override
    public String getGuiID() {
        return LibGuiIDs.GUI_XPHOPPERMINECART;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerXPHopper(playerInventory, this);
    }

    @Override
    public IBlockState getDisplayTile() {
        return VABlocks.xpHopper.getDefaultState();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand) {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, player, stack, hand)))
            return true;
        if (!this.worldObj.isRemote) {
            player.displayGUIChest(this);
        }

        return true;
    }
}
