package de.universallp.va.client.gui;

import de.universallp.va.client.gui.screen.ButtonIcon;
import de.universallp.va.client.gui.screen.ButtonLabel;
import de.universallp.va.core.container.ContainerFilteredHopper;
import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.tile.TileFilteredHopper;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.block.BlockCake;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import java.io.IOException;

/**
 * Created by universallp on 31.03.2016 16:05 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class GuiFilteredHopper extends GuiContainer {

    private IInventory playerInventory;
    private TileFilteredHopper hopperInventory;

    private ButtonIcon btnIco;
    private ButtonLabel btnMatchMeta;
    private ButtonLabel btnMatchNBT;
    private ButtonLabel btnMatchMod;

    public GuiFilteredHopper(InventoryPlayer playerInv, IInventory hopperInv) {
        super(new ContainerFilteredHopper(playerInv, hopperInv));
        this.ySize = 153;
        this.playerInventory = playerInv;
        this.hopperInventory = (TileFilteredHopper) hopperInv;
    }

    @Override
    public void initGui() {
        super.initGui();
        btnIco = new ButtonIcon(0, guiLeft + 136, guiTop + 38, ButtonIcon.IconType.values()[hopperInventory.getField(0)]);
        int xOffset = fontRenderer.getStringWidth(I18n.format(LibLocalization.BTN_MOD)) + 21;
        btnMatchMeta = new ButtonLabel(LibLocalization.BTN_META, ButtonIcon.IconType.values()[2 + hopperInventory.getField(1)], 1, guiLeft - xOffset, guiTop + 12);
        btnMatchNBT = new ButtonLabel(LibLocalization.BTN_NBT, ButtonIcon.IconType.values()[2 + hopperInventory.getField(2)], 2, guiLeft - xOffset, guiTop + 24);
        btnMatchMod = new ButtonLabel(LibLocalization.BTN_MOD, ButtonIcon.IconType.values()[2 + hopperInventory.getField(3)], 3, guiLeft - xOffset, guiTop + 36);

        buttonList.add(btnIco);
        buttonList.add(btnMatchMeta);
        buttonList.add(btnMatchNBT);
        buttonList.add(btnMatchMod);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(I18n.format(LibLocalization.GUI_FILTEREDHOPPER), 8, 6, LibNames.TEXT_COLOR);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, LibNames.TEXT_COLOR);
        this.fontRenderer.drawString(I18n.format(LibLocalization.GUI_FILTER), 13, 44, LibNames.TEXT_COLOR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LibResources.GUI_FILTEREDHOPPER);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        int xOffset = fontRenderer.getStringWidth(I18n.format(LibLocalization.BTN_MOD)) + 21;
        drawTexturedModalRect(guiLeft - xOffset, guiTop + 5, 0, this.ySize, xOffset, 58);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        int fieldID = button.id;
        int fieldValue = 5;

        if (button.id == 0) {
            fieldValue = btnIco.getIcon() == ButtonIcon.IconType.BLACKLIST ? 0 : 1;
            PacketHandler.sendToServer(new MessageSetFieldServer(0, fieldValue, hopperInventory.getPos()));
            btnIco.setIcon(btnIco.getIcon().toggle());
        } else if (button.id == 1) {
            fieldValue = btnMatchMeta.getIcon() == ButtonIcon.IconType.CHECKED ? 1 : 0;
            PacketHandler.sendToServer(new MessageSetFieldServer(1, fieldValue, hopperInventory.getPos()));
            btnMatchMeta.setIcon(btnMatchMeta.getIcon().toggle());
        } else if (button.id == 2) {
            fieldValue = btnMatchNBT.getIcon() == ButtonIcon.IconType.CHECKED ? 1 : 0;
            PacketHandler.sendToServer(new MessageSetFieldServer(2, fieldValue, hopperInventory.getPos()));
            btnMatchNBT.setIcon(btnMatchNBT.getIcon().toggle());
        } else if (button.id == 3) {
            fieldValue = btnMatchMod.getIcon() == ButtonIcon.IconType.CHECKED ? 1 : 0;
            PacketHandler.sendToServer(new MessageSetFieldServer(3, fieldValue, hopperInventory.getPos()));
            btnMatchMod.setIcon(btnMatchMod.getIcon().toggle());
        }
        hopperInventory.setField(fieldID, fieldValue);
        hopperInventory.markDirty();
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }
}
