package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class StickBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        TileEntityDispenser te = source.getBlockTileEntity();
        IBlockState s = te.getWorld().getBlockState(source.getBlockPos());
        EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);
        BlockPos destination = source.getBlockPos().add(Utils.extend(f.getDirectionVec(), Utils.getReach(stack)));

        VAFakePlayer player = VAFakePlayer.instance(te.getWorld());
        IBlockState state = te.getWorld().getBlockState(destination);

        state.getBlock().onBlockClicked(te.getWorld(), destination, player);
        state.getBlock().onBlockActivated(te.getWorld(), destination, state, player, EnumHand.MAIN_HAND, f, 0.5f, 0.5f, 0.f);
        return stack;
    }
}
