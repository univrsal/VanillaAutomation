package de.universallp.va.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by universallp on 23.06.2016 00:18.
 */
public class GuiTextFieldMultiLine extends GuiTextField {

    private final int lineHeight;
    private List<String> lines = new ArrayList<String>();
    private String[] visibleLines;
    private int scrollIndex = 0;
    private int cursorX = 0, cursorY = 0;

    public GuiTextFieldMultiLine(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
        lineHeight = fontrendererObj.FONT_HEIGHT + 2;
        visibleLines = new String[par6Height / lineHeight];
        lines.add("");
    }

    @Override
    public boolean textboxKeyTyped(char typedChar, int keyCode) {
        if (keyCode == 28) {
            if (cursorY + 1 >= lines.size())
                lines.add("");
            cursorY++;
            return true;
        } else
            return super.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public void writeText(String textToWrite) {
        String line = lines.get(cursorY);
        line = line.substring(cursorX, line.length()) + textToWrite + line.substring(0, cursorX);
        lines.set(cursorY, line);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public String getSelectedText() {
        return super.getSelectedText();
    }

    @Override
    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
                drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
            }

            int line = 0;
            for (int i = scrollIndex; i < scrollIndex + visibleLines.length; i++) {
                if (i >= lines.size())
                    break;
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(lines.get(i), xPosition + 2, yPosition + 2 + lineHeight * line, 14737632);
                line++;
            }
        }
    }

    @Override
    public void setCursorPositionEnd() {
        cursorY = lines.size();
        cursorX = lines.get(cursorY - 1).length();
        scrollIndex = cursorY - visibleLines.length;
    }


}
