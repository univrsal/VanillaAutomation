package de.universallp.va.core.util;

import de.universallp.va.core.item.VAItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;
import java.util.List;

/**
 * Created by universallp on 22.03.2016 14:33.
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
            f.drawString(line, x, y, c.getRGB());
            y += f.FONT_HEIGHT + 2;
        }
    }

    public static void drawWrappedString(String s, int x, int y, int maxWidth, FontRenderer f) {
        drawWrappedString(s, x, y, maxWidth, new Color(130, 130, 130), false, f);
    }

    public static ItemStack getCarriedItem(EntityPlayer p) {
        ItemStack s = p.getHeldItem(EnumHand.MAIN_HAND);
        if (s == null)
            s = p.getHeldItem(EnumHand.OFF_HAND);

        return s;
    }

    public static Set<String> getCarriedTools(EntityPlayer pl) {
        Set<String> toolClasses = new HashSet<String>();

        for (ItemStack stack : pl.inventory.mainInventory)
            if (stack != null && stack.getItem() != null)
                toolClasses.addAll(stack.getItem().getToolClasses(stack));

        return toolClasses;
    }

    public static float getFirstEfficientTool(EntityPlayer pl, IBlockState s) {
        for (ItemStack stack : pl.inventory.mainInventory)
            if (stack != null && stack.getItem() != null && !stack.getItem().equals(VAItems.itemPokeStick))
                for (String string : stack.getItem().getToolClasses(stack))
                    if (s.getBlock().isToolEffective(string, s))
                        return stack.getItem().getStrVsBlock(stack, s);


        return 1;
    }

    public static int getFirstEfficientToolSlot(EntityPlayer pl, IBlockState s) {
        for (int i = 0; i < pl.inventory.mainInventory.length; i++) {
            ItemStack stack = pl.inventory.mainInventory[i];

            if (stack != null && stack.getItem() != null && !stack.getItem().equals(VAItems.itemPokeStick)) {
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
            // input the file content to the String "input"
            BufferedReader file = new BufferedReader(new FileReader("notes.txt"));
            String line;
            String input = "";

            while ((line = file.readLine()) != null) input += line + '\n';

            file.close();

            // this if structure determines whether or not to replace "0" or "1"
            if (input.contains(find)) {
                input = input.replace(find, find + replace);
            }

            // write the new String with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("notes.txt");
            fileOut.write(input.getBytes());
            fileOut.close();

        } catch (Exception e) {
            LogHelper.logError("Problem writing config file.");
        }
    }
}
