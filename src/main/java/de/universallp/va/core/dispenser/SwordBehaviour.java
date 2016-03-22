package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
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
                }
            }
        }
        return stack;
    }

    public void attackEntity(EntityLiving e, ItemStack tool, VAFakePlayer p) {
        p.setItemInHand(tool);
        float f = (float)p.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();

        float f1 = 0.0F;

            f1 = EnchantmentHelper.getModifierForCreature(p.getHeldItemMainhand(), p.getCreatureAttribute());


        float f2 = p.getCooledAttackStrength(0.5F);
        f = f * (0.2F + f2 * f2 * 0.8F);
        f1 = f1 * f2;
        p.resetCooldown();
        f = f + f1;
        System.out.println(f);
        e.attackEntityFrom(DamageSource.causePlayerDamage(p), f);

//        p.attackTargetEntityWithCurrentItem(e);
        tool.damageItem(1, p);
    }
}
