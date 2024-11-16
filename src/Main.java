
public class Main {
    public static void main(String[] args) {
//        Node <Integer> n1 = new Node <Integer>(5);
//        Node <String> n2 = new Node <String>("Java");
//        Node <Double> n3 = new Node <Double>(10.8);
//
//        Node head = n1;
//        head.next = n2;
//        System.out.println("Hello");
//        System.out.println(n1.data);
//        System.out.println(n3.data);
//        LinkedList<Integer> l1 = new LinkedList<Integer>();
//        l1.insert(n1.data);
//        Integer x= l1.retrieve();
//        System.out.println(x);
//
//     Reading.Load("dataset.csv");
//        BST<Double> bst = new BST<Double>();
//        System.out.println("Tree empty ? " + bst.empty());
//        System.out.println("============================");
//        System.out.println("Inserting 11 nodes");
//        bst.insert("A", 35.0);
//        bst.insert("B", 14.0);
//        bst.insert("C", 5.0);
//        bst.insert("D", 33.0);
//        bst.insert("E", 53.5);
//        bst.insert("F", 50.3);
//        bst.insert("G", 44.8);
//        bst.insert("H", 40.7);
//        System.out.println("All nodes: ");
//        bst.inOrder();
        Text t = new Text();
        t.loadAllFiles("stop.txt", "dataset.csv");
        t.index.displayDocuments();
        Query q = new Query(t.invertedIndex);
        LinkedList result = Query.andQuery("colorANDflag");
       // t.displayDocumentIDs(result);


    }
}