package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.Utils;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

/**
 * Created by universallp on 20.03.2016 18:14 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ShearBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        TileEntityDispenser te = source.getBlockTileEntity();
        IBlockState s = te.getWorld().getBlockState(source.getBlockPos());
        EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);

        BlockPos destination = source.getBlockPos().add(Utils.extend(f.getDirectionVec(), Utils.getReach(stack)));
        List<EntitySheep> sheeps = te.getWorld().getEntitiesWithinAABB(EntitySheep.class, new AxisAlignedBB(destination));

        if (sheeps != null && sheeps.size() > 0) {
            for (EntitySheep sheep : sheeps) {
                if (sheep.isShearable(stack, te.getWorld(), source.getBlockPos())) {
                    List<ItemStack> drops = sheep.onSheared(stack, te.getWorld(), source.getBlockPos(),
                            EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

                    Random rand = new java.util.Random();

                    for(ItemStack item : drops) {
                        EntityItem ent = sheep.entityDropItem(item, 1.0F);
                        ent.motionY += rand.nextFloat() * 0.05F;
                        ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                        ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    }

                    stack.damageItem(1, sheep);
                }
            }
        } else {
            List<EntityMooshroom> moos = te.getWorld().getEntitiesWithinAABB(EntityMooshroom.class, new AxisAlignedBB(destination));

            if (moos != null && moos.size() > 0) {
                for (EntityMooshroom moo : moos) {
                    if (moo.isShearable(stack, te.getWorld(), source.getBlockPos())) {
                        List<ItemStack> drops = moo.onSheared(stack, te.getWorld(), source.getBlockPos(),
                                EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

                        Random rand = new java.util.Random();

                        for(ItemStack item : drops) {
                            EntityItem ent = moo.entityDropItem(item, 1.0F);
                            ent.motionY += rand.nextFloat() * 0.05F;
                            ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                            ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                        }

                        stack.damageItem(1, moo);
                    }
                }
            }
        }

        return stack;
    }
}
