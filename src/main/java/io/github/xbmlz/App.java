package io.github.xbmlz;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import io.github.xbmlz.ui.MainFrame;

import javax.swing.*;

public class App {

    public static MainFrame mainFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();
            FlatJetBrainsMonoFont.installLazy();
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
