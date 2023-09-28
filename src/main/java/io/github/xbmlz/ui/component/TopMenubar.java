package io.github.xbmlz.ui.component;

import com.formdev.flatlaf.FlatClientProperties;
import io.github.xbmlz.App;
import io.github.xbmlz.ui.MainFrame;
import io.github.xbmlz.util.Constants;
import io.github.xbmlz.util.Messages;
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

    public TopMenubar() {
        initComponents();
    }

    public void initComponents() {
        JMenu helpMenu = new JMenu(Messages.getString("toolbar.help"));
        helpMenu.setMnemonic('H');
        {
            JMenuItem aboutMenuItem = new JMenuItem(Messages.getString("toolbar.about"));
            aboutMenuItem.setMnemonic('A');
            aboutMenuItem.addActionListener(e -> about());
            helpMenu.add(aboutMenuItem);
        }
        add(helpMenu);
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
