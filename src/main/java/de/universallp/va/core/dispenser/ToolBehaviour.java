package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by universallp on 19.03.2016 17:52 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ToolBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        World w = source.getBlockTileEntity().getWorld();
        IBlockState s = w.getBlockState(source.getBlockPos());
        EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);

        BlockPos dest = source.getBlockPos().add(Utils.extend(f.getDirectionVec(), Utils.getReach(stack)));
        VAFakePlayer pl = VAFakePlayer.instance(source.getWorld());
        pl.setPosition(source.getX(), source.getY() - pl.getEyeHeight(), source.getZ());
        pl.rotationYaw = getYaw(f);
        pl.rotationPitch = getPitch(f);

        breakBlock(w, dest, pl, stack);

        return stack;
    }

    private float getYaw(EnumFacing f) {
        switch (f) {
            case SOUTH:
                return 0;
            case WEST:
                return 90;
            case NORTH:
                return 180;
            case EAST:
                return -90;
            case DOWN:
                return 0;
            case UP:
                return 0;
            default:
                return 0;
        }
    }

    private float getPitch(EnumFacing f) {
        switch (f) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return 0;
            case DOWN:
                return 90;
            case UP:
                return -90;
            default:
                return 0;
        }
    }

    private boolean breakBlock(World worldObj, BlockPos pos, VAFakePlayer fakePlayer, ItemStack tool) {
        fakePlayer.setItemInHand(tool);
        final IBlockState bS = worldObj.getBlockState(pos);
        final float f = bS.getBlock().getPlayerRelativeBlockHardness(bS, fakePlayer, worldObj, pos);

        if (f > 0)
            return fakePlayer.interactionManager.tryHarvestBlock(pos);
        else
            return false;
    }
}
