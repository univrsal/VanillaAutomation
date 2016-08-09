package de.universallp.va.client.gui;

import de.universallp.va.client.gui.screen.ButtonIcon;
import de.universallp.va.core.container.ContainerAutoTrader;
import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.tile.TileAutoTrader;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import java.io.IOException;

/**
 * Created by universallp on 08.08.2016 21:32.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class GuiAutoTrader extends GuiContainer {

    private final int maxTrades;
    private final IInventory playerInv;
    private final TileAutoTrader autoTrader;
    private ButtonIcon btnLeft;
    private ButtonIcon btnRight;
    private int currentTrade = 0;

    public GuiAutoTrader(InventoryPlayer playerInv, TileAutoTrader trader) {
        super(new ContainerAutoTrader(playerInv, trader));
        maxTrades = trader.getField(1);
        currentTrade = trader.getField(0);
        ySize = 183;
        this.playerInv = playerInv;
        this.autoTrader = trader;
    }

    @Override
    public void initGui() {
        super.initGui();
        btnLeft = new ButtonIcon(0, guiLeft + 22, guiTop + 37, currentTrade > 0 ? ButtonIcon.IconType.ARROW_LEFT : ButtonIcon.IconType.ARROW_LEFT_DISABLED, false);
        btnRight = new ButtonIcon(1, guiLeft + 145, guiTop + 37, currentTrade < maxTrades ? ButtonIcon.IconType.ARROW_RIGHT : ButtonIcon.IconType.ARROW_RIGHT_DISABLED, false);
        buttonList.add(btnLeft);
        buttonList.add(btnRight);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        boolean enabled = currentTrade > 0;
        btnLeft.enabled = enabled;

        if (enabled && btnLeft.isMouseOver()) {
            btnLeft.setIcon(ButtonIcon.IconType.ARROW_LEFT_SELECTED);
        } else if (enabled) {
            btnLeft.setIcon(ButtonIcon.IconType.ARROW_LEFT);
        } else {
            btnLeft.setIcon(ButtonIcon.IconType.ARROW_LEFT_DISABLED);
        }

        enabled = currentTrade < maxTrades - 1;
        btnRight.enabled = enabled;

        if (enabled && btnRight.isMouseOver()) {
            btnRight.setIcon(ButtonIcon.IconType.ARROW_RIGHT_SELECTED);
        } else if (enabled) {
            btnRight.setIcon(ButtonIcon.IconType.ARROW_RIGHT);
        } else {
            btnRight.setIcon(ButtonIcon.IconType.ARROW_RIGHT_DISABLED);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            if (currentTrade - 1 >= 0) {
                currentTrade--;
                PacketHandler.sendToServer(new MessageSetFieldServer(0, currentTrade, autoTrader.getPos()));
            }
        } else if (button.id == 1) {
            if (currentTrade + 1 < maxTrades) {
                currentTrade++;
                PacketHandler.sendToServer(new MessageSetFieldServer(0, currentTrade, autoTrader.getPos()));
            }
        }

        super.actionPerformed(button);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.fontRendererObj.drawString(I18n.format(LibLocalization.GUI_AUTOTRADER), 8, 6, LibNames.TEXT_COLOR);
        this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, LibNames.TEXT_COLOR);
        this.fontRendererObj.drawString(autoTrader.getStringField(0), this.xSize / 2 - this.fontRendererObj.getStringWidth(autoTrader.getStringField(0)) / 2, 20, LibNames.TEXT_COLOR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LibResources.GUI_AUTOTRADER);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (!autoTrader.isTradingPossible() || !autoTrader.getIsTradePossible(currentTrade)) {
            this.drawTexturedModalRect(i + 84, j + 36, this.width + 6, 0, 25, 20);
            this.drawTexturedModalRect(i + 84, j + 66, this.width + 6, 0, 25, 20);
        }
    }
}
