package drawer;

import redblacktree.RedBlackTree;

import javax.swing.*;
import java.awt.*;

import static drawer.Constant.SEARCH_VALUE;

public class DrawingPanel extends JPanel {
    private final RedBlackTree redBlackTree;
    static private DrawingPanel panel;
    private int diameter;

    private DrawingPanel(RedBlackTree theTree) {
        diameter = 50;
        this.redBlackTree = theTree;
    }

    static public DrawingPanel getNewDrawingPanel(RedBlackTree theTree) {
        if (panel == null) {
            panel = new DrawingPanel(theTree);
        }
        return panel;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setColor(Color.red);
        if (redBlackTree == null) return;
        setPreferredSize(new Dimension(5000, 5000));
        draw(g);
        revalidate();
    }

    private void draw(Graphics g) {
        g.setFont(new Font("Courier", Font.BOLD, diameter / 4));
        if (SEARCH_VALUE == -1) {
            redBlackTree.drawTree(g, diameter);
        } else {
            redBlackTree.drawTreeSearch(g, diameter, SEARCH_VALUE);
        }
    }
}