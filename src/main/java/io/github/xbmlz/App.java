package io.github.xbmlz;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import io.github.xbmlz.ui.MainFrame;
import io.github.xbmlz.ui.component.Splash;
import io.github.xbmlz.util.Constants;

import javax.swing.*;

public class App {

    public static MainFrame mainFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();
            FlatJetBrainsMonoFont.installLazy();
            createAndShowGUI();
        });
    }

    public static void createAndShowGUI() {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
