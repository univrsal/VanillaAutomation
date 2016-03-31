package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

/**
 * Created by universallp on 31.03.2016 14:10.
 */
public class SeedBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        World worldIn = source.getBlockTileEntity().getWorld();
        EnumFacing facing = (EnumFacing) worldIn.getBlockState(source.getBlockPos()).getProperties().get(BlockDirectional.FACING);
        BlockPos pos = source.getBlockPos().add(facing.getDirectionVec());
        IBlockState state = worldIn.getBlockState(pos);
        IPlantable pl = (IPlantable) stack.getItem();

        if (facing == EnumFacing.UP && VAFakePlayer.instance(worldIn).canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, pl) && worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), pl.getPlant(worldIn, pos));
            --stack.stackSize;
        }

        return stack;
    }
}
