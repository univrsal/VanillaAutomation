package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;

/**
 * Created by universallp on 19.03.2016 19:02.
 */
public class SwordBehaviour implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        TileEntityDispenser te = source.getBlockTileEntity();
        IBlockState s = te.getWorld().getBlockState(source.getBlockPos());
        EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);

        BlockPos destination = source.getBlockPos().add(f.getDirectionVec());
        List<EntityLiving> mobs = te.getWorld().getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(destination));

        if (mobs != null && mobs.size() > 0) {
            for (EntityLiving l : mobs) {
                if (!l.isDead) {
                    attackEntity(l, stack, VAFakePlayer.instance(te.getWorld()));
                    break;
                }
            }
        }
        return stack;
    }

    public void attackEntity(EntityLiving e, ItemStack tool, VAFakePlayer p) {
        p.setItemInHand(tool);
        if (tool != null && tool.getItem() instanceof ItemSword) {
            p.resetCooldown();
            float f = ((ItemSword) tool.getItem()).getDamageVsEntity();
            float f1 = EnchantmentHelper.getModifierForCreature(tool, e.getCreatureAttribute());
            float f3 = (float) p.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            System.out.println(f1);
            e.attackEntityFrom(DamageSource.causePlayerDamage(p), f + f1 + f3 + 3);

            int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.fireAspect, tool);

            if (j > 0 && !e.isBurning()) {
                e.setFire(j);
            }

            tool.damageItem(1, p);
        }
    }
}
