package listeners;

import drawer.Constant;
import drawer.DrawingPanel;
import redblacktree.Node;
import redblacktree.RedBlackTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchAction implements ActionListener {
    private final DrawingPanel drawingPanel;

    private final RedBlackTree theTree;

    private void searchNode(String newDiameter) {
        try {

            Node search = theTree.Search(Integer.parseInt(newDiameter));

            if (search.value == -1) {
                JOptionPane.showMessageDialog(null, "Can not find this node");
            } else {
                Constant.setSearchValue(search.value);
                drawingPanel.repaint();
                createAndShowMessage(newDiameter +" is already in the tree");
            }

        } catch (NumberFormatException exp) {
            JOptionPane.showMessageDialog(null, "Enter digits only");
        }
    }

    private static void createAndShowMessage(String message) {
        Object[] options = {"OK"};

        int result = JOptionPane.showOptionDialog(
                null,  // parentComponent, null for default
                message,  // message
                "Message",  // title
                JOptionPane.DEFAULT_OPTION,  // optionType
                JOptionPane.INFORMATION_MESSAGE,  // messageType
                null,  // icon
                options,  // options
                options[0]);  // initialValue

        if (result == 0) {
            Constant.setSearchValue(-1);
        }
    }

    public SearchAction(RedBlackTree tree, DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        this.theTree = tree;
    }

    public void actionPerformed(ActionEvent e) {
        String newDiameter = JOptionPane.showInputDialog("Enter the diameter");
        if (newDiameter == null) return;
        searchNode(newDiameter);
    }
}
