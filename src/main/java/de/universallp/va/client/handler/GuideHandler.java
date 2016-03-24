package de.universallp.va.client.handler;

import de.universallp.va.client.ClientProxy;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.References;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by universallp on 23.03.2016 18:32.
 */
public class GuideHandler {

    private static Map<Block, EnumEntry> vanillaEntries = new HashMap<Block, EnumEntry>();

    public static void initVanillaEntries() {
        vanillaEntries.put(Blocks.dispenser, EnumEntry.DISPENSER);
    }

    @SubscribeEvent
    public void drawGameOverlay(RenderGameOverlayEvent.Post e) {
        ItemStack main = FMLClientHandler.instance().getClientPlayerEntity().getHeldItem(EnumHand.MAIN_HAND);
        ItemStack off = FMLClientHandler.instance().getClientPlayerEntity().getHeldItem(EnumHand.OFF_HAND);

        if (e.type == RenderGameOverlayEvent.ElementType.ALL)
            if ((off != null && off.getItem().equals(VAItems.itemGuide)) || (main != null && main.getItem().equals(VAItems.itemGuide))) {
                RayTraceResult r = Minecraft.getMinecraft().objectMouseOver;

                if (r != null && r.typeOfHit == RayTraceResult.Type.BLOCK) {
                    Block b = FMLClientHandler.instance().getWorldClient().getBlockState(r.getBlockPos()).getBlock();

                    if (((b != null && b instanceof IEntryProvider) || vanillaEntries.containsKey(b)) && Minecraft.getMinecraft().currentScreen == null) {
                        EnumEntry entry;

                        if (b instanceof IEntryProvider)
                            entry = ((IEntryProvider) b).getEntry();
                        else
                            entry = vanillaEntries.get(b);

                        entry.getEntry().setPage(0);
                        ClientProxy.hoveredEntry = entry;

                        int x = e.resolution.getScaledWidth() / 2;
                        int y = e.resolution.getScaledHeight() / 2;
                        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(VAItems.itemGuide, 1), x, y);
                        Minecraft.getMinecraft().fontRendererObj.drawString(I18n.format(References.Local.GUIDE_LOOK), x + 18, y + 7, new Color(87, 145, 225).getRGB(), true);
                    } else
                        ClientProxy.hoveredEntry = null;
                }
            }
    }
}
