package de.universallp.va.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;

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
    private int selectionStartX = 0, selectionStartY = 0;
    private int selectionEndX = 0, selectionEndY = 0;

    public GuiTextFieldMultiLine(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
        lineHeight = fontrendererObj.FONT_HEIGHT + 2;
        visibleLines = new String[par6Height / lineHeight];
        lines.add("");
    }

    @Override
    public boolean textboxKeyTyped(char typedChar, int keyCode) {
        switch (keyCode) {
            case 28:
                if (cursorY + 1 >= lines.size())
                    lines.add("");
                cursorY++;
                cursorX = 0;
                return true;

            case 203: // Left

                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown())
                        this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                    else
                        this.setSelectionPos(this.getSelectionEnd() - 1);
                } else if (GuiScreen.isCtrlKeyDown())
                    this.setCursorPosition(this.getNthWordFromCursor(-1));
                else
                    moveCursorHorizontal(-1);
                return true;

            case 205: // Right
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown())
                        this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                    else
                        this.setSelectionPos(this.getSelectionEnd() + 1);
                } else if (GuiScreen.isCtrlKeyDown())
                    this.setCursorPosition(this.getNthWordFromCursor(1));
                else
                    this.moveCursorHorizontal(1);
                return true;

            case 211:
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(1);
                    }
                } else if (this.isEnabled) {
                    this.deleteFromCursor(1);
                }
                return true;
            default:
                if (this.isEnabled && this.isFocused()) {
                    this.writeText(Character.toString(typedChar));
                    return true;
                } else {
                    return false;
                }
        }
    }

    @Override
    public void deleteFromCursor(int num) {

        if (lines.size() > 0) {
            if (selectionStartX == 0 && selectionEndX == 0 && selectionStartY == 0 && selectionEndY == 0) {
                if (num > 0) { // Delete after cursor
                    String line = lines.get(cursorY);
                    if (cursorX < line.length()) {
                        line = line.substring(0, cursorX) + line.substring(cursorX + 1, line.length());
                        if (line.length() == 0) {
                            if (cursorY == lines.size() - 1 && cursorY > 0)
                                cursorY--;

                            lines.remove(cursorY);
                        } else
                            lines.set(cursorY, line);
                    }
                } else { // Delete before cursor
                    String line = lines.get(cursorY);
                    line = line.substring(0, cursorX - 1) + line.substring(cursorX, line.length());
                    lines.set(cursorY, line);
                }
            } else {

            }
        }
    }

    public void moveCursorHorizontal(int n) {
        if (cursorX + n >= 0 && cursorX + n <= lines.get(cursorY).length()) {
            cursorX += n;
        } else {
            if (cursorX + n < 0 && cursorY > 0) {
                cursorY--;
                cursorX = lines.get(cursorY).length();
            } else if (cursorX + n > lines.get(cursorY).length() && cursorY < lines.size() - 1) {
                cursorY++;
                cursorX = 0;
            }
        }
    }

    @Override
    public void writeText(String textToWrite) {
        String line = lines.get(cursorY);
        String s1 = ChatAllowedCharacters.filterAllowedCharacters(textToWrite);
        line = line.substring(0, cursorX) + s1 + line.substring(cursorX, line.length());
        lines.set(cursorY, line);
        cursorX += s1.length();
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
            FontRenderer f = Minecraft.getMinecraft().fontRendererObj;
            if (this.getEnableBackgroundDrawing()) {
                drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
                drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
            }

            int line = 0;
            for (int i = scrollIndex; i < scrollIndex + visibleLines.length; i++) {
                if (i >= lines.size())
                    break;
                String lineText = lines.get(i);
                int top = lineHeight * line + yPosition + 2;

                if (i == cursorY && isFocused()) {
                    int xBeforeCursor = xPosition + f.getStringWidth(lineText.substring(0, cursorX)) + 2;
                    Gui.drawRect(xBeforeCursor, top, xBeforeCursor + 1, top + 1 + f.FONT_HEIGHT, -3092272);
                }

                f.drawStringWithShadow(lineText, xPosition + 2, top, 14737632);
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
