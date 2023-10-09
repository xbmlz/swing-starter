package io.github.xbmlz.ui.component;

import javax.swing.*;
import java.awt.*;

public class JEmptyBox extends JPanel {

    public JEmptyBox() {
        this(0, 0);
    }


    public JEmptyBox(int w, int h) {
        setPreferredSize(new Dimension(w, h));
        setSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        setOpaque(false);
        setBorder(null);
    }
}
