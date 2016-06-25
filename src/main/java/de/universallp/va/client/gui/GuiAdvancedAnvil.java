package de.universallp.va.client.gui;

import de.universallp.va.client.gui.screen.GuiTextFieldMultiLine;
import de.universallp.va.core.container.ContainerAdvancedAnvil;
import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.tile.TileAdvancedAnvil;
import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

/**
 * Created by universallp on 22.06.2016 19:28.
 */
public class GuiAdvancedAnvil extends GuiContainer {

    private IInventory playerInventory;
    private TileAdvancedAnvil anvilInventory;
    private GuiTextField renameText;
    private GuiTextFieldMultiLine descriptionText;

    public GuiAdvancedAnvil(InventoryPlayer playerInv, IInventory anvilInv) {
        super(new ContainerAdvancedAnvil(playerInv, anvilInv));
        this.ySize = 215;
        this.playerInventory = playerInv;
        this.anvilInventory = (TileAdvancedAnvil) anvilInv;
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        renameText = new GuiTextField(0, fontRendererObj, guiLeft + 60, guiTop + 21, 108, 14);
        descriptionText = new GuiTextFieldMultiLine(1, fontRendererObj, guiLeft + 60, guiTop + 39, 108, 47);

        descriptionText.setMaxStringLength(200);
        descriptionText.setVisible(true);
        renameText.setMaxStringLength(30);
        renameText.setVisible(true);

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        //this.inventorySlots.removeListener(this);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        renameText.mouseClicked(mouseX, mouseY, mouseButton);
        descriptionText.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (renameText.textboxKeyTyped(typedChar, keyCode)) {

        } else {
            if (descriptionText.textboxKeyTyped(typedChar, keyCode)) {

            } else
                super.keyTyped(typedChar, keyCode);
        }
        List<String> origText = descriptionText.getEntireText();
        int[] fieldIds = new int[origText.size()];
        for (int i = 0; i < fieldIds.length; i++)
            fieldIds[i] = i;
        PacketHandler.sendToServer(new MessageSetFieldServer(fieldIds, origText.toArray(new String[0]), anvilInventory.getPos()));


    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LibResources.GUI_ADVANCED_ANVIL);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        renameText.drawTextBox();
        descriptionText.drawTextBox();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    public void setItemName(String s) {
        renameText.setText(s);
    }

    public void setItemDesc(List<String> s) {
        descriptionText.setText(s);
        descriptionText.setCursorPositionZero();
    }

    public void toggleTextBoxes(boolean status) {
        renameText.setEnabled(status);
        descriptionText.setEnabled(status);
    }
}
