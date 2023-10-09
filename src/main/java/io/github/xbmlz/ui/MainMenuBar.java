package io.github.xbmlz.ui;

import com.formdev.flatlaf.extras.components.FlatButton;
import io.github.xbmlz.ui.resources.Icons;
import io.github.xbmlz.ui.plugin.I18n;

import javax.swing.*;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar() {
        initComponents();
    }

    private void initComponents() {
        // file
        {
            JMenu file = I18n.menu("menubar.file");
            file.setMnemonic('F');
            JMenuItem exit = I18n.menuItem("menubar.quit");
            exit.setMnemonic('X');
            exit.addActionListener(e -> Application.mainFrame.quit());
            file.add(exit);
            add(file);
        }
        // help
        {
            JMenu help = I18n.menu("menubar.help");
            help.setMnemonic('H');
            JMenuItem about = I18n.menuItem("menubar.about");
            about.setMnemonic('A');
            about.addActionListener(e -> Application.mainFrame.showAboutDialog());
            help.add(about);
            add(help);
        }
        // Settings
        {
            FlatButton settingsButton = new FlatButton();
            settingsButton.setIcon(Icons.SETTINGS);
            settingsButton.setButtonType(FlatButton.ButtonType.toolBarButton);
            settingsButton.setFocusable(false);
            add(Box.createGlue());
            add(settingsButton);
        }
    }
}
