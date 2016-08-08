package de.universallp.va.client.gui;

import de.universallp.va.client.gui.screen.ButtonIcon;
import de.universallp.va.core.container.ContainerAutoTrader;
import de.universallp.va.core.tile.TileAutoTrader;
import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

import java.io.IOException;

/**
 * Created by universallp on 08.08.2016 21:32.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class GuiAutoTrader extends GuiContainer {

    private final int maxTrades;
    private ButtonIcon btnLeft;
    private ButtonIcon btnRight;
    private int currentTrade = 0;

    public GuiAutoTrader(InventoryPlayer playerInv, TileAutoTrader trader) {
        super(new ContainerAutoTrader(playerInv, trader));
        maxTrades = trader.getField(1);
        currentTrade = trader.getField(0);
        ySize = 183;
    }

    @Override
    public void initGui() {
        super.initGui();

        btnLeft = new ButtonIcon(0, guiLeft, guiTop, currentTrade > 0 ? ButtonIcon.IconType.ARROW_LEFT : ButtonIcon.IconType.ARROW_LEFT_DISABLED);
        btnRight = new ButtonIcon(1, guiLeft, guiTop, currentTrade > maxTrades ? ButtonIcon.IconType.ARROW_RIGHT : ButtonIcon.IconType.ARROW_RIGHT_DISABLED);

        buttonList.add(btnLeft);
        buttonList.add(btnRight);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (btnLeft.enabled && btnLeft.isMouseOver()) {
            btnLeft.setIcon(ButtonIcon.IconType.ARROW_LEFT_SELECTED);
        } else if (btnLeft.enabled) {
            btnLeft.setIcon(ButtonIcon.IconType.ARROW_LEFT);
        }

        if (btnRight.enabled && btnLeft.isMouseOver()) {
            btnRight.setIcon(ButtonIcon.IconType.ARROW_RIGHT_SELECTED);
        } else if (btnRight.enabled) {
            btnRight.setIcon(ButtonIcon.IconType.ARROW_RIGHT);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LibResources.GUI_AUTOTRADER);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
