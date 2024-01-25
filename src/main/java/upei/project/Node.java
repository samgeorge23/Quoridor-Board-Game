package upei.project;

abstract class Node {
    protected Node parent;
    protected Board state;

    protected Node(Board state) {
        this.state = state;
        this.parent = null;
    }
    protected Node(Node parent) {
        this.parent = parent;
        this.state = null;
    }
    protected Node(Board state, Node parent) {
        this.state = state;
        this.parent = parent;
    }
}
