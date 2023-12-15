package listeners;

import drawer.DrawingPanel;
import redblacktree.Node;
import redblacktree.RedBlackTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNodeAction implements ActionListener {
    private final RedBlackTree redBlackTree;
    private final DrawingPanel drawingPanel;
    private final JScrollPane scrollPane;

    public AddNodeAction(RedBlackTree tree, DrawingPanel drawingPanel, JScrollPane scrollPane) {
        this.redBlackTree = tree;
        this.drawingPanel = drawingPanel;
        this.scrollPane = scrollPane;
    }

    private void addToTree(String newNodeValue) {
        try {
            if (Integer.parseInt(newNodeValue) <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number");
            } else {
                Node search = redBlackTree.Search(Integer.parseInt(newNodeValue));

                if (search.value != -1) {
                    JOptionPane.showMessageDialog(null, newNodeValue + " is already in the tree");
                } else {
                    redBlackTree.insert(Integer.parseInt(newNodeValue));
                    JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
                    horizontalScrollBar.setValue(1850);
                    drawingPanel.repaint();
                }
            }
        } catch (NumberFormatException exp) {
            JOptionPane.showMessageDialog(null, "Enter digits only");
        }
    }

    public void actionPerformed(ActionEvent e) {
        String newNodeValue = JOptionPane.showInputDialog("Enter data for new node");
        if (newNodeValue == null) return;
        addToTree(newNodeValue);
    }
}
