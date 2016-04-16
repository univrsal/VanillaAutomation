package de.universallp.va.client.gui;

import de.universallp.va.core.container.ContainerXPHopper;
import de.universallp.va.core.entity.EntityMinecartXPHopper;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * Created by universallp on 28.03.2016 00:04.
 */
public class GuiXPHopper extends GuiContainer {

    private int percent = 0;
    private IInventory playerInventory;
    private TileXPHopper hopperInventory;
    private EntityMinecartXPHopper cart;

    public GuiXPHopper(InventoryPlayer playerInv, IInventory hopperInv) {
        super(new ContainerXPHopper(playerInv, hopperInv));
        this.ySize = 133;
        this.playerInventory = playerInv;
        if (hopperInv instanceof TileXPHopper)
            this.hopperInventory = (TileXPHopper) hopperInv;
        else
            cart = (EntityMinecartXPHopper) hopperInv;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String displayName = hopperInventory == null ? cart.getDisplayName().getFormattedText() : hopperInventory.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(displayName, 8, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
        if (mouseX >= guiLeft + 128 && mouseX <= guiLeft + 128 + 4 && mouseY >= guiTop + 20 && mouseY <= guiTop + 36)
            drawCreativeTabHoveringText(percent + " %", mouseX - guiLeft, mouseY - guiTop);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LibResources.GUI_XPHOPPER);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int p = hopperInventory == null ? cart.getProgress() : hopperInventory.getProgress();

        float percentage = ((float) p) / TileXPHopper.xpPerBottle;
        this.percent = (int) (percentage * 100);
        int progress = (int) (percentage * 16);

        this.drawTexturedModalRect(i + 128, j + 20 + 16 - progress, 176, 16 - progress, 4, progress);
    }
}
