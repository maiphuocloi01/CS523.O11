package listeners;

import drawer.DrawingPanel;
import redblacktree.RedBlackTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddRandomNodesAction implements ActionListener {
    private final RedBlackTree redBlackTree;
    private final DrawingPanel panel;
    private final JScrollPane pane;

    private void addNodesToTree(String numberOfNodes) {
        try {
            if (Integer.parseInt(numberOfNodes) <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number");
            } else {
                generateRandomNodes(Integer.parseInt(numberOfNodes));
                JScrollBar horizontalScrollBar = pane.getHorizontalScrollBar();
                horizontalScrollBar.setValue(1850);
                panel.repaint();
            }
        } catch (NumberFormatException exp) {
            JOptionPane.showMessageDialog(null, "Enter digits only");
        }
    }

    private void generateRandomNodes(int numberOfNodes) {
        List<Integer> generateRandomList = generateRandomList(numberOfNodes);
        for (Integer integer : generateRandomList) {
            redBlackTree.insert(integer);
        }
    }

    public static List<Integer> generateRandomList(int quantity) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers.subList(0, quantity);
    }

    public AddRandomNodesAction(RedBlackTree theTree, DrawingPanel drawingPanel, JScrollPane scrollPane) {
        this.redBlackTree = theTree;
        this.panel = drawingPanel;
        this.pane = scrollPane;
    }

    public void actionPerformed(ActionEvent e) {
        String numberOfNodes = JOptionPane.showInputDialog("Enter the number of nodes to add", "20");
        if (numberOfNodes == null) return;
        addNodesToTree(numberOfNodes);
    }
}