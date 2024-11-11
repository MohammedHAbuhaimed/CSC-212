public class Document {
    int id;
    LinkedList<String> words = new LinkedList<>();

    public Document(int id, LinkedList<String> words) {
        this.id = id;
        this.words = words;

    }
}
