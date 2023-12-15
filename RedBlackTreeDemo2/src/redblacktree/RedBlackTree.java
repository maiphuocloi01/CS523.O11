package redblacktree;

import java.awt.*;

public class RedBlackTree {

    private Node root;
    private final Node NIL;
    private int length;

    public RedBlackTree() {
        length = 0;
        NIL = new Node();
        root = NIL;
    }

    public int getSize() {
        return length;
    }

    public Node getRoot() {
        return root;
    }

    private int getDepth(Node currentNode, int level) {
        if (currentNode == null) return level;
        return Math.max(getDepth(currentNode.left, level + 1),
                getDepth(currentNode.right, level + 1));
    }

    private int getDepth() {
        return getDepth(root, 0);
    }

    private void leftRotate(Node currentNode) {
        Node y = currentNode.right; // set y equal the right child of currentNode
        currentNode.right = y.left; // turn y's left subtree into currentNode's subtree

        if (y.left != NIL)
            y.left.parent = currentNode; // if exists, set the parent to the left subtree to be currentNode

        y.parent = currentNode.parent; // set the parent of y to be the parent of currentNode

        if (currentNode.parent == NIL) root = y;
        else if (currentNode.parent.left == currentNode) currentNode.parent.left = y;
        else currentNode.parent.right = y;

        y.left = currentNode; // put currentNode on y's left
        currentNode.parent = y;
    }

    private void rightRotate(Node currentNode) {
        Node y = currentNode.left; // set y equal the left child of currentNode
        currentNode.left = y.right; // turn y's right subtree into currentNode's subtree

        if (y.right != NIL)
            y.right.parent = currentNode; // if exists, set the parent to the right subtree to be currentNode

        y.parent = currentNode.parent; // set the parent of y to be the parent of currentNode

        if (currentNode.parent == NIL) root = y;
        else if (currentNode.parent.left == currentNode) currentNode.parent.left = y;
        else currentNode.parent.right = y;

        y.right = currentNode; // put currentNode on y's right
        currentNode.parent = y;
    }

    private void transPlant(Node node, Node replacement) { // replace node with another node
        if (node.parent == NIL) root = replacement;
        else if (node == node.parent.left) node.parent.left = replacement;
        else node.parent.right = replacement;
        replacement.parent = node.parent;
    }

    private void insertFixUp(Node newNode) {
        while (newNode.parent.colorIsRed) { // as long as it is red
            // if the subtree is the left child of its parent
            if (newNode.parent == newNode.parent.parent.left) {
                var y = newNode.parent.parent.right;
                // case 1, if y's color is red, make y and its parent black and its grandparent red
                // then move the pointer to its grandparent for the next iteration
                if (y.colorIsRed) {
                    newNode.parent.colorIsRed = false; // black
                    y.colorIsRed = false; // black
                    newNode.parent.parent.colorIsRed = true; // red
                    newNode = newNode.parent.parent;
                } else {
                    // case 2, if newNode is the right child of its parent make a left rotation
                    // so that the right subtree of this tree is empty prior the right rotation
                    if (newNode == newNode.parent.right) {
                        newNode = newNode.parent;
                        leftRotate(newNode);
                    }
                    // case 3, do the right rotation and change coloring to maintain black-height property
                    newNode.parent.colorIsRed = false; // black
                    newNode.parent.parent.colorIsRed = true; // red
                    rightRotate(newNode.parent.parent);
                }
            } else { // symmetric to the above case but if the subtree was the right child of its parent
                var y = newNode.parent.parent.left;
                if (y.colorIsRed) {
                    newNode.parent.colorIsRed = false;
                    y.colorIsRed = false;
                    newNode.parent.parent.colorIsRed = true;
                    newNode = newNode.parent.parent;
                } else {
                    if (newNode == newNode.parent.left) {
                        newNode = newNode.parent;
                        rightRotate(newNode);
                    }
                    newNode.parent.colorIsRed = false; // black
                    newNode.parent.parent.colorIsRed = true; // red
                    leftRotate(newNode.parent.parent);
                }
            }
        }
        root.colorIsRed = false;
    }

    private void deleteFixUp(Node currentNode) {
        Node y;
        while (currentNode != root && !currentNode.colorIsRed) {
            if (currentNode == currentNode.parent.left) {
                y = currentNode.parent.right;
                if (y.colorIsRed) {
                    y.colorIsRed = false;
                    currentNode.parent.colorIsRed = true;
                    leftRotate(currentNode.parent);
                    y = currentNode.parent.right;
                }
                if (!y.left.colorIsRed && !y.right.colorIsRed) {
                    y.colorIsRed = true;
                    currentNode = currentNode.parent;
                } else {
                    if (!y.right.colorIsRed) {
                        y.left.colorIsRed = false;
                        y.colorIsRed = true;
                        rightRotate(y);
                        y = currentNode.parent.right;
                    }
                    y.colorIsRed = currentNode.parent.colorIsRed;
                    currentNode.parent.colorIsRed = false;
                    y.right.colorIsRed = false;
                    leftRotate(currentNode.parent);
                    currentNode = root;
                }
            } else {
                y = currentNode.parent.left;
                if (y.colorIsRed) {
                    y.colorIsRed = false;
                    currentNode.parent.colorIsRed = true;
                    rightRotate(currentNode.parent);
                    y = currentNode.parent.left;
                }
                if (!y.right.colorIsRed && !y.left.colorIsRed) {
                    y.colorIsRed = true;
                    currentNode = currentNode.parent;
                } else {
                    if (!y.left.colorIsRed) {
                        y.right.colorIsRed = false;
                        y.colorIsRed = true;
                        leftRotate(y);
                        y = currentNode.parent.left;
                    }
                    y.colorIsRed = currentNode.parent.colorIsRed;
                    currentNode.parent.colorIsRed = false;
                    y.left.colorIsRed = false;
                    rightRotate(currentNode.parent);
                    currentNode = root;
                }
            }
        }
        currentNode.colorIsRed = false;
    }

    private Node getMinimumNode(Node currentNode) { // find minimum node
        while (currentNode.left != NIL) { // while the subtree has a left child
            currentNode = currentNode.left; // point to that child
        }
        return currentNode; // return the most down-left node in the tree
    }

    public void insert(int value) {
        if (root == NIL) { // if the tree is empty
            root = new Node(NIL); // assign the root node to this value
            root.value = value;
            root.colorIsRed = false;
        } else {
            Node newNode = new Node(NIL); // create new node
            newNode.value = value;
            Node currentNode = root;
            Node currentParent = null;
            while (currentNode != NIL) { // traverse from the root to find the newNode position
                currentParent = currentNode; // trailer node for the parent of the currentNode
                if (value < currentNode.value) currentNode = currentNode.left; // traverse the left subtree
                else currentNode = currentNode.right; // traverse the right subtree
            }
            newNode.parent = currentParent; // the newNode parent is the trailer node
            if (value < currentParent.value) currentParent.left = newNode;
            else currentParent.right = newNode;

            insertFixUp(newNode);
        }
        ++length;
    }

    public Node Search(int value) {
        Node currentNode = root;
        while (currentNode != NIL &&
                currentNode.value != value) { // while value not found and currentNode not a null
            if (value < currentNode.value) currentNode = currentNode.left; // point to left subtree
            else currentNode = currentNode.right; // point to right subtree
        }
        return currentNode; // holds value or null
    }

    public void remove(Node z) {
        if (z == null) return;
        Node x, y = z;
        boolean originalColor = z.colorIsRed;
        if (z.left == NIL) {
            x = z.right;
            transPlant(z, x);
        } else if (z.right == NIL) {
            x = z.left;
            transPlant(z, x);
        } else {
            y = getMinimumNode(z.right);
            originalColor = y.colorIsRed;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transPlant(y, x);
                y.right = z.right;
                y.right.parent = y;
            }
            transPlant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.colorIsRed = z.colorIsRed;
        }
        if (!originalColor) deleteFixUp(x);
        --length;
    }

    private void traceTreePreorder(int x, int y, Node currentNode, Graphics g, int n, int level, int nodeDiameter) {
        if (currentNode == NIL) {
            return;
        }
        g.setColor((currentNode.colorIsRed) ? Color.red : Color.black);
        g.fillOval(x - nodeDiameter / 2 + 10, y - nodeDiameter / 2 - 5, nodeDiameter, nodeDiameter);

        int gap = (1300 / (n * nodeDiameter)) * Math.max(3, level);

        g.setColor(Color.black);
        if (currentNode.left != NIL) g.drawLine(x + 10, y + 5, x - gap, y + 70);
        if (currentNode.right != NIL) g.drawLine(x + 10, y + 5, x + gap + 30, y + 70);

        g.setColor(Color.white);
        g.drawString(Integer.toString(currentNode.value), x, y);

        traceTreePreorder(x - gap, y + 70, currentNode.left, g, 2 * n, level - 1, nodeDiameter);
        traceTreePreorder(x + gap + 30, y + 70, currentNode.right, g, 2 * n, level - 1, nodeDiameter);

    }

    public void drawTree(Graphics g, int nodeDiameter) {
        int width = getDepth();
        this.traceTreePreorder(2500, 50, root, g, 1, width * 2, nodeDiameter);
    }

    private void traceTreeSearch(int x, int y, Node currentNode, Graphics g, int n, int level, int nodeDiameter, int value) {
        if (currentNode == NIL) {
            return;
        }
        g.setColor((currentNode.colorIsRed) ? Color.red : Color.black);
        g.fillOval(x - nodeDiameter / 2 + 10, y - nodeDiameter / 2 - 5, nodeDiameter, nodeDiameter);

        int gap = (1300 / (n * nodeDiameter)) * Math.max(3, level);

        g.setColor(Color.black);
        if (currentNode.left != NIL) g.drawLine(x + 10, y + 5, x - gap, y + 70);
        if (currentNode.right != NIL) g.drawLine(x + 10, y + 5, x + gap + 30, y + 70);

        if (currentNode.value == value) {
            g.setColor(Color.yellow);
            g.drawString(Integer.toString(currentNode.value), x, y);
        } else {
            g.setColor(Color.white);
            g.drawString(Integer.toString(currentNode.value), x, y);
        }

        traceTreeSearch(x - gap, y + 70, currentNode.left, g, 2 * n, level - 1, nodeDiameter, value);
        traceTreeSearch(x + gap + 30, y + 70, currentNode.right, g, 2 * n, level - 1, nodeDiameter, value);

    }

    public void drawTreeSearch(Graphics g, int nodeDiameter, int value) {
        int width = getDepth();
        this.traceTreeSearch(2500, 50, root, g, 1, width * 2, nodeDiameter, value);
    }
}
