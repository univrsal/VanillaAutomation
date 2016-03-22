package de.universallp.va.core.dispenser;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;

/**
 * Created by universallp on 19.03.2016 17:25.
 */
public class DyeBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        TileEntityDispenser te = source.getBlockTileEntity();
        IBlockState s = te.getWorld().getBlockState(source.getBlockPos());
        EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);

        BlockPos destination = source.getBlockPos().add(f.getDirectionVec());
        List<EntitySheep> sheeps = te.getWorld().getEntitiesWithinAABB(EntitySheep.class, new AxisAlignedBB(destination));
        EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());

        if (sheeps != null && sheeps.size() > 0) {
            for (EntitySheep sheep : sheeps) {
                if (sheep.getFleeceColor() != enumdyecolor) {
                    sheep.setFleeceColor(enumdyecolor);
                    stack.stackSize--;
                    break;
                }
            }
        } else if (enumdyecolor == EnumDyeColor.WHITE) {
            ItemDye.applyBonemeal(stack, te.getWorld(), destination.down());
            if (te.getWorld().isRemote)
                ItemDye.spawnBonemealParticles(te.getWorld(), destination.down(), 5);
        }

        return stack;
    }
}
