package de.universallp.va.core.util;

import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by universallp on 22.03.2016 14:33.
 */
public class Utils {

    public static void drawWrappedString(String s, int x, int y, int maxWidth, Color c, boolean shadow, FontRenderer f) {
        List<String> words = new ArrayList<String>();

        Collections.addAll(words, s.split(" "));

        int line = 0;
        int wordIndex = 0;
        String currentLine = "";

        main: while (wordIndex < words.size()) {
            while (wordIndex < words.size() && f.getStringWidth(currentLine + " " + words.get(wordIndex)) <= maxWidth) {
                if (f.getStringWidth(words.get(wordIndex)) >= maxWidth)
                    break main;

                if (words.get(wordIndex).equals("/n")) {
                    f.drawString(currentLine, x, y + (f.FONT_HEIGHT + 2) * line, c.getRGB(), shadow);
                    currentLine = "";
                    line++;
                    wordIndex++;
                    continue main;
                }

                currentLine += " " + words.get(wordIndex);
                wordIndex++;
            }

            f.drawString(currentLine, x, y + (f.FONT_HEIGHT + 2) * line, c.getRGB(), shadow);
            currentLine = "";
            line++;
        }
    }

    public static void drawWrappedString(String s, int x, int y, int maxWidth, FontRenderer f) {
        drawWrappedString(s, x, y, maxWidth, new Color(130, 130, 130), false, f);
    }
}
