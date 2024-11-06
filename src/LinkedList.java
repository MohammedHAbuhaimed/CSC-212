
public class LinkedList <T> implements List<T>{

    private Node<T> head;
    private Node<T> current;
    public LinkedList() { // list is empty > head=null
        head=current=null;
    }
    public LinkedList(Node<T> n) {
        head=current=n;
    }


    @Override
    public boolean empty() {
        return head==null;
    }
    @Override
    public boolean full() {
        return false;
    }
    @Override
    public void FindFirst() {
        current=head;

    }
    @Override
    public void findNext() {
        if(last())
            return;
        else
            current=current.next;
    }
    @Override
    public boolean last() {
        if(current.next==null)
            return true;
        return false;
    }
    @Override
    public T retrieve() {
        return current.data;
    }
    @Override
    public void update(T e) {
        current.data=e;
    }
    @Override
    public void insert(T e) {
        Node<T> n = new Node<T>(e);
        if(empty())
            head=current=n;


        else {

            n.next=current.next;
            current.next=n;
            current=n;

        }

    }

    @Override
    public void remove() {

        if(current==head) {
            head=head.next;
            current=current.next;
        }
        if(last())
            current=head;


        Node<T> p = head;

        while(p.next!=current) {
            p=p.next;
        }
        p.next=current.next;

        if(current.next!=null)
            current=current.next;
        else
            current=head;

    }

    public boolean isEmpty() {
        return head==null;
    }

}
