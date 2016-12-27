package de.universallp.va.core.item;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.ClientProxy;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.libs.LibGuiIDs;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by universallp on 21.03.2016 15:43 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class ItemGuide extends ItemVA {

    private static VisualRecipe recipe;

    public ItemGuide() {
        super(LibNames.ITEM_GUIDE);
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_GUIDE, worldIn, 0, 0, 0);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!VanillaAutomation.proxy.isServer() && ClientProxy.hoveredEntry != null) {
            player.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_GUIDE, world, 0, 0, 0);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(TextFormatting.GRAY + I18n.format(LibLocalization.GUIDE_DESC));
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;

        ItemStack book = new ItemStack(Items.BOOK, 1);
        ItemStack piston = new ItemStack(Blocks.PISTON, 1);

        recipe = new VisualRecipe(new ItemStack[]{book, piston}, new ItemStack(this, 1), VisualRecipe.EnumRecipeType.SHAPELESS);
        return recipe;
    }
}
