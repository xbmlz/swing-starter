package io.github.xbmlz.ui.demo;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.util.FontUtils;
import io.github.xbmlz.App;
import io.github.xbmlz.ui.plugin.Fonts;
import io.github.xbmlz.ui.plugin.I18n;
import io.github.xbmlz.ui.plugin.Themes;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class SystemPanel extends JPanel {

    private JLabel i18nLabel;

    private JLabel themeLabel;

    private JLabel fontLabel;

    private JLabel fontSizeLabel;

    private JLabel notificationLabel;

    private JComboBox<String> i18nComboBox;

    private JComboBox<Themes.ThemeInfo> themeComboBox;

    private JComboBox<String> fontComboBox;

    private JComboBox<Integer> fontSizeComboBox;

    private JButton notificationButton;


    public SystemPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("insets 15, wrap 2, gapx 10, gapy 10"));
        // language
        {
            String[] bundledLocales = I18n.BUNDLED_LOCALES;
            i18nLabel = new JLabel(I18n.get("demo.tabs.system.language"));
            i18nComboBox = new JComboBox<>();
            for (String locale : bundledLocales) {
                i18nComboBox.addItem(locale);
            }
            for (int i = 0; i < bundledLocales.length; i++) {
                if (bundledLocales[i].equals(I18n.getLocaleLanguage())) {
                    i18nComboBox.setSelectedIndex(i);
                    break;
                }
            }
            i18nComboBox.addActionListener(e -> i18nChanged());
            add(i18nLabel, "width 80");
            add(i18nComboBox, "growx");
        }
        // theme
        {
            Themes.ThemeInfo[] bundledThemes = Themes.BUNDLED_THEMES;
            themeLabel = new JLabel(I18n.get("demo.tabs.system.theme"));
            themeComboBox = new JComboBox<>();
            for (Themes.ThemeInfo themeInfo : bundledThemes) {
                themeComboBox.addItem(themeInfo);
            }
            themeComboBox.addActionListener(e -> themeChanged());
            add(themeLabel);
            add(themeComboBox, "growx");
        }
        // font
        {
            String[] families = Fonts.getAvailableFontFamilies();
            fontLabel = new JLabel(I18n.get("demo.tabs.system.font"));
            fontComboBox = new JComboBox<>();
            for (String family : families) {
                fontComboBox.addItem(family);
            }
            for (int i = 0; i < families.length; i++) {
                if (families[i].equals(Fonts.getCurrentFontFamily())) {
                    fontComboBox.setSelectedIndex(i);
                    break;
                }
            }
            fontComboBox.addActionListener(e -> fontChanged());
            add(fontLabel);
            add(fontComboBox, "growx");
        }
        // font size
        {
            fontSizeLabel = new JLabel(I18n.get("demo.tabs.system.fontsize"));
            fontSizeComboBox = new JComboBox<>();
            for (int i = 10; i <= 28; i++) {
                fontSizeComboBox.addItem(i);
            }
            fontSizeComboBox.setSelectedItem(12);
            fontSizeComboBox.addActionListener(e -> fontSizeChanged());
            add(fontSizeLabel);
            add(fontSizeComboBox, "growx");
        }
        // notification
        {
            notificationLabel = new JLabel(I18n.get("demo.tabs.system.notification"));
            notificationButton = new JButton(I18n.get("demo.tabs.system.notification.btn"));
            notificationButton.addActionListener(e -> notificationClicked());
            add(notificationLabel);
            add(notificationButton, "growx");
        }
    }

    private void notificationClicked() {
        App.systemTray.displayMessage("Test Notification", "This is a test notification", TrayIcon.MessageType.INFO);
    }

    private void i18nChanged() {
        String item = (String) i18nComboBox.getSelectedItem();
        if (item == null) return;
        I18n.setLocale(new Locale(item));
        i18nLabel.setText(I18n.get("demo.tabs.system.language"));
        themeLabel.setText(I18n.get("demo.tabs.system.theme"));
        fontLabel.setText(I18n.get("demo.tabs.system.font"));
        fontSizeLabel.setText(I18n.get("demo.tabs.system.fontsize"));
        notificationLabel.setText(I18n.get("demo.tabs.system.notification"));
        notificationButton.setText(I18n.get("demo.tabs.system.notification.btn"));
    }

    private void themeChanged() {
        Themes.ThemeInfo item = (Themes.ThemeInfo) themeComboBox.getSelectedItem();
        if (item != null) Themes.applyTheme(item.lafClassName());
    }

    private void fontChanged() {
        String item = (String) fontComboBox.getSelectedItem();
        FlatAnimatedLafChange.showSnapshot();
        Font font = UIManager.getFont("defaultFont");
        Font newFont = FontUtils.getCompositeFont(item, font.getStyle(), font.getSize());
        UIManager.put("defaultFont", newFont);
        FlatLaf.updateUI();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    private void fontSizeChanged() {
        Integer fontSizeStr = (Integer) fontSizeComboBox.getSelectedItem();
        if (fontSizeStr == null) return;
        Font font = UIManager.getFont("defaultFont");
        Font newFont = font.deriveFont((float) fontSizeStr);
        UIManager.put("defaultFont", newFont);
        FlatLaf.updateUI();
    }
}
