package de.universallp.va.core.util;

import com.google.common.base.Predicates;
import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.item.VAItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

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
                            l.add(nbttaglist3.getStringTagAt(l1));
                        }
                    }
                }
                return l;
            }
        }
        return null;
    }

    public static ItemStack withDescription(ItemStack s, List<String> desc) {
        NBTTagCompound tag;
        if (s.hasTagCompound())
            tag = s.getTagCompound();
        else
            tag = new NBTTagCompound();


        NBTTagList list = new NBTTagList();

        for (int i = 0; i < desc.size(); i++) {
            NBTTagString line = new NBTTagString(desc.get(i));
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
        s.setTagCompound(tag);

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

    public static ItemStack getCarriedItem(EntityPlayer p) {
        ItemStack s = p.getHeldItem(EnumHand.MAIN_HAND);
        if (s == null)
            s = p.getHeldItem(EnumHand.OFF_HAND);

        return s;
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

    public static boolean canToolMineBlock(ItemStack toolStack, IBlockState state) {
        if (toolStack == null || state == null)
            return false;

        String tool = state.getBlock().getHarvestTool(state);
        int harvesLevel = state.getBlock().getHarvestLevel(state);
        Set<String> toolClasses = toolStack.getItem().getToolClasses(toolStack);

        if (tool != null)
            for (String toolClass : toolClasses) {
                if (toolClass.equals(tool) && toolStack.getItem().getHarvestLevel(toolStack, toolClass) >= harvesLevel)
                    return true;
            }
        else
            return true; // No provided toolclass = anything can mine it

        return false;
    }

    public static Entity getMouseOver(float partialTicks, double distance, EntityPlayer player) {

        Entity pointedEntity = null;

        if (player != null) {
            if (player.worldObj != null) {
                Vec3d vec3 = player.getPositionEyes(partialTicks);

                Vec3d vec31 = player.getLook(partialTicks);
                Vec3d vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);

                float f = 1.0F;
                List<Entity> list = player.worldObj.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().addCoord(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance).expand((double) f, (double) f, (double) f), Predicates.and(EntitySelectors.NOT_SPECTATING));
                double d2 = distance;

                for (Entity entity1 : list) {
                    float f1 = entity1.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double) f1, (double) f1, (double) f1);
                    RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                    if (axisalignedbb.isVecInside(vec3)) {
                        if (d2 >= 0.0D) {
                            pointedEntity = entity1;
                            d2 = 0.0D;
                        }
                    } else if (movingobjectposition != null) {
                        double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                        if (d3 < d2 || d2 == 0.0D) {
                            if (entity1 == player.getRidingEntity() && !player.canRiderInteract()) {
                                if (d2 == 0.0D) {
                                    pointedEntity = entity1;
                                }
                            } else {
                                pointedEntity = entity1;
                                d2 = d3;
                            }
                        }
                    }
                }
            }
        }

        return pointedEntity;
    }
}
