package io.github.xbmlz.ui.component;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import io.github.xbmlz.App;
import io.github.xbmlz.ui.dialog.SettingsDialog;
import io.github.xbmlz.util.Constants;
import io.github.xbmlz.util.I18n;
import io.github.xbmlz.util.Icons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;

public class TopMenubar extends JMenuBar {

    private static final Logger log = LoggerFactory.getLogger(TopMenubar.class);

    private SettingsDialog settingsDialog;

    public TopMenubar() {
        initComponents();
    }

    public void initComponents() {
        // file
        {
            JMenu fileMenu = new JMenu(I18n.getString("toolbar.file"));
            fileMenu.setMnemonic('F');
            {
                JMenuItem exitMenuItem = new JMenuItem(I18n.getString("toolbar.exit"));
                exitMenuItem.setMnemonic('E');
                exitMenuItem.addActionListener(e -> System.exit(0));
                fileMenu.add(exitMenuItem);
            }
            add(fileMenu);
        }
        // help
        {
            JMenu helpMenu = new JMenu(I18n.getString("toolbar.help"));
            helpMenu.setMnemonic('H');
            {
                JMenuItem aboutMenuItem = new JMenuItem(I18n.getString("toolbar.about"));
                aboutMenuItem.setMnemonic('A');
                aboutMenuItem.addActionListener(e -> about());
                helpMenu.add(aboutMenuItem);
            }
            add(helpMenu);
        }
        // add "Users" button to menubar
        {
            FlatButton usersButton = new FlatButton();
            usersButton.setIcon(Icons.SETTINGS);
            usersButton.setButtonType(FlatButton.ButtonType.toolBarButton);
            usersButton.setFocusable(false);
            usersButton.addActionListener(e -> settings());
            add(Box.createGlue());
            add(usersButton);
        }
    }

    private void settings() {
        if (settingsDialog == null) {
            settingsDialog = new SettingsDialog();
        }
        settingsDialog.setVisible(true);
        settingsDialog.setLocationRelativeTo(App.mainFrame);
    }


    private void about() {
        JLabel titleLabel = new JLabel(Constants.APP_NAME);
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");

        String link = "https://github.com/xbmlz/swing-starter";
        JLabel linkLabel = new JLabel("<html><a href=\"#\">" + link + "</a></html>");
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(linkLabel,
                            "Failed to open '" + link + "' in browser.",
                            "About", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        JOptionPane.showMessageDialog(App.mainFrame,
                new Object[]{
                        titleLabel,
                        "Swing Application Fast Development Boilerplate",
                        " ",
                        "Copyright 2019-" + Year.now() + " xbmlz",
                        linkLabel,
                },
                "About", JOptionPane.PLAIN_MESSAGE);
    }
}
