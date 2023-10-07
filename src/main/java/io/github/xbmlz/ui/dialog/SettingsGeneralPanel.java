package io.github.xbmlz.ui.dialog;

import io.github.xbmlz.model.KV;
import io.github.xbmlz.ui.component.AccentColorIcon;
import io.github.xbmlz.util.I18n;
import io.github.xbmlz.util.Theme;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingsGeneralPanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(SettingsGeneralPanel.class);

    private JComboBox<KV> themeComboBox;

    private JComboBox<KV> languageComboBox;

    private Color accentColor;

    private final JToggleButton[] accentColorButtons = new JToggleButton[Theme.ACCENT_COLOR_KEYS.length];

    public SettingsGeneralPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new MigLayout("wrap", "[]30[]", "[]10[]"));
        initComponents();
    }

    private void initComponents() {
        // 主题
        {
            JLabel themeLabel = new JLabel();
            themeLabel.setText(I18n.getString("settings.theme"));
            add(themeLabel);
            themeComboBox = new JComboBox<>();
            themeComboBox.addItem(new KV("dark", I18n.getString("settings.theme.dark")));
            themeComboBox.addItem(new KV("light", I18n.getString("settings.theme.light")));
            themeComboBox.addItem(new KV("system", I18n.getString("settings.theme.system")));
            themeComboBox.addActionListener(this::themeChanged);
            add(themeComboBox, "width 200");
        }
        // 语言
        {
            JLabel languageLabel = new JLabel();
            languageLabel.setText(I18n.getString("settings.language"));
            add(languageLabel);
            languageComboBox = new JComboBox<>();
            languageComboBox.addItem(new KV(I18n.LOCAL_ZH, I18n.getString("settings.language.zh")));
            languageComboBox.addItem(new KV(I18n.LOCAL_EN, I18n.getString("settings.language.en")));
            languageComboBox.addActionListener(this::languageChanged);
            add(languageComboBox, "width 200");
        }
        // 主题色
        {
            JLabel accentColorLabel = new JLabel(I18n.getString("settings.accentColor"));
            add(accentColorLabel);
            JPanel accentColorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 0));
            ButtonGroup group = new ButtonGroup();
            for (int i = 0; i < accentColorButtons.length; i++) {
                accentColorButtons[i] = new JToggleButton(new AccentColorIcon(Theme.ACCENT_COLOR_KEYS[i]));
                accentColorButtons[i].setToolTipText(Theme.ACCENT_COLOR_NAMES[i]);
                accentColorButtons[i].addActionListener(this::accentColorChanged);
                accentColorPanel.add(accentColorButtons[i]);
                group.add(accentColorButtons[i]);
            }
            add(accentColorPanel, "width 200");
        }
        // 缩放
        {
            JLabel zoomLabel = new JLabel();
            zoomLabel.setText(I18n.getString("settings.zoom"));
            add(zoomLabel);
            JSlider zoomSlider = buildZoomSlider();
            add(zoomSlider, "width 200");
        }
        // init select
        setupSelected();
    }

    private static JSlider buildZoomSlider() {
        JSlider zoomSlider = new JSlider();
        zoomSlider.setMinimum(100);
        zoomSlider.setMaximum(200);
        zoomSlider.setMinorTickSpacing(10);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setMajorTickSpacing(50);
        zoomSlider.setPaintLabels(true);
        zoomSlider.setValue(100);
        zoomSlider.addChangeListener(e -> {
            int value = zoomSlider.getValue();
            log.debug("zoomSlider: {}", value);
        });
        return zoomSlider;
    }


    private void setupSelected() {
        // theme
        String theme = Theme.getTheme();
        if (theme.equals(Theme.DARK)) {
            themeComboBox.setSelectedIndex(0);
        } else if (theme.equals(Theme.LIGHT)) {
            themeComboBox.setSelectedIndex(1);
        } else {
            themeComboBox.setSelectedIndex(2);
        }
        // language
        String language = I18n.getLocale();
        if (language.equals(I18n.LOCAL_ZH)) {
            languageComboBox.setSelectedIndex(0);
        } else {
            languageComboBox.setSelectedIndex(1);
        }
        // accent color
        String accentColorKey = Theme.getAccentColorKey();
        if (accentColorKey == null) {
            accentColorButtons[0].setSelected(true);
        } else {
            for (int i = 0; i < Theme.ACCENT_COLOR_KEYS.length; i++) {
                if (Theme.ACCENT_COLOR_KEYS[i].equals(accentColorKey)) {
                    accentColorButtons[i].setSelected(true);
                    break;
                }
            }
        }
    }

    private void themeChanged(ActionEvent e) {
        KV currentTheme = (KV) themeComboBox.getSelectedItem();
        assert currentTheme != null;
        Theme.toggleTheme(currentTheme.getKey());
        Theme.saveTheme(currentTheme.getKey());
    }

    private void languageChanged(ActionEvent e) {
        KV currentLanguage = (KV) languageComboBox.getSelectedItem();
        assert currentLanguage != null;
        I18n.setLocal(currentLanguage.getKey());
    }

    private void accentColorChanged(ActionEvent e) {
        String accentColorKey = null;
        for (int i = 0; i < accentColorButtons.length; i++) {
            if (accentColorButtons[i].isSelected()) {
                accentColorKey = Theme.ACCENT_COLOR_KEYS[i];
                break;
            }
        }
        Theme.applyAccentColor(accentColorKey);
        Theme.saveAccentColor(accentColorKey);
    }
}
