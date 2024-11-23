public class Node <T> {
        public T data;
        public Node<T> next;

        public Node (T d) {
            next=null;
            data=d;
        }
        public Node () {
            next = null;
            data = null;
        }

}
