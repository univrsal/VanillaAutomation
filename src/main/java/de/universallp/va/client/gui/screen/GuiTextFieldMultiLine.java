package de.universallp.va.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by universallp on 23.06.2016 00:18.
 */
public class GuiTextFieldMultiLine extends GuiTextField {

    private final int lineHeight;
    private List<String> lines = new ArrayList<String>();
    private int visibleLines;
    private int scrollIndex = 0;
    private int cursorX = 0, cursorY = 0;
    private int selectionStartX = 0, selectionStartY = 0;
    private int selectionEndX = 0, selectionEndY = 0;

    public GuiTextFieldMultiLine(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
        lineHeight = fontrendererObj.FONT_HEIGHT + 2;
        visibleLines = par6Height / lineHeight;
        lines.add("");
    }

    @Override
    public boolean textboxKeyTyped(char typedChar, int keyCode) {

        switch (keyCode) {
            case 14: // Backspace
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled)
                        deleteWordFromCursor(-1);
                } else if (this.isEnabled)
                    this.deleteFromCursor(-1);
                return true;
            case 28:
                String currentLine = lines.get(cursorY);
                if (cursorX == currentLine.length()) {
                    if (cursorY + 1 >= lines.size())
                        lines.add("");
                    else
                        lines.add(cursorY + 1, "");
                } else {
                    lines.set(cursorY, currentLine.substring(0, cursorX));
                    lines.add(cursorY + 1, currentLine.substring(cursorX, currentLine.length()));
                }

                cursorY++;
                cursorX = 0;
                return true;
            case 200: // Up
                moveCursorVertical(-1);
                return true;
            case 208:
                moveCursorVertical(1);
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

            case 211: // DEL
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        deleteWordFromCursor(1);
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

    private void deleteWordFromCursor(int num) {
        if (num > 0) { // After Cursor
//            String line = lines.get(cursorY);
//            String origLine = line.substring(0, cursorX);
//            String[] words = line.substring(cursorX, line.length()).split(" ");
//            if (words.length >= num)
//                for (int i = num; i < words.length; i++) {
//                    origLine.concat(" " + words[i]);
//                }
//
//            lines.set(cursorY, origLine);
//            cursorX = 0;
        } else { // Before Cursor
            String line = lines.get(cursorY);
            if (line.contains(" ")) {

                String origLine = line.substring(cursorX, line.length());
                line = line.substring(0, cursorX);

                for (int i = num; i < 0; i++)
                    line = StringUtils.substringBeforeLast(line, " ");
                cursorX = line.length();
                line += origLine;
                lines.set(cursorY, line);
            } else {
                if (line.length() > 0) {
                    lines.set(cursorY, "");
                    cursorX = 0;
                } else {
                    moveCursorHorizontal(-1);
                }
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

                        line = line.substring(0, cursorX) + line.substring(cursorX + num, line.length());

                        if (line.length() == 0) {
                            if (lines.size() > 1)
                                lines.remove(cursorY);
                            else
                                lines.set(0, "");

                            if (cursorY == lines.size() && cursorY > 0)
                                --cursorY;
                        } else
                            lines.set(cursorY, line);

                    } else if (line.length() == 0) {
                        if (lines.size() > 1)
                            lines.remove(cursorY);
                        else
                            lines.set(0, "");

                        if (cursorY == lines.size() && cursorY > 0)
                            --cursorY;
                    }
                } else { // Delete before cursor
                    String line = lines.get(cursorY);

                    if (cursorX > 0) {
                        cursorX += num;
                        line = line.substring(0, cursorX + 1 + num) + line.substring(cursorX + 1, line.length());
                        lines.set(cursorY, line);
                    } else {
                        if (line.length() == 0) {
                            if (lines.size() > 1)
                                lines.remove(cursorY);
                            else
                                lines.set(0, "");

                            if (cursorY == lines.size() && cursorY > 0)
                                --cursorY;
                            cursorX = lines.get(cursorY).length();
                        } else { // merge lines
                            if (cursorY > 0) {
                                --cursorY;

                                if (lines.size() > 1) {
                                    cursorX = lines.get(cursorY).length();
                                    lines.set(cursorY, lines.get(cursorY).concat(line));
                                    lines.remove(cursorY + 1);

                                } else
                                    lines.set(0, "");
                            }
                        }
                    }
                }
            }
        }
    }

    private void moveCursorHorizontal(int n) {
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

    private void moveCursorVertical(int n) {
        if (cursorY + n >= 0 && cursorY + n < lines.size()) {
            cursorY += n;
            if (cursorX > lines.get(cursorY).length())
                cursorX = lines.get(cursorY).length();
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
    public void setCursorPositionZero() {
        cursorX = 0;
        cursorY = 0;
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
            for (int i = scrollIndex; i < scrollIndex + visibleLines; i++) {
                if (i >= lines.size())
                    break;
                String lineText = lines.get(i);
                int top = lineHeight * line + yPosition + 2;
                if (i == cursorY && isFocused() && cursorX <= lineText.length()) {
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
        scrollIndex = cursorY - visibleLines;
    }

    public void setText(List<String> text) {
        lines = text;
    }

    public List<String> getEntireText() {
        return lines;
    }

}
