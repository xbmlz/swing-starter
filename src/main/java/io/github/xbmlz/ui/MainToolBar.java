package io.github.xbmlz.ui;

import io.github.xbmlz.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;

public class MainToolBar extends JToolBar {

    public MainToolBar() {
        initComponents();
    }

    private void initComponents() {
        setMargin(new Insets(3, 3, 3, 3));

        JButton backButton = new JButton();
        backButton.setToolTipText("Back");
        backButton.setIcon(Icons.BACK);
        add(backButton);

        JButton forwardButton = new JButton();
        forwardButton.setToolTipText("Forward");
        forwardButton.setIcon(Icons.FORWARD);
        add(forwardButton);

        addSeparator();

        JButton cutButton = new JButton();
        cutButton.setToolTipText("Cut");
        cutButton.setIcon(Icons.CUT);
        add(cutButton);

        JButton copyButton = new JButton();
        copyButton.setToolTipText("Copy");
        copyButton.setIcon(Icons.COPY);
        add(copyButton);

        JButton pasteButton = new JButton();
        pasteButton.setToolTipText("Paste");
        pasteButton.setIcon(Icons.PASTE);
        add(pasteButton);
    }
}
