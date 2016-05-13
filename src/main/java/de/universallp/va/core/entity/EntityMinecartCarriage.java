package de.universallp.va.core.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * Created by universallp on 16.04.2016 17:32.
 */
public class EntityMinecartCarriage extends EntityMinecart {

    private ItemStack carriedBlock;

    public EntityMinecartCarriage(World worldIn) { // Default constructor, needed
        super(worldIn);
        this.carriedBlock = new ItemStack(Blocks.brick_block, 1);
    }

    public EntityMinecartCarriage(World worldIn, double x, double y, double z, ItemStack block) {
        super(worldIn, x, y, z);
        this.carriedBlock = block;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagCompound c = new NBTTagCompound();
        carriedBlock.writeToNBT(c);

        tagCompound.setTag("carriage", c);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        if (tagCompound.hasKey("carriage"))
            carriedBlock = ItemStack.loadItemStackFromNBT((NBTTagCompound) tagCompound.getTag("carriage"));
    }

    @Override
    public Type getType() {
        return Type.TNT;
    }

    @Override
    public IBlockState getDisplayTile() {
        if (carriedBlock != null)
            return Block.getBlockFromItem(carriedBlock.getItem()).getStateFromMeta(carriedBlock.getItemDamage());
        else return null;
    }

    @Override
    public void killMinecart(DamageSource source) {
        super.killMinecart(source);
        this.entityDropItem(carriedBlock, 0.0F);
    }

    public void setCarriedBlock(ItemStack carriedBlock) {
        this.carriedBlock = carriedBlock;
    }
}
