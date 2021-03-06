package de.universallp.va.client.gui;

import de.universallp.va.core.container.ContainerClock;
import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.tile.TileClock;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by universallp on 22.10.2016 21:02.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class GuiClock extends GuiContainer {

    private int tickdelay;
    private int ticklength;
    private TileClock te;

    public GuiClock(TileClock te) {
        super(new ContainerClock());
        this.te = te;
        this.tickdelay = te.getTickDelay();
        this.ticklength = te.getTickLength();
    }

    @Override
    public void initGui() {
        super.initGui();
        GuiButton[] delay_btn = new GuiButton[4];
        GuiButton[] length_btn = new GuiButton[4];

        int x = this.width / 2;
        int y = this.height / 2 + 2;
        delay_btn[0] = new GuiButton(0, x - 75, y, 30, 20, "-10");
        delay_btn[1] = new GuiButton(1, x - 35, y, 30, 20, "-1");
        delay_btn[2] = new GuiButton(2, x + 5, y, 30, 20, "+1");
        delay_btn[3] = new GuiButton(3, x + 45, y, 30, 20, "+10");

        y += 23;
        length_btn[0] = new GuiButton(4, x - 75, y, 30, 20, "-10");
        length_btn[1] = new GuiButton(5, x - 35, y, 30, 20, "-1");
        length_btn[2] = new GuiButton(6, x + 5, y, 30, 20, "+1");
        length_btn[3] = new GuiButton(7, x + 45, y, 30, 20, "+10");

        buttonList.addAll(Arrays.asList(delay_btn));
        buttonList.addAll(Arrays.asList(length_btn));

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 0:
                tickdelay -= (tickdelay - 10 > 0 ? 10 : 0);
                break;
            case 1:
                tickdelay -= (tickdelay - 1 > 0 ? 1 : 0);
                break;
            case 2:
                tickdelay += (tickdelay + 10 <= ConfigHandler.CLOCK_MAX_VALUE ? 1 : 0);
                break;
            case 3:
                tickdelay += (tickdelay + 1 <= ConfigHandler.CLOCK_MAX_VALUE ? 10 : 0);
                break;
            case 4:
                ticklength -= (ticklength - 10 > 0 ? 10 : 0);
                break;
            case 5:
                ticklength -= (ticklength - 1 > 0 ? 1 : 0);
                break;
            case 6:
                ticklength += (ticklength + 10 <= ConfigHandler.CLOCK_MAX_VALUE ? 1 : 0);
                break;
            case 7:
                ticklength += (ticklength + 1 <= ConfigHandler.CLOCK_MAX_VALUE ? 10 : 0);
                break;
        }

        PacketHandler.sendToServer(new MessageSetFieldServer(0, tickdelay, te.getPos()));
        PacketHandler.sendToServer(new MessageSetFieldServer(1, ticklength, te.getPos()));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        String s = I18n.format(LibLocalization.GUI_CLOCK);
        int l = fontRenderer.getStringWidth(s);
        fontRenderer.drawString(s, width / 2 - l / 2, height / 2 - 28, LibNames.TEXT_COLOR);

        if (GuiScreen.isShiftKeyDown())
            s = I18n.format(LibLocalization.GUI_CLOCK_DELAY) + ": " + String.valueOf(tickdelay / 20.0) + " " + I18n.format(LibLocalization.GUI_CLOCK_SECONDS);
        else
            s = I18n.format(LibLocalization.GUI_CLOCK_DELAY) + ": " + String.valueOf(tickdelay) + " " + I18n.format(LibLocalization.GUI_CLOCK_TICKS);

        l = fontRenderer.getStringWidth(s);
        fontRenderer.drawString(s, width / 2 - l / 2, height / 2 - 17, LibNames.TEXT_COLOR);

        if (GuiScreen.isShiftKeyDown())
            s = I18n.format(LibLocalization.GUI_CLOCK_LENGTH) + ": " + String.valueOf(ticklength / 20.0) + " " + I18n.format(LibLocalization.GUI_CLOCK_SECONDS);
        else
            s = I18n.format(LibLocalization.GUI_CLOCK_LENGTH) + ": " + String.valueOf(ticklength) + " " + I18n.format(LibLocalization.GUI_CLOCK_TICKS);

        l = fontRenderer.getStringWidth(s);
        fontRenderer.drawString(s, width / 2 - l / 2, height / 2 - 8, LibNames.TEXT_COLOR);


    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(LibResources.GUI_CLOCK);
        int i = (this.width - 176) / 2;
        int j = (this.height - 70) / 2;
        drawTexturedModalRect(i, j, 0, 0, 176, 86);

    }
}
