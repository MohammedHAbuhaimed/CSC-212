public class BST <T> {
    private BSTNode<T> root, current;

    public BST() {
        root = current = null;
    }

    public boolean empty() {
        return root == null;
    }

    public boolean full() {
        return false;
    }

    public T retrieve() {
        return current.data;
    }

    public boolean findkey(String key) {
        BSTNode<T> p = root;

        if (empty())
            return false;

        while (p != null) {
            current = p;
            if (key.compareToIgnoreCase(p.key) == 0)
                return true;
            else if (key.compareToIgnoreCase(p.key) < 0)
                p = p.left;
            else
                p = p.right;


        }
        return false;
    }
    public boolean insert(String k, T val) {
        if (root == null) {
            current = root = new BSTNode<>(k, val);
            return true;
        }
        BSTNode<T> p = current;
        if (findkey(k)) {
            current = p;  // findkey() modified current
            return false; // key already in the BST
        }
        BSTNode<T> tmp = new BSTNode<T>(k, val);
        if (k.compareToIgnoreCase(current.key) < 0) {
            current.left = tmp;
        } else
            current.right = tmp;
        current = tmp;
        return true;
    }
    public void inOrder() {
        if (root == null) {
            System.out.println("The Tree is empty");
        } else
            inOrder(root);
    }
    private void inOrder(BSTNode<T> p) {
        if(p==null)
            return;
        inOrder(p.left);
        System.out.println("Key: "+ p.key);
        System.out.println("Data: "+p.data);
        inOrder(p.right);
    }
    public void preOrder() {
        if(root==null)
            System.out.println("The Tree is empty");
        else
            preOrder(root);
    }
    private void preOrder(BSTNode<T> p) {
        if(p==null)
            return;
        System.out.println("Key: "+ p.key);
        System.out.println("Data" +p.data);
        preOrder(p.left);
        preOrder(p.right);
    }
}

//