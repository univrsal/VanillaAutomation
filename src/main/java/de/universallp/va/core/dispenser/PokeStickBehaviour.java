package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.LogHelper;
import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by universallp on 24.03.2016 23:12.
 */
public class PokeStickBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        World w = source.getBlockTileEntity().getWorld();
        IBlockState s = w.getBlockState(source.getBlockPos());
        EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);

        BlockPos dest = source.getBlockPos().add(Utils.extend(f.getDirectionVec(), Utils.getReach(stack)));
        IBlockState state = w.getBlockState(dest);
        Block b = state.getBlock();

        VAFakePlayer.instance(w).rightClick(stack, dest, f, 0, 0, 0);
        if (b != null) {
            try {
                b.onBlockActivated(w, dest, state, VAFakePlayer.instance(w), EnumHand.MAIN_HAND, stack, f, 0, 0, 0);
                stack.damageItem(1, VAFakePlayer.instance(w));
            } catch (Exception e) {
                LogHelper.logException("That went wrong. Plz stahp it. Exception: %s", e, false);
            }
        }
        return stack;
    }
}
