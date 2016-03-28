package de.universallp.va.client.handler;

import com.google.common.base.Predicates;
import de.universallp.va.client.ClientProxy;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.References;
import de.universallp.va.core.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
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
        ItemStack heldItem = Utils.getCarriedItem(FMLClientHandler.instance().getClientPlayerEntity());

        if (e.type == RenderGameOverlayEvent.ElementType.ALL)
            if (heldItem != null && heldItem.getItem().equals(VAItems.itemGuide)) {
                Minecraft mc = Minecraft.getMinecraft();
                RayTraceResult r = mc.objectMouseOver;

                if (r != null)
                    if (r.typeOfHit == RayTraceResult.Type.BLOCK) {
                        Block b = FMLClientHandler.instance().getWorldClient().getBlockState(r.getBlockPos()).getBlock();

                        if (((b != null && b instanceof IEntryProvider) || vanillaEntries.containsKey(b)) && mc.currentScreen == null) {
                            EnumEntry entry;

                            if (b instanceof IEntryProvider)
                                entry = ((IEntryProvider) b).getEntry();
                            else
                                entry = vanillaEntries.get(b);

                            entry.getEntry().setPage(0);
                            ClientProxy.hoveredEntry = entry;

                            int x = e.resolution.getScaledWidth() / 2;
                            int y = e.resolution.getScaledHeight() / 2;
                            mc.getRenderItem().renderItemIntoGUI(new ItemStack(VAItems.itemGuide, 1), x, y);
                            mc.fontRendererObj.drawString(I18n.format(References.Local.GUIDE_LOOK), x + 18, y + 7, new Color(87, 145, 225).getRGB(), true);
                        } else {
                            Entity mouseOver = getMouseOver(e.partialTicks, 5, mc);
                            if (mouseOver != null && mouseOver instanceof EntityItem && mc.currentScreen == null) {
                                ItemStack stack = ((EntityItem) mouseOver).getEntityItem();

                                if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEntryProvider) {
                                    EnumEntry entry = ((IEntryProvider) stack.getItem()).getEntry();
                                    entry.getEntry().setPage(0);
                                    ClientProxy.hoveredEntry = entry;

                                    int x = e.resolution.getScaledWidth() / 2;
                                    int y = e.resolution.getScaledHeight() / 2;
                                    mc.getRenderItem().renderItemIntoGUI(new ItemStack(VAItems.itemGuide, 1), x, y);
                                    mc.fontRendererObj.drawString(I18n.format(References.Local.GUIDE_LOOK), x + 18, y + 7, new Color(87, 145, 225).getRGB(), true);
                                }
                            } else
                                ClientProxy.hoveredEntry = null;
                        }
                }
            }
    }

    /**
     * Stolen from EntityRenderer and optimized for this case
     */
    private Entity getMouseOver(float partialTicks, double distance, Minecraft mc) {
        Entity entity = mc.getRenderViewEntity();
        Entity pointedEntity = null;

        if (entity != null) {
            if (mc.theWorld != null) {
                Vec3d vec3 = entity.getPositionEyes(partialTicks);

                Vec3d vec31 = entity.getLook(partialTicks);
                Vec3d vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);

                float f = 1.0F;
                List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance).expand((double) f, (double) f, (double) f), Predicates.and(EntitySelectors.NOT_SPECTATING));
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
                            if (entity1 == entity.getRidingEntity() && !entity.canRiderInteract()) {
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
