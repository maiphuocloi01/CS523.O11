package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import drawer.DrawingPanel;
import redblacktree.RedBlackTree;

public class ClearPanelAction implements ActionListener {
    private final RedBlackTree theTree;
    private final DrawingPanel drawingPanel;

    public ClearPanelAction(RedBlackTree tree, DrawingPanel drawingPanel) {
        this.theTree = tree;
        this.drawingPanel = drawingPanel;
    }

    public void actionPerformed(ActionEvent e) {
        while (theTree.getSize() != 0) {
            theTree.remove(theTree.getRoot());
        }
        drawingPanel.repaint();
    }
}