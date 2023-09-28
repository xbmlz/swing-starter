package io.github.xbmlz.ui.demo;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.LoggingFacade;
import io.github.xbmlz.App;
import io.github.xbmlz.model.KV;
import io.github.xbmlz.ui.component.AccentColorIcon;
import io.github.xbmlz.util.Messages;
import io.github.xbmlz.util.ProcessUtils;
import io.github.xbmlz.util.Themes;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingsPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(DemoTabs.class);

    private JLabel themeLabel;

    private JLabel languageLabel;

    private JLabel zoomLabel;

    private JLabel accentColorLabel;

    private JComboBox<KV> themeComboBox;

    private JComboBox<KV> languageComboBox;

    // the real colors are defined in
    // flatlaf-demo/src/main/resources/com/formdev/flatlaf/demo/FlatLightLaf.properties and
    // flatlaf-demo/src/main/resources/com/formdev/flatlaf/demo/FlatDarkLaf.properties
    private static String[] accentColorKeys = {
            "Demo.accent.default", "Demo.accent.blue", "Demo.accent.purple", "Demo.accent.red",
            "Demo.accent.orange", "Demo.accent.yellow", "Demo.accent.green",
    };
    private static String[] accentColorNames = {
            "Default", "Blue", "Purple", "Red", "Orange", "Yellow", "Green",
    };

    private final JToggleButton[] accentColorButtons = new JToggleButton[accentColorKeys.length];

    private Color accentColor;

    public SettingsPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new MigLayout("wrap", "[]30[]", "[]10[]"));
        initComponents();
    }

    private void initComponents() {
        // 主题
        {
            themeLabel = new JLabel();
            themeLabel.setText(Messages.getString("settings.theme"));
            add(themeLabel);
            themeComboBox = new JComboBox<>();
            themeComboBox.addItem(new KV("dark", Messages.getString("settings.theme.dark")));
            themeComboBox.addItem(new KV("light", Messages.getString("settings.theme.light")));
            themeComboBox.addItem(new KV("system", Messages.getString("settings.theme.system")));
            themeComboBox.addActionListener(e -> changeTheme());
            add(themeComboBox, "width 200");
        }
        // 语言
        {
            languageLabel = new JLabel();
            languageLabel.setText(Messages.getString("settings.language"));
            add(languageLabel);
            languageComboBox = new JComboBox<>();
            languageComboBox.addItem(new KV(Messages.LOCAL_ZH, Messages.getString("settings.language.zh")));
            languageComboBox.addItem(new KV(Messages.LOCAL_EN, Messages.getString("settings.language.en")));
            languageComboBox.addActionListener(e -> changeLanguage());
            add(languageComboBox, "width 200");
        }
        // 主题色
        {
            accentColorLabel = new JLabel();
            accentColorLabel.setText(Messages.getString("settings.accentColor"));
            add(accentColorLabel);
            JPanel accentColorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
            ButtonGroup group = new ButtonGroup();
            for (int i = 0; i < accentColorButtons.length; i++) {
                accentColorButtons[i] = new JToggleButton(new AccentColorIcon(accentColorKeys[i]));
                accentColorButtons[i].setToolTipText(accentColorNames[i]);
                accentColorButtons[i].addActionListener(this::accentColorChanged);
                // 显示在同一行
                accentColorPanel.add(accentColorButtons[i]);
                group.add(accentColorButtons[i]);
            }
            add(accentColorPanel, "width 200");
            accentColorButtons[0].setSelected(true);
            FlatLaf.setSystemColorGetter(name -> name.equals("accent") ? accentColor : null);

            UIManager.addPropertyChangeListener(e -> {
                if ("lookAndFeel".equals(e.getPropertyName()))
                    updateAccentColorButtons();
            });
            updateAccentColorButtons();
        }
        // 缩放
        {
            zoomLabel = new JLabel();
            zoomLabel.setText(Messages.getString("settings.zoom"));
            add(zoomLabel);
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
            add(zoomSlider, "width 200");
        }
        // init select
        initSelect();
    }

    private void updateAccentColorButtons() {
        Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
        boolean isAccentColorSupported =
                lafClass == FlatLightLaf.class ||
                        lafClass == FlatDarkLaf.class ||
                        lafClass == FlatIntelliJLaf.class ||
                        lafClass == FlatDarculaLaf.class ||
                        lafClass == FlatMacLightLaf.class ||
                        lafClass == FlatMacDarkLaf.class;

        accentColorLabel.setVisible(isAccentColorSupported);
        for (JToggleButton accentColorButton : accentColorButtons) accentColorButton.setVisible(isAccentColorSupported);
    }

    private void accentColorChanged(ActionEvent e) {
        String accentColorKey = null;
        for (int i = 0; i < accentColorButtons.length; i++) {
            if (accentColorButtons[i].isSelected()) {
                accentColorKey = accentColorKeys[i];
                break;
            }
        }

        accentColor = (accentColorKey != null && accentColorKey != accentColorKeys[0])
                ? UIManager.getColor(accentColorKey)
                : null;

        Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
        try {
            FlatLaf.setup(lafClass.getDeclaredConstructor().newInstance());
            FlatLaf.updateUI();
        } catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
        }
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
        String language = Messages.getLocale();
        if (language.equals(Messages.LOCAL_ZH)) {
            languageComboBox.setSelectedIndex(0);
        } else {
            languageComboBox.setSelectedIndex(1);
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
//        int dialog = JOptionPane.showConfirmDialog(this,
//                Messages.getString("settings.restart"),
//                Messages.getString("settings"),
//                JOptionPane.DEFAULT_OPTION,
//                JOptionPane.INFORMATION_MESSAGE);
//        if (dialog == JOptionPane.OK_OPTION) {
//            ProcessUtils.relaunch(App.class.getName());
//        }
    }
}
