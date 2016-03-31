package de.universallp.va.core.item;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by universallp on 24.03.2016 17:45.
 */
public class ItemPokeStick extends ItemVA {

    private static VisualRecipe recipe;
    private Set<String> toolClasses = new HashSet<String>();
    private float toolEfficiency = 1;
    private int toolToDamage = -1;
    private int harvestLevel = 0;

    protected ItemPokeStick() {
        super(LibNames.ITEM_POKESTICK);
        setCreativeTab(CreativeTabs.tabTools);
        setMaxStackSize(1);
        setMaxDamage(120);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof EntityPlayer) {
            ItemStack heldItem = Utils.getCarriedItem((EntityPlayer) entityIn);
            EntityPlayer pl = (EntityPlayer) entityIn;
            if (heldItem != null && heldItem.getItem() != null && heldItem.getItem().equals(VAItems.itemPokeStick)) {
                VanillaAutomation.proxy.setReach(pl, 15);
                toolClasses = Utils.getCarriedTools(pl);
                RayTraceResult r = getMovingObjectPositionFromPlayer(worldIn, pl, false);
                if (r != null && r.typeOfHit == RayTraceResult.Type.BLOCK) {
                    toolEfficiency = Utils.getFirstEfficientTool(pl, entityIn.worldObj.getBlockState(r.getBlockPos()));
                    toolToDamage = Utils.getFirstEfficientToolSlot(pl, entityIn.worldObj.getBlockState(r.getBlockPos()));

                    if (toolToDamage > -1) {
                        ItemStack tool = pl.inventory.mainInventory[toolToDamage];
                        if (tool != null && tool.getItem() != null) {
                            int h = 0;
                            for (String s : tool.getItem().getToolClasses(tool)) {
                                h = tool.getItem().getHarvestLevel(tool, s);
                                harvestLevel = h > harvestLevel ? h : harvestLevel;
                            }
                        }
                    }
                }
            } else
                VanillaAutomation.proxy.setReach((EntityLivingBase) entityIn, 5);
        }

        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ActionResult<ItemStack> result = super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
        if (!playerIn.capabilities.isCreativeMode && result.getType() != EnumActionResult.FAIL)
            itemStackIn.damageItem(1, playerIn);
        return result;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        return toolEfficiency;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return toolClasses.size() > 0 ? toolClasses : super.getToolClasses(stack);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        return harvestLevel;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer pl = (EntityPlayer) entityLiving;
            if (pl.capabilities.isCreativeMode)
                return super.onBlockDestroyed(stack, worldIn, blockIn, pos, entityLiving);
            else {
                stack.damageItem(1, pl);
                toolToDamage = Utils.getFirstEfficientToolSlot(pl, pl.worldObj.getBlockState(pos));
                if (toolToDamage > -1) {
                    // Damage the efficient tool
                    ItemStack tool = pl.inventory.mainInventory[toolToDamage];
                    tool.damageItem(1, entityLiving);
                    if (tool.stackSize == 0)
                        pl.inventory.mainInventory[toolToDamage] = null;
                }
            }

        } else
            stack.damageItem(1, entityLiving);
        return super.onBlockDestroyed(stack, worldIn, blockIn, pos, entityLiving);
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;

        ItemStack blaze = new ItemStack(Items.blaze_rod, 1);
        ItemStack glowstone = new ItemStack(Items.glowstone_dust, 1);
        recipe = new VisualRecipe(new ItemStack[]{null, null, glowstone,
                null, blaze, null,
                blaze, null, null}, new ItemStack(this, 1), VisualRecipe.EnumRecipeType.SHAPED);

        return recipe;
    }

    @Override
    public EnumEntry getEntry() {
        return EnumEntry.POKE_STICK;
    }
}
