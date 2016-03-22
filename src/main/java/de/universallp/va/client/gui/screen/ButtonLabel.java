package de.universallp.va.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import java.awt.*;

/**
 * Created by universallp on 21.03.2016 17:53.
 */
public class ButtonLabel extends GuiButton {

    private Color textColor;

    public ButtonLabel(String unlocalizedText, int id, int x, int y) {
        super(id, x, y, unlocalizedText);
        textColor = new Color(255, 255, 255);
        width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(I18n.format(unlocalizedText));
        height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void drawButtonForegroundLayer(int mouseX, int mouseY) {

    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (visible)
            if (enabled)
                if (isMouseOver(mouseX, mouseY, mc))
                    mc.fontRendererObj.drawString(I18n.format(displayString), xPosition, yPosition, 16777120, true);
                else
                    mc.fontRendererObj.drawString(I18n.format(displayString), xPosition, yPosition, textColor.getRGB(), true);
            else
                mc.fontRendererObj.drawString(I18n.format(displayString), xPosition, yPosition, 10526880, true);
    }

    public boolean isMouseOver(int mouseX, int mouseY, Minecraft mc) {
        return mouseX > xPosition && mouseX < xPosition + mc.fontRendererObj.getStringWidth(I18n.format(displayString)) && mouseY > yPosition && mouseY < yPosition + mc.fontRendererObj.FONT_HEIGHT;
    }


}
