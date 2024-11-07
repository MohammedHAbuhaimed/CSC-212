public class BT <T>{
    BTNode<T> root, current;
    public BT(){
        root = null;
        current = null;
    }
    public boolean empty(){
        return root == null;
    }
    public T retrieve(){
        return current.data;
    }
    public void update(T val){
        current.data = val;
    }
    public boolean insert(Relative rel, T val){
        switch(rel) {
            case Root:
                if(!empty()) return false;
                current = root = new BTNode<T>(val);
                return true;
            case Parent:	//This is an impossible case.
                return false;
            case LeftChild:
                if(current.left != null) return false;
                current.left = new BTNode<T>(val);
                current = current.left;
                return true;
            case RightChild:
                if(current.right != null) return false;
                current.right = new BTNode<T> (val);
                current = current.right;
                return true;
            default:
                return false;
        }

    }
    public void deleteSubtree(){
        if(current == root){
            current = root = null;
        }
        else {
            BTNode<T> p = current;
            find(Relative.Parent);
            if(current.left == p)
                current.left = null;
            else
                current.right = null;
            current = root;
        }
    }
    public boolean find(Relative rel){
        switch (rel) {
            case Root:	// Easy case
                current = root;
                return true;
            case Parent:
                if(current == root) return false;
                current = findparent(current, root);
                return true;
            case LeftChild:
                if(current.left == null) return false;
                current = current.left;
                return true;
            case RightChild:
                if(current.right == null) return false;
                current = current.right;
                return true;
            default:
                return false;
        }
    }
    // Recursive version of findparent – preorder traversal used
    private BTNode<T> findparent(BTNode<T> p, BTNode<T> t) {
        if(t == null)
            return null;	// empty tree

        if(t.right == null && t.left == null)
            return null;
        else if(t.right == p || t.left == p)
            return t;	// parent is t
        else {
            BTNode q = findparent(p, t.left);
            if (q != null)
                return q;
            else
                return findparent(p, t.right);
        }
    }

}



