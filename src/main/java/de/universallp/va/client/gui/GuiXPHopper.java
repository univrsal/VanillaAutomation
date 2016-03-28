package de.universallp.va.client.gui;

import de.universallp.va.core.container.ContainerXPHopper;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.References;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

/**
 * Created by universallp on 28.03.2016 00:04.
 */
public class GuiXPHopper extends GuiContainer {

    private static final ResourceLocation guiTexture = new ResourceLocation(References.MOD_ID, "textures/gui/xphopper.png");

    private int percent = 0;
    private IInventory playerInventory;
    private TileXPHopper hopperInventory;

    public GuiXPHopper(InventoryPlayer playerInv, IInventory hopperInv) {
        super(new ContainerXPHopper(playerInv, hopperInv));
        this.ySize = 133;
        this.playerInventory = playerInv;
        this.hopperInventory = (TileXPHopper) hopperInv;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.fontRendererObj.drawString(this.hopperInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
        if (mouseX >= guiLeft + 128 && mouseX <= guiLeft + 128 + 4 && mouseY >= guiTop + 20 && mouseY <= guiTop + 36)
            drawCreativeTabHoveringText(percent + " %", mouseX - guiLeft, mouseY - guiTop);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        float percentage = ((float) hopperInventory.getProgress()) / TileXPHopper.xpPerBottle;
        this.percent = (int) (percentage * 100);
        int progress = (int) (percentage * 16);

        this.drawTexturedModalRect(i + 128, j + 20 + 16 - progress, 176, 16 - progress, 4, progress);
    }
}
