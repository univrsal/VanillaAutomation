package de.universallp.va.client.gui;

import de.universallp.va.client.ClientProxy;
import de.universallp.va.client.gui.guide.Entry;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.ButtonLabel;
import de.universallp.va.core.util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;

/**
 * Created by universallp on 21.03.2016 15:45.
 */
public class GuiGuide extends GuiScreen {

    private static ResourceLocation bg = new ResourceLocation(References.MOD_ID, "textures/gui/guide.png");

    private ButtonLabel btnMenu;
    private ButtonLabel btnBack;
    private ButtonLabel btnNext;
    private ButtonLabel btnLast;

    private Entry currentEntry;
    private Entry lastEntry;

    public GuiGuide(EnumEntry entry) {

    }

    public GuiGuide() {
        if (ClientProxy.lastEntry != null)
            currentEntry = ClientProxy.lastEntry;
        else
            currentEntry = EnumEntry.MENU.getEntry();

            ClientProxy.guiScale = Minecraft.getMinecraft() .gameSettings.guiScale;
            Minecraft.getMinecraft() .gameSettings.guiScale = 0;
    }

    @Override
    public void initGui() {
        btnMenu = new ButtonLabel("gui.va.buttonmenu", 0, width / 2 + 68, height / 2 - 78);
        btnBack = new ButtonLabel("gui.va.buttonback", 1, width / 2 + 68, height / 2 - 68);
        btnNext = new ButtonLabel("-->", 2, width / 2 + 38, height / 2 + 100);
        btnLast = new ButtonLabel("<--", 3, width / 2 - 56, height / 2 + 100);

        buttonList.add(btnMenu);
        buttonList.add(btnBack);
        buttonList.add(btnNext);
        buttonList.add(btnLast);

        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        mc.renderEngine.bindTexture(bg);
        drawTexturedModalRect(width / 2 - 129 / 2, height / 2 - 167 / 2, 0, 0, 129, 179);
        currentEntry.draw(mouseX, mouseY, this, partialTicks);

        super.drawScreen(mouseX, mouseY, partialTicks);
        if (currentEntry.getCurrentPage().getRecipe() != null && currentEntry.getCurrentPage().getRecipe().getTooltip() != null)
            drawHoveringText(currentEntry.getCurrentPage().getRecipe().getTooltip(), mouseX, mouseY, currentEntry.getCurrentPage().getRecipe().getFontRenderer());

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        currentEntry.onClick(mouseX, mouseY, mouseButton, this);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) // Menu
            currentEntry = EnumEntry.MENU.getEntry();
        else if (button.id == 1) // Back
            currentEntry = lastEntry;
        else if (button.id == 2) { // Next page
            if (currentEntry.getPage() < currentEntry.getMaxPages() - 1)
                currentEntry.setPage(currentEntry.getPage() + 1);
        } else if (button.id == 3) {
            if (currentEntry.getPage() > 0)
                currentEntry.setPage(currentEntry.getPage() - 1);
        }
        super.actionPerformed(button);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (lastEntry != null)
            btnBack.enabled = true;
        else
            btnBack.enabled = false;

        if (currentEntry.getPage() >= currentEntry.getMaxPages() - 1)
            btnNext.enabled = false;
        else
            btnNext.enabled = true;

        if (currentEntry.getPage() == 0)
            btnLast.enabled = false;
        else
            btnLast.enabled = true;
    }

    @Override
    public void onGuiClosed() {
        ClientProxy.lastEntry = currentEntry;
        Minecraft.getMinecraft().gameSettings.guiScale = ClientProxy.guiScale;
        super.onGuiClosed();
    }

    public void setCurrentEntry(Entry currentEntry) {
        this.lastEntry = currentEntry;
        this.currentEntry = currentEntry;
    }

    public void drawTooltip(int x, int y, List<String> text, FontRenderer f) {
        if (f == null)
            f = mc.fontRendererObj;
        f.setUnicodeFlag(false);
        drawHoveringText(text, x, y, f);
        f.setUnicodeFlag(true);
    }
}
