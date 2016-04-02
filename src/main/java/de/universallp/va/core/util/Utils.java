package de.universallp.va.core.util;

import de.universallp.va.core.item.VAItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameData;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by universallp on 22.03.2016 14:33.
 */
public class Utils {

    public static void drawWrappedString(String s, int x, int y, int maxWidth, Color c, boolean shadow, FontRenderer f) {
        List<String> words = new ArrayList<String>();

        Collections.addAll(words, s.split(" "));

        int line = 0;
        int wordIndex = 0;
        String currentLine = "";

        main: while (wordIndex < words.size()) {
            while (wordIndex < words.size() && f.getStringWidth(currentLine + " " + words.get(wordIndex)) <= maxWidth) {
                if (f.getStringWidth(words.get(wordIndex)) >= maxWidth)
                    break main;

                if (words.get(wordIndex).equals("/n")) {
                    f.drawString(currentLine, x, y + (f.FONT_HEIGHT + 2) * line, c.getRGB(), shadow);
                    currentLine = "";
                    line++;
                    wordIndex++;
                    continue main;
                }

                currentLine += " " + words.get(wordIndex);
                wordIndex++;
            }

            f.drawString(currentLine, x, y + (f.FONT_HEIGHT + 2) * line, c.getRGB(), shadow);
            currentLine = "";
            line++;
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

        String itemName = GameData.getItemRegistry().getNameForObject(s.getItem()).getResourceDomain();
        ModContainer mod = Loader.instance().getIndexedModList().get(itemName.split(":")[0]);

        return mod == null ? "Minecraft" : mod.getName();
    }
}
