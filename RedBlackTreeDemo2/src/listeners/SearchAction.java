package listeners;

import drawer.DrawingPanel;
import redblacktree.Node;
import redblacktree.RedBlackTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchAction implements ActionListener {
    private final DrawingPanel drawingPanel;

    private final RedBlackTree theTree;

    private void changeDiameter(String newDiameter) {
        try {

            Node search = theTree.Search(Integer.parseInt(newDiameter));

            if (search.value == -1) {
                JOptionPane.showMessageDialog(null, "Can not find this node");
            } else {
                JOptionPane.showMessageDialog(null, newDiameter +" is already in the tree");
            }
            //drawingPanel.setNodeDiameter(Integer.parseInt(newDiameter));
            //drawingPanel.repaint();
        } catch (NumberFormatException exp) {
            JOptionPane.showMessageDialog(null, "Enter digits only");
        }
    }

    public SearchAction(RedBlackTree tree, DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        this.theTree = tree;
    }

    public void actionPerformed(ActionEvent e) {
        String newDiameter = JOptionPane.showInputDialog("Enter the diameter");
        if (newDiameter == null) return;
        changeDiameter(newDiameter);
    }
}
