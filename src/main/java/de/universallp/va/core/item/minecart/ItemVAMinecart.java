package de.universallp.va.core.item.minecart;

import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.entity.EntityXPHopperMinecart;
import de.universallp.va.core.item.ItemVA;
import de.universallp.va.core.item.VAItems;
import net.minecraft.block.BlockRailBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by universallp on 10.04.2016 11:38.
 */
public class ItemVAMinecart extends ItemVA {

    private static VisualRecipe recipe;
    private CartType type;

    public ItemVAMinecart(String name, CartType type) {
        super(name);
        setCreativeTab(CreativeTabs.tabTransport);
        this.type = type;
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (BlockRailBase.isRailBlock(worldIn.getBlockState(pos))) {
            if (!worldIn.isRemote) {
                EntityMinecart entityminecart;
                if (type == CartType.XP)
                    entityminecart = new EntityXPHopperMinecart(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                else
                    entityminecart = new EntityXPHopperMinecart(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

                if (stack.hasDisplayName())
                    entityminecart.setCustomNameTag(stack.getDisplayName());

                worldIn.spawnEntityInWorld(entityminecart);
            }
            --stack.stackSize;
            return EnumActionResult.SUCCESS;
        }

        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public EnumEntry getEntry() {
        if (type == CartType.XP)
            return EnumEntry.XPHOPPER;
        else
            return EnumEntry.FILTERED_HOPPER;
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;
        if (type == CartType.XP) {
            ItemStack minecart = new ItemStack(Items.minecart, 1);
            ItemStack xpHopper = new ItemStack(VABlocks.xpHopper, 1);
            recipe = new VisualRecipe(new ItemStack[] { minecart, null, null, xpHopper }, new ItemStack(VAItems.itemCartXPHopper, 1), VisualRecipe.EnumRecipeType.SHAPED);
        } else {
            ItemStack minecart = new ItemStack(Items.minecart, 1);
            ItemStack filteredHopper = new ItemStack(VABlocks.filterHopper, 1);
            recipe = new VisualRecipe(new ItemStack[] { minecart, null, null, filteredHopper }, new ItemStack(VAItems.itemCartXPHopper, 1), VisualRecipe.EnumRecipeType.SHAPED);
        }

        return recipe;
    }

    @Override
    public void addRecipe() {
        if (type == CartType.XP)
            GameRegistry.addShapedRecipe(getRecipe().getResult(), "H", "M", 'H', VABlocks.xpHopper, 'M', Items.minecart);
        else
            GameRegistry.addShapedRecipe(getRecipe().getResult(), "H", "M", 'H', VABlocks.filterHopper, 'M', Items.minecart);
    }

    public enum CartType {
        FILTER,
        XP
    }
}
