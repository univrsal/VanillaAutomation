package de.universallp.va.client.gui;

import de.universallp.va.client.gui.screen.ButtonIcon;
import de.universallp.va.core.container.ContainerFilteredHopper;
import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.tile.TileFilteredHopper;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import java.io.IOException;

/**
 * Created by universallp on 31.03.2016 16:05.
 */
public class GuiFilteredHopper extends GuiContainer {

    private IInventory playerInventory;
    private TileFilteredHopper hopperInventory;
    private ButtonIcon btnIco;

    public GuiFilteredHopper(InventoryPlayer playerInv, IInventory hopperInv) {
        super(new ContainerFilteredHopper(playerInv, hopperInv));
        this.ySize = 153;
        this.playerInventory = playerInv;
        this.hopperInventory = (TileFilteredHopper) hopperInv;
    }

    @Override
    public void initGui() {
        super.initGui();
        btnIco = new ButtonIcon(0, guiLeft + 136, guiTop + 38, ButtonIcon.IconType.values()[hopperInventory.getFilterMode().ordinal()]);
        buttonList.add(btnIco);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.fontRendererObj.drawString(this.hopperInventory.getDisplayName().getUnformattedText(), 8, 6, LibNames.TEXT_COLOR);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, LibNames.TEXT_COLOR);
        this.fontRendererObj.drawString(I18n.format(LibLocalization.GUI_FILTER), 13, 44, LibNames.TEXT_COLOR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LibResources.GUI_FILTEREDHOPPER);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 0) {
            if (btnIco.getIcon() == ButtonIcon.IconType.WHITELIST) {
                btnIco.setIcon(ButtonIcon.IconType.BLACKLIST);
                hopperInventory.setFilterMode(TileFilteredHopper.EnumFilter.BLACKLIST);
                PacketHandler.sendToServer(new MessageSetFieldServer(0, 1, hopperInventory.getPos()));
            } else {
                btnIco.setIcon(ButtonIcon.IconType.WHITELIST);
                hopperInventory.setFilterMode(TileFilteredHopper.EnumFilter.WHITELIST);
                PacketHandler.sendToServer(new MessageSetFieldServer(0, 0, hopperInventory.getPos()));
            }
        }
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }
}
