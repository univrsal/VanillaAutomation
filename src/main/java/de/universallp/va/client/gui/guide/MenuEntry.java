package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.GuiGuide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;

import java.awt.*;

/**
 * Created by universallp on 21.03.2016 18:33.
 */
public class MenuEntry {

    private static final int SELECTED = new Color(90, 90, 90).getRGB();
    private static final int UNSELECTED = new Color(40, 40, 40).getRGB();
    private static final int BACKGROUND = new Color(230, 230, 230).getRGB();
    private String name;
    private EnumEntry pointsTo;

    public MenuEntry(String name, EnumEntry pointsTo) {
        this.name = name;
        this.pointsTo = pointsTo;
    }

    public void draw(int mouseX, int mouseY, int x, int y, GuiGuide parent) {
        if (mouseOver(mouseX, mouseY, x, y)) {
            parent.mc.fontRendererObj.drawString(I18n.format(name), x, y, SELECTED);
            parent.drawRectangle(x - 1, y, x + 113, y + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, BACKGROUND & 0x22000000, BACKGROUND & 0x22000000);
        } else {
            parent.mc.fontRendererObj.drawString(I18n.format(name), x, y, UNSELECTED);
        }
    }

    public void onClick(int mouseX, int mouseY, int button, GuiGuide parent) {
        if (button == 0) {
            parent.setCurrentEntry(pointsTo.getEntry());
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ui_button_click, 1.0F));
        }
    }

    public boolean mouseOver(int mX, int mY, int x, int y) {
        return mX > x && mX < x + 120 && mY > y && mY < y + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }
}
