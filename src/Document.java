public class Document { //This class is used for indexing
    int id; // unique id for each document
    LinkedList<String> words = new LinkedList<String>(); // linked list of words, every word is a node except for the stop words
    String content;
    public Document(int id, LinkedList<String> words, String content) {

        this.id = id;
        this.content=content;
        this.words = words;

    }
}
//