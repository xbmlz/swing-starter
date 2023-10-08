package io.github.xbmlz.ui.plugin;

import com.formdev.flatlaf.util.FontUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public final class Fonts {

    public static String[] getAvailableFontFamilies() {
        final String[] availableFontFamilyNames = FontUtils.getAvailableFontFamilyNames().clone();
        Arrays.sort(availableFontFamilyNames, String.CASE_INSENSITIVE_ORDER);
        return availableFontFamilyNames;
    }

    public static String getCurrentFontFamily() {
        Font currentFont = UIManager.getFont("Label.font");
        return currentFont.getFamily();
    }
}
