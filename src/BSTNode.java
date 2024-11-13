public class BSTNode <T> {
    public String key;
    public T data;
    public BSTNode<T> left, right;

    /** Creates a new instance of BSTNode */
    public BSTNode(String k, T val) {
        key = k;
        data = val;
        left = right = null;
    }


}
