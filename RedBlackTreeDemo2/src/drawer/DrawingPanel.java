package drawer;

import redblacktree.RedBlackTree;

import javax.swing.*;
import java.awt.*;

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

    public void setDiameter(int diameter) {
        this.diameter = diameter;
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
        redBlackTree.drawTree(g, diameter);
    }
}