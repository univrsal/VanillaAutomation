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

    private String name;
    private EnumEntry pointsTo;

    public MenuEntry(String name, EnumEntry pointsTo) {
        this.name = name;
        this.pointsTo = pointsTo;
    }

    public void draw(int mouseX, int mouseY, int x, int y, GuiGuide parent) {
        String text = I18n.format(name);

        if (mouseOver(mouseX, mouseY, x, y)) {
            parent.mc.fontRendererObj.drawString(I18n.format(name), x, y, new Color(90, 90, 90).getRGB());
        } else {
            parent.mc.fontRendererObj.drawString(I18n.format(name), x, y, new Color(40, 40, 40).getRGB());
        }
    }

    public void onClick(int mouseX, int mouseY, int button, GuiGuide parent) {
        if (button == 0) {
            parent.setCurrentEntry(pointsTo.getEntry());
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ui_button_click, 1.0F));
        }
    }

    public boolean mouseOver(int mX, int mY, int x, int y) {
        return mX > x && mX < x + Minecraft.getMinecraft().fontRendererObj.getStringWidth(I18n.format(name)) && mY > y && mY < y + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }
}
