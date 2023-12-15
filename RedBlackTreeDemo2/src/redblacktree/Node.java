package redblacktree;

public class Node {

    public int value;
    public boolean colorIsRed;
    public Node parent, left, right;

    public Node() {
        colorIsRed = false;
        parent = left = right = null;
        value = -1;
    }

    public Node(Node NIL) {
        colorIsRed = true;
        parent = left = right = NIL;
    }
}
