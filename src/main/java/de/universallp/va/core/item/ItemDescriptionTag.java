package de.universallp.va.core.item;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.universallp.va.client.gui.guide.Entries;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by universallp on 06.08.2016 16:23 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ItemDescriptionTag extends ItemVA {

    private static VisualRecipe recipe;

    protected ItemDescriptionTag() {
        super(LibNames.ITEM_DESCRIPTIONTAG);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public static boolean hasDescription(ItemStack stack) {
        return getDescription(stack) != null && getDescription(stack).size() > 0;
    }

    public static List<String> getDescription(ItemStack stack) {
        ArrayList<String> l = new ArrayList<String>();

        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("description")) {
                NBTTagCompound description = tag.getCompoundTag("description");
                if (description.getTagId("lines") == 9) {
                    NBTTagList nbttaglist3 = description.getTagList("lines", 8);

                    if (!nbttaglist3.hasNoTags()) {
                        for (int l1 = 0; l1 < nbttaglist3.tagCount(); ++l1) {
                            l.add(ChatFormatting.stripFormatting(nbttaglist3.getStringTagAt(l1)));
                        }
                    }
                }
            } else
                return null;
        }

        return l;
    }

    public static EnumTagMode getMode(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("tagmode"))
            return EnumTagMode.values()[stack.getTagCompound().getInteger("tagmode")];
        return EnumTagMode.NONE;
    }

    public static void setDescription(ItemStack stack, String name) {
        EnumTagMode mode = EnumTagMode.NONE;

        if (name.startsWith("++")) {
            name = name.substring(2);
            mode = EnumTagMode.ADD;
        } else if (name.startsWith("+")) {
            name = name.substring(1);
            mode = EnumTagMode.ADDBOTTOM;
        } else if (name.toLowerCase().equals("null")) {
            mode = EnumTagMode.CLEAR;
        }

        List desc = mode == EnumTagMode.ADD ? Arrays.asList(new String[] { name }) : Arrays.asList(name.split("\\\\n"));
        NBTTagCompound tag;
        if (stack.hasTagCompound())
            tag = stack.getTagCompound();
        else
            tag = new NBTTagCompound();


        NBTTagList list = new NBTTagList();

        for (int i = 0; i < desc.size(); i++) {
            NBTTagString line = new NBTTagString(ChatFormatting.RESET + "" + ChatFormatting.GRAY + desc.get(i));
            list.appendTag(line);
        }

        NBTTagCompound display;
        if (tag.hasKey("description")) {
            display = tag.getCompoundTag("description");
            display.setTag("lines", list);
        } else {
            display = new NBTTagCompound();
            display.setTag("lines", list);
        }
        tag.setTag("description", display);
        tag.setInteger("tagmode", mode.ordinal());
        stack.setTagCompound(tag);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt.hasKey("display") && nbt.getCompoundTag("display").hasKey("Name")) {
                setDescription(stack, stack.getDisplayName());
                stack.clearCustomName();
            }
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (hasDescription(stack)) {
            tooltip.add(I18n.format(LibLocalization.TIP_DESCRIPTIONMODE) + " " + getMode(stack).getLocalizedName());
            tooltip.add(I18n.format(LibLocalization.TIP_DESCRIPTION));
            tooltip.addAll(getDescription(stack));
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public int getEntryID() {
        return Entries.DESCRIPTION_TAG.getEntryID();
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;

        ItemStack string = new ItemStack(Items.STRING, 1);
        ItemStack paper = new ItemStack(Items.PAPER, 1);
        ItemStack ink = new ItemStack(Items.DYE, 1);

        recipe = new VisualRecipe(new ItemStack[] { string, ink, ItemStack.EMPTY,
                paper, paper, ItemStack.EMPTY,
                ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY }, new ItemStack(this, 3), VisualRecipe.EnumRecipeType.SHAPED);
        return recipe;
    }


    public enum EnumTagMode {
        NONE(LibLocalization.TAGMODE_NONE),
        ADD(LibLocalization.TAGMODE_ADD),
        ADDBOTTOM(LibLocalization.TAGMODE_ADDBOTTOM),
        CLEAR(LibLocalization.TAGMODE_CLEAR);

        private String unlocalizedName;

        EnumTagMode(String ulname) {
            this.unlocalizedName = ulname;
        }

        public String getLocalizedName() {
            return I18n.format(unlocalizedName);
        }
    }
}
