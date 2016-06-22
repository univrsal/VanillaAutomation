package de.universallp.va.client.gui;

import de.universallp.va.core.container.ContainerAdvancedAnvil;
import de.universallp.va.core.tile.TileAdvancedAnvil;
import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * Created by universallp on 22.06.2016 19:28.
 */
public class GuiAdvancedAnvil extends GuiContainer {

    private IInventory playerInventory;
    private TileAdvancedAnvil anvilInventory;
    private GuiTextField renameText;

    public GuiAdvancedAnvil(InventoryPlayer playerInv, IInventory anvilInv) {
        super(new ContainerAdvancedAnvil(playerInv, anvilInv));
        this.ySize = 215;
        this.playerInventory = playerInv;
        this.anvilInventory = (TileAdvancedAnvil) anvilInv;
        GuiRepair
    }

    @Override
    public void initGui() {
        super.initGui();
//        renameText = new GuiTextField()
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LibResources.GUI_ADVANCED_ANVIL);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }
}
