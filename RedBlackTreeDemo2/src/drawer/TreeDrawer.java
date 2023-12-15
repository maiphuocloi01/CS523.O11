package drawer;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import listeners.*;
import redblacktree.RedBlackTree;

public class TreeDrawer {

    private JFrame jFrame;
    private DrawingPanel panel;
    private JScrollPane jScrollPane;
    private final RedBlackTree redBlackTree;

    public TreeDrawer() {
        redBlackTree = new RedBlackTree();
        initialize();
    }

    private void initialize() {
        ArrayList<Integer> dimensions = getScreenDimensions();
        int screenWidth = dimensions.get(0);
        int screenHeight = dimensions.get(1);

        panel = DrawingPanel.getNewDrawingPanel(redBlackTree);
        jScrollPane = constructScrollPane(screenWidth, screenHeight);
        jFrame = constructFrame(screenWidth, screenHeight);

        JPanel jPanel = constructButtonsPanel();
        setContentContainer(jPanel, screenWidth);

        jFrame.setVisible(true);
    }

    private ArrayList<Integer> getScreenDimensions() {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add((int) (defaultToolkit.getScreenSize().getWidth()));
        integerArrayList.add((int) (defaultToolkit.getScreenSize().getHeight()));
        return integerArrayList;
    }

    private JFrame constructFrame(int screenWidth, int screenHeight) {
        JFrame frame1 = new JFrame("Red Black Tree Demo Nhóm 12");
        frame1.setSize(screenWidth, screenHeight);
        frame1.setLocation(0, 0);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame1;
    }

    private JScrollPane constructScrollPane(int screenWidth, int screenHeight) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(screenWidth - 150, screenHeight - 200));
        return scrollPane;
    }

    private JPanel constructButtonsPanel() {
        ArrayList<JButton> jButtons = constructButtons();
        JPanel buttonPanel = new JPanel();
        for (var button : jButtons) {
            buttonPanel.add(button);
        }
        return buttonPanel;
    }

    private ArrayList<JButton> constructButtons() {
        ArrayList<JButton> btn = new ArrayList<>();
        JButton addNodesButton = new JButton("Add random nodes");
        addNodesButton.addActionListener(new AddRandomNodesAction(redBlackTree, panel, jScrollPane));
        btn.add(addNodesButton);

        JButton setDiameterButton = new JButton("Search");
        setDiameterButton.addActionListener(new SearchAction(redBlackTree, panel));
        btn.add(setDiameterButton);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddNodeAction(redBlackTree, panel, jScrollPane));
        btn.add(addButton);

        JButton deleteButton = new JButton("Remove");
        deleteButton.addActionListener(new DeleteNodeAction(redBlackTree, panel));
        btn.add(deleteButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearPanelAction(redBlackTree, panel));
        btn.add(clearButton);
        return btn;
    }

    private void setContentContainer(JPanel buttonPanel, int screenWidth) {
        Container con = jFrame.getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.CENTER, screenWidth, 20));
        con.add(new JScrollPane(buttonPanel));
        con.add(jScrollPane, BorderLayout.CENTER);
    }
}