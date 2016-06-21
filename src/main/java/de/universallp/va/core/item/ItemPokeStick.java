package de.universallp.va.core.item;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by universallp on 24.03.2016 17:45.
 */
public class ItemPokeStick extends ItemVA {

    private static VisualRecipe recipe;
    private Set<String> toolClasses = new HashSet<String>();

    protected ItemPokeStick() {
        super(LibNames.ITEM_POKESTICK);
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);

        if (ConfigHandler.POKE_STICK_DURABILITY > -1)
            setMaxDamage(ConfigHandler.POKE_STICK_DURABILITY);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof EntityPlayer) {
            ItemStack heldItem = Utils.getCarriedItem((EntityPlayer) entityIn);
            EntityPlayer pl = (EntityPlayer) entityIn;

            if (heldItem != null && heldItem.getItem().equals(VAItems.itemPokeStick)) {
                VanillaAutomation.proxy.setReach(pl, 5 + ConfigHandler.POKE_STICK_RANGE);
                RayTraceResult r = rayTrace(worldIn, pl, false);

                if (r != null && r.typeOfHit == RayTraceResult.Type.BLOCK) {
                    IBlockState bS = entityIn.worldObj.getBlockState(r.getBlockPos());
                    int toolToDamage = Utils.getFirstEfficientToolSlot(pl, bS);
                    int harvestLevel = 0;

                    if (toolToDamage > -1) {
                        ItemStack tool = pl.inventory.mainInventory[toolToDamage];
                        if (tool != null) {
                            int h;
                            for (String s : tool.getItem().getToolClasses(tool)) {
                                h = tool.getItem().getHarvestLevel(tool, s);
                                harvestLevel = h > harvestLevel ? h : harvestLevel;
                            }
                        }
                    }

                    NBTTagCompound newTag = writeToolInfoToNBT(stack.getTagCompound(), Utils.getCarriedTools(pl), Utils.getFirstEfficientTool(pl, bS), toolToDamage, harvestLevel);
                    if (!stack.hasTagCompound() || !newTag.equals(stack.getTagCompound()))
                        stack.setTagCompound(newTag);
                }
            } else
                VanillaAutomation.proxy.setReach((EntityLivingBase) entityIn, 5);
        }

        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (itemStackIn.getItemDamage() > 0 && ConfigHandler.POKE_STICK_DURABILITY == -1) {
            itemStackIn.setItemDamage(0);
        }
        ActionResult<ItemStack> result = super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
        if (!playerIn.capabilities.isCreativeMode && result.getType() != EnumActionResult.FAIL)
            itemStackIn.damageItem(1, playerIn);
        return result;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (stack.getItemDamage() > 0 && ConfigHandler.POKE_STICK_DURABILITY == -1) {
            tooltip.add(I18n.format(LibLocalization.TIP_CONVERT));
        }

        if (stack.hasTagCompound()) {
            Set<String> tools = readToolClasses(stack.getTagCompound());
            if (tools.size() > 0) {
                if (GuiScreen.isShiftKeyDown()) {
                    tooltip.add(I18n.format(LibLocalization.TIP_IMITATES));

                    for (String tool : tools) {
                        String format = tool.substring(0, 1).toUpperCase() + tool.substring(1);
                        tooltip.add(" - " + format);
                    }
                } else
                    tooltip.add(I18n.format(LibLocalization.TIP_HOLDSHIFT));
            }
        }

        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        return readToolEfficiency(stack.getTagCompound());
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        if (stack.hasTagCompound() && readToolClasses(stack.getTagCompound()).size() > 0)
            return readToolClasses(stack.getTagCompound());
        else
            return super.getToolClasses(stack);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        int hL = state.getBlock().getHarvestLevel(state);
        String tool = state.getBlock().getHarvestTool(state);
        return getHarvestLevel(stack, "") >= hL && (getToolClasses(stack).contains(tool) || tool == null);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return true; // Actual calculations will be done in the other method
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        return readHarvestLevel(stack.getTagCompound());
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer pl = (EntityPlayer) entityLiving;
            if (pl.capabilities.isCreativeMode)
                return super.onBlockDestroyed(stack, worldIn, blockIn, pos, entityLiving);
            else {
                stack.damageItem(1, pl);
                if (ConfigHandler.USE_TOOLS) {
                    int toolToDamage = readToolToDamage(stack.getTagCompound());
                    if (toolToDamage > -1) {
                        // Damage the efficient tool
                        ItemStack tool = pl.inventory.mainInventory[toolToDamage];
                        tool.damageItem(1, entityLiving);
                        if (tool.stackSize == 0)
                            pl.inventory.mainInventory[toolToDamage] = null;
                    }
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

        ItemStack blaze = new ItemStack(Items.BLAZE_ROD, 1);
        ItemStack glowstone = new ItemStack(Items.GLOWSTONE_DUST, 1);
        recipe = new VisualRecipe(new ItemStack[]{null, null, glowstone,
                null, blaze, null,
                blaze, null, null}, new ItemStack(this, 1), VisualRecipe.EnumRecipeType.SHAPED);

        return recipe;
    }

    @Override
    public EnumEntry getEntry() {
        return EnumEntry.POKE_STICK;
    }

    private Set<String> readToolClasses(NBTTagCompound tag) {
        Set<String> s = new HashSet<String>();
        if (tag.hasKey("toolClasses")) {
            NBTTagCompound toolTag = tag.getCompoundTag("toolClasses");
            int size = toolTag.getInteger("toolCount");

            for (int i = 0; i < size; i++) {
                s.add(toolTag.getString("tool" + i));
            }
        }
        return s;
    }

    private NBTTagCompound writeToolInfoToNBT(NBTTagCompound tag, Set<String> s, float toolEfficiency, int toolToDamage, int harvestLevel) {
        int i = 0;
        NBTTagCompound toolTag = new NBTTagCompound();

        for (String string : s) {
            toolTag.setString("tool" + i, string);
            i++;
        }

        toolTag.setInteger("toolCount", i);
        toolTag.setFloat("toolEfficiency", toolEfficiency);
        toolTag.setInteger("toolToDamage", toolToDamage);
        toolTag.setInteger("toolHarvestLevel", harvestLevel);
        if (tag == null)
            tag = new NBTTagCompound();

        tag.setTag("toolClasses", toolTag);
        return tag;
    }

    private float readToolEfficiency(NBTTagCompound tag) {
        if (tag != null && tag.hasKey("toolClasses"))
            return tag.getCompoundTag("toolClasses").getFloat("toolEfficiency");
        else
            return 1F;
    }

    private int readHarvestLevel(NBTTagCompound tag) {
        if (tag != null && tag.hasKey("toolClasses"))
            return tag.getCompoundTag("toolClasses").getInteger("toolHarvestLevel");
        else
            return 1;
    }

    private int readToolToDamage(NBTTagCompound tag) {
        if (tag != null && tag.hasKey("toolClasses"))
            return tag.getCompoundTag("toolClasses").getInteger("toolToDamage");
        else
            return -1;
    }
}
