package de.universallp.va.client.gui;

import de.universallp.va.client.ClientProxy;
import de.universallp.va.client.gui.guide.Entry;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.ButtonLabel;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

/**
 * Created by universallp on 21.03.2016 15:45 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class GuiGuide extends GuiScreen {

    public static final ResourceLocation bg = new ResourceLocation(LibNames.MOD_ID, "textures/gui/guide.png");

    private ButtonLabel btnMenu;
    private ButtonLabel btnBack;
    private ButtonLabel btnNext;
    private ButtonLabel btnLast;

    private Entry currentEntry;
    private Entry lastEntry;

    public GuiGuide(EnumEntry entry) {
        currentEntry = entry.getEntry();
        ClientProxy.guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
        Minecraft.getMinecraft().gameSettings.guiScale = 0;
    }

    public GuiGuide() {
        if (ClientProxy.lastEntry != null)
            currentEntry = ClientProxy.lastEntry;
        else
            currentEntry = EnumEntry.MENU.getEntry();

        ClientProxy.guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
        Minecraft.getMinecraft().gameSettings.guiScale = 0;
    }

    @Override
    public void initGui() {
        btnMenu = new ButtonLabel(LibLocalization.BTN_MENU, 0, width / 2 + 73, height / 2 - 78);
        btnBack = new ButtonLabel(LibLocalization.BTN_BACK, 1, width / 2 + 73, height / 2 - 68);
        btnNext = new ButtonLabel("-->", 2, width / 2 + 44, height / 2 + 100);
        btnLast = new ButtonLabel("<--", 3, width / 2 - 50, height / 2 + 100);

        btnBack.enabled = lastEntry != null || !currentEntry.equals(EnumEntry.MENU.getEntry());

        btnNext.enabled = currentEntry.getPage() < currentEntry.getMaxPages() - 1;

        btnLast.enabled = currentEntry.getPage() != 0;

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
        drawTexturedModalRect(width / 2 - 134 / 2, height / 2 - 167 / 2, 0, 0, 134, 181);
        currentEntry.draw(mouseX, mouseY, this, partialTicks);

        super.drawScreen(mouseX, mouseY, partialTicks);
        if (currentEntry.getCurrentPage().getRecipe() != null && currentEntry.getCurrentPage().getRecipe().getTooltip() != null)
            drawHoveringText(currentEntry.getCurrentPage().getRecipe().getTooltip(), mouseX, mouseY, currentEntry.getCurrentPage().getRecipe().getFontRenderer());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 1) // To last entry
            actionPerformed(btnBack);

        if (mouseButton == 3) // Side button on mouse to go back
            actionPerformed(btnLast);

        if (mouseButton == 4)// Side button on mouse to go forward
            actionPerformed(btnNext);

        currentEntry.onClick(mouseX, mouseY, mouseButton, this);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) // Menu
            currentEntry = EnumEntry.MENU.getEntry();
        else if (button.id == 1) { // Back
            if (!currentEntry.equals(EnumEntry.MENU.getEntry())) {
                lastEntry = currentEntry;
                currentEntry = EnumEntry.MENU.getEntry();
            } else if (lastEntry != null) {
                Entry e = lastEntry;
                lastEntry = currentEntry;
                currentEntry = e;
            }
        } else if (button.id == 2) { // Next page
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
        btnBack.enabled = lastEntry != null || !currentEntry.equals(EnumEntry.MENU.getEntry());

        btnNext.enabled = currentEntry.getPage() < currentEntry.getMaxPages() - 1;

        btnLast.enabled = currentEntry.getPage() != 0;
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

    public void drawRectangle(int left, int top, int right, int bottom, int startC, int endC) {
        drawGradientRect(left, top, right, bottom, startC, endC);
    }
}
