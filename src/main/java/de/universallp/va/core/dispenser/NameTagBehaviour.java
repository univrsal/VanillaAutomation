package de.universallp.va.core.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by universallp on 21.06.2016 18:20.
 */
public class NameTagBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        if (stack.hasDisplayName()) {
            EnumFacing f = BlockDispenser.getFacing(source.getBlockMetadata());
            BlockPos dest = source.getBlockPos().add(f.getDirectionVec());
            World w = source.getWorld();

            List<EntityLiving> entityLivingList = w.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(dest));

            if (entityLivingList.size() > 0) {
                if (entityLivingList.get(0).hasCustomName() && entityLivingList.get(0).getCustomNameTag().equals(stack.getDisplayName()))
                    return stack;
                entityLivingList.get(0).setCustomNameTag(stack.getDisplayName());
                stack.stackSize--;
            }
        }
        return stack;
    }
}
