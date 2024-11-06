
public class Main {
    public static void main(String[] args) {
        Node <Integer> n1 = new Node <Integer>(5);
        Node <String> n2 = new Node <String>("Java");
        Node <Double> n3 = new Node <Double>(10.8);

        Node head = n1;
        head.next = n2;
        System.out.println("Hello");
        System.out.println(n1.data);
        System.out.println(n3.data);
        LinkedList<Integer> l1 = new LinkedList<Integer>();
        l1.insert(n1.data);
        Integer x= l1.retrieve();
        System.out.println(x);
    }
}