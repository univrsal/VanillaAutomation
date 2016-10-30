package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Created by universallp on 19.03.2016 17:52 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
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
        System.out.println(stack);

        if (breakBlock(w, dest, pl, stack)) {
            stack.damageItem(1, pl);
        }

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
        tool.getItem().onBlockStartBreak(tool, pos, fakePlayer);

        final IBlockState blockS = worldObj.getBlockState(pos);
        final Block block = blockS.getBlock();
        if (!Utils.canToolMineBlock(tool, blockS))
            return false;

        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldObj, pos, blockS, fakePlayer);
        if (MinecraftForge.EVENT_BUS.post(event)) return false;

        boolean canHarvest = block.canHarvestBlock(worldObj, pos, fakePlayer);

        block.onBlockHarvested(worldObj, pos, blockS, fakePlayer);
        boolean canRemove = block.removedByPlayer(blockS, worldObj, pos,fakePlayer, canHarvest);

        if (canRemove) {
            block.onBlockDestroyedByPlayer(worldObj, pos, blockS);
            TileEntity te = worldObj.getTileEntity(pos);
            if (canHarvest) {
                block.harvestBlock(worldObj, fakePlayer, pos, blockS, te, tool);
                worldObj.playEvent(2001, pos, Block.getIdFromBlock(block) + (block.getMetaFromState(blockS) << 12));
                return true;
            }
        }
        return false;
    }
}
