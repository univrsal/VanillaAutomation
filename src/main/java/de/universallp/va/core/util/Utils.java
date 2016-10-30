package de.universallp.va.core.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.item.VAItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;
import java.util.List;

/**
 * Created by universallp on 22.03.2016 14:33 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class Utils {

    public static void drawWrappedString(String s, int x, int y, int maxWidth, Color c, boolean shadow, FontRenderer f) {

        List<String> lines = Arrays.asList(s.split("\\\\n"));

        List<String> descriptionLinesWrapped = new ArrayList<String>();
        for (String descriptionLine : lines) {
            List<String> textLines = f.listFormattedStringToWidth(descriptionLine, maxWidth);
            descriptionLinesWrapped.addAll(textLines);
        }

        for (String line : descriptionLinesWrapped) {
            line = line.replaceAll("7n", "\\\\n");
            f.drawString(line, x, y, c.getRGB());
            y += f.FONT_HEIGHT + 2;
        }
    }

    public static List<String> readDescFromStack(ItemStack s) {
        if (s.hasTagCompound()) {
            List<String> l = new ArrayList<String>();
            NBTTagCompound tag = s.getTagCompound();

            if (tag.hasKey("display")) {
                NBTTagCompound display = tag.getCompoundTag("display");
                if (display.getTagId("Lore") == 9) {
                    NBTTagList nbttaglist3 = display.getTagList("Lore", 8);

                    if (!nbttaglist3.hasNoTags()) {
                        for (int l1 = 0; l1 < nbttaglist3.tagCount(); ++l1) {
                            l.add((nbttaglist3.getStringTagAt(l1)));
                        }
                    }
                }
                return l;
            }
        }
        return null;
    }

    public static ItemStack withDescription(ItemStack s, List<String> desc) {
        ItemStack copy = s.copy();

        if (desc == null && copy.hasTagCompound()) {
            NBTTagCompound tag = copy.getTagCompound();
            if (tag.hasKey("display")) {
                tag.getCompoundTag("display").removeTag("Lore");
            }
            copy.setTagCompound(tag);
        } else {
            NBTTagCompound tag;
            if (copy.hasTagCompound())
                tag = copy.getTagCompound();
            else
                tag = new NBTTagCompound();


            NBTTagList list = new NBTTagList();

            for (int i = 0; i < desc.size(); i++) {
                NBTTagString line = new NBTTagString(ChatFormatting.RESET + "" + ChatFormatting.GRAY + desc.get(i));
                list.appendTag(line);
            }

            NBTTagCompound display;
            if (tag.hasKey("display")) {
                display = tag.getCompoundTag("display");
                display.setTag("Lore", list);
            } else {
                display = new NBTTagCompound();
                display.setTag("Lore", list);
            }
            tag.setTag("display", display);
            copy.setTagCompound(tag);
        }
        return copy;
    }

    public static int getRedstoneAnyDirection(World w, BlockPos pos) {
        int s = 0;
        for (EnumFacing f : EnumFacing.VALUES) {
            int s2 = w.getStrongPower(pos, f);
            s = s2 > s ? s2 : s;
        }
        return s;
    }

    public static int getReach(ItemStack stack) {
        if (stack.hasDisplayName()) {
            String name = stack.getDisplayName();
            String[] s = name.split(": ");

            if (s.length == 2) {
                try {
                    int reach = Integer.valueOf(s[1]);
                    return reach > ConfigHandler.DISPENSER_REACH_MAX ? ConfigHandler.DISPENSER_REACH_MAX : reach;
                } catch (NumberFormatException e) {
                    return 1;
                }
            }

        }
        return 1;
    }

    public static Vec3i extend(Vec3i v, int i) {
        return new Vec3i(v.getX() * i, v.getY() * i, v.getZ() * i);
    }

    public static void drawWrappedString(String s, int x, int y, int maxWidth, FontRenderer f) {
        drawWrappedString(s, x, y, maxWidth, new Color(130, 130, 130), false, f);
    }


    public static boolean carriesItem(Item item, EntityPlayer p) {
        if (p == null || item == null)
            return false;

        if (p.getHeldItemMainhand() != null && p.getHeldItemMainhand().getItem().equals(item))
            return true;

        if (p.getHeldItemOffhand() != null && p.getHeldItemOffhand().getItem().equals(item))
            return true;

        return false;
    }

    public static Set<String> getCarriedTools(EntityPlayer pl) {
        Set<String> toolClasses = new HashSet<String>();

        for (ItemStack stack : pl.inventory.mainInventory)
            if (stack != null && !stack.getItem().equals(VAItems.itemPokeStick))
                toolClasses.addAll(stack.getItem().getToolClasses(stack));

        return toolClasses;
    }

    public static float getFirstEfficientTool(EntityPlayer pl, IBlockState s) {
        for (ItemStack stack : pl.inventory.mainInventory)
            if (stack != null && !stack.getItem().equals(VAItems.itemPokeStick))
                for (String string : stack.getItem().getToolClasses(stack))
                    if (s.getBlock().isToolEffective(string, s))
                        return stack.getItem().getStrVsBlock(stack, s);


        return 1;
    }

    public static int getFirstEfficientToolSlot(EntityPlayer pl, IBlockState s) {
        for (int i = 0; i < pl.inventory.mainInventory.length; i++) {
            ItemStack stack = pl.inventory.mainInventory[i];

            if (stack != null && !stack.getItem().equals(VAItems.itemPokeStick)) {
                for (String string : stack.getItem().getToolClasses(stack))
                    if (s.getBlock().isToolEffective(string, s))
                        return i;
            }
        }

        return -1;
    }

    public static ItemStack decreaseStack(ItemStack stack, int amount) {
        if (stack.stackSize > amount)
            stack.stackSize -= amount;
        else
            return null;
        return stack;
    }

    public static EnumFacing getNextFacing(EnumFacing f) {
        if (f == EnumFacing.EAST)
            return EnumFacing.DOWN;
        else
            return EnumFacing.values()[f.ordinal() + 1];
    }

    public static String getModName(ItemStack s) {
        if (s == null)
            return "nope";

        String modID = s.getItem().getRegistryName().getResourceDomain();
        return modID == null ? "Minecraft" : modID;
    }

    public static void setConfigValue(File f, String find, String replace) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(f));
            String line;
            String input = "";

            while ((line = file.readLine()) != null) {
                if (line.contains(find))
                    line = find + replace;
                input += line + '\n';
            }

            file.close();

            FileOutputStream fileOut = new FileOutputStream(f);
            fileOut.write(input.getBytes());
            fileOut.close();

        } catch (Exception e) {
            LogHelper.logError("Problem writing config file.");
        }
    }

    public static boolean mouseInRect(int rectX, int rectY, int w, int h, int mouseX, int mouseY) {
        return mouseX > rectX && mouseX < rectX + w && mouseY > rectY && mouseY < rectY + h;
    }

    public static boolean canToolMineBlock(ItemStack toolStack, IBlockState state) {
        if (toolStack == null || state == null)
            return false;

        if (state.getBlock().getHarvestLevel(state) < 0)
            return false;

        String tool = state.getBlock().getHarvestTool(state);
        int harvesLevel = state.getBlock().getHarvestLevel(state);
        Set<String> toolClasses = toolStack.getItem().getToolClasses(toolStack);

        for (String toolClass : toolClasses) {
            if (toolClass.equals(tool) && toolStack.getItem().getHarvestLevel(toolStack, toolClass) >= harvesLevel)
                return true;
        }

        return false;
    }
}
