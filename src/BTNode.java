public class BTNode <T> {
    public T data;
    public BTNode<T> left, right;
    public BTNode(T data) {
        this.data = data;
        left = right = null;
    }
    public BTNode(BTNode<T> left, BTNode<T> right, T data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }
}
