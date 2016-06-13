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
 * Created by universallp on 19.03.2016 17:52.
 */
public class PickaxeBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        World w = source.getBlockTileEntity().getWorld();
        IBlockState s = w.getBlockState(source.getBlockPos());
        EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);

        BlockPos dest = source.getBlockPos().add(f.getDirectionVec());

        if (breakBlock(w, dest, VAFakePlayer.instance(w), stack)) {
            stack.damageItem(1, VAFakePlayer.instance(w));
        }

        return stack;
    }

    public boolean breakBlock(World worldObj, BlockPos pos, VAFakePlayer fakePlayer, ItemStack tool) {
        fakePlayer.setItemInHand(tool);

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
