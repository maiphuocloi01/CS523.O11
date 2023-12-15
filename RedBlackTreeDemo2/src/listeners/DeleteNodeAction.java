package listeners;

import drawer.DrawingPanel;
import redblacktree.Node;
import redblacktree.RedBlackTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteNodeAction implements ActionListener {
    private final RedBlackTree theTree;
    private final DrawingPanel drawingPanel;

    private void delete(String valueToBeDeleted) {
        try {
            if (Integer.parseInt(valueToBeDeleted) <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number");
            } else {
                Node search = theTree.Search(Integer.parseInt(valueToBeDeleted));
                if (search.value == -1) {
                    JOptionPane.showMessageDialog(null, "Can not find this node");
                } else {
                    theTree.remove(theTree.Search(Integer.parseInt(valueToBeDeleted)));
                    drawingPanel.repaint();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Enter digits only");
        }
    }

    public DeleteNodeAction(RedBlackTree tree, DrawingPanel drawingPanel) {
        this.theTree = tree;
        this.drawingPanel = drawingPanel;
    }

    public void actionPerformed(ActionEvent e) {
        String valueToBeDeleted = JOptionPane.showInputDialog("Enter node to delete");
        if (valueToBeDeleted == null) return;
        delete(valueToBeDeleted);
    }
}
