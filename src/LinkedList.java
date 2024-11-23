
public class LinkedList <T> implements List<T>{

    private Node<T> head;
    private Node<T> current;
    int n=0;
    public LinkedList() { // list is empty > head=null
        head=current=null;
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
    public void findFirst() {
        current=head;

    }
    @Override
    public void findNext() {


            current=current.next;
    }
    @Override
    public boolean last() {
      return current.next==null;
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
        n++;
        Node<T> tmp;
        if(empty())
            head=current=new Node<T>(e);


        else {

         tmp=current.next;
         current.next=new Node<T> (e);
         current=current.next;
         current.next=tmp;

        }

    }

    @Override
    public void remove() {

        if(current==head) {
            head=head.next;

        }
        else {


            Node<T> p = head;

            while (p.next != current) {
                p = p.next;
            }
            p.next = current.next;
        }
        if(current.next==null)
            current=head;
        else
            current=current.next;

    }

    public boolean isEmpty() {
        return head==null;

    }

    // Helping methods:
    public boolean search(T x){
        Node<T> temp = head;
        while(temp!=null){
            if(temp.data.equals(x))
                return true;

            temp = temp.next;
        }
        return false;
    }
    public void display(){
        if(this == null)
            System.out.println("Null list");
        if(head == null)
            System.out.println("Empty list");

        Node<T> p = head;
        while(p!=null){
            System.out.print(p.data+" ");
            p = p.next;
        }
    }




}
