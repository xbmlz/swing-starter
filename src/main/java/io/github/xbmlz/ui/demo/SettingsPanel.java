package io.github.xbmlz.ui.demo;

import io.github.xbmlz.model.KV;
import io.github.xbmlz.util.Messages;
import io.github.xbmlz.util.Themes;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class SettingsPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(DemoTabs.class);

    private JLabel themeLabel;

    private JLabel languageLabel;

    private JComboBox<KV> themeComboBox;

    private JComboBox<KV> languageComboBox;

    public SettingsPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new MigLayout("wrap 2", "[]30[]"));
        initComponents();
    }

    private void initComponents() {
        // 主题
        {
            themeLabel = new JLabel();
            themeLabel.setText(Messages.getString("tab.settings.theme"));
            add(themeLabel);
            themeComboBox = new JComboBox<>();
            themeComboBox.addItem(new KV("dark", Messages.getString("tab.settings.theme.dark")));
            themeComboBox.addItem(new KV("light", Messages.getString("tab.settings.theme.light")));
            themeComboBox.addItem(new KV("system", Messages.getString("tab.settings.theme.system")));
            themeComboBox.addActionListener(e -> changeTheme());
            add(themeComboBox);
        }
        // 语言
        {
            languageLabel = new JLabel();
            languageLabel.setText(Messages.getString("tab.settings.language"));
            add(languageLabel);
            languageComboBox = new JComboBox<>();
            languageComboBox.addItem(new KV(Messages.LOCAL_ZH, Messages.getString("tab.settings.language.zh")));
            languageComboBox.addItem(new KV(Messages.LOCAL_EN, Messages.getString("tab.settings.language.en")));
            languageComboBox.addActionListener(e -> changeLanguage());
            add(languageComboBox);
        }
        // init select
        initSelect();
    }

    private void initSelect() {
        String theme = Themes.get();
        if (theme.equals(Themes.DARK)) {
            themeComboBox.setSelectedIndex(0);
        } else if (theme.equals(Themes.LIGHT)) {
            themeComboBox.setSelectedIndex(1);
        } else {
            themeComboBox.setSelectedIndex(2);
        }
    }

    private void changeTheme() {
        KV currentTheme = (KV) themeComboBox.getSelectedItem();
        assert currentTheme != null;
        Themes.toggle(currentTheme.getKey());
        Themes.save(currentTheme.getKey());
    }

    private void changeLanguage() {
        KV currentLanguage = (KV) languageComboBox.getSelectedItem();
        assert currentLanguage != null;
        Messages.setLocal(currentLanguage.getKey());
    }
}
