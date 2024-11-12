public class Index {
    LinkedList<Document> documents; // linked list of linked lists (document)
    public Index() {
        documents = new LinkedList<>();
    }
    public void addDocument(Document document) { // This method will add the document into the list
        documents.insert(document);
    }
    public void displayDocuments() { // This method will display all the documents in the list
        if(documents==null) { // The list is null
            System.out.println("No documents found");
            return;
        } else if (documents.isEmpty()) { // The list is empty
            System.out.println("document is empty ");
            return;
        }
        documents.findFirst(); // moving the current to the head
        while(!documents.last()){ // display all the documents while we haven't reached the last document
            Document doc = documents.retrieve(); // store the current document into the variable doc
           // System.out.println("\n-----------------------------------------"); // Splitting
            System.out.println("ID: "+ doc.id); // displaying the id for each document
            doc.words.display(); // display all the words in the document
            System.out.println("\n-----------------------------------------"); // Splitting
            documents.findNext(); // move the current to the next document
        }

        // Displaying the last document
        Document doc = documents.retrieve();
        System.out.println("\n-------------------------------------");
        System.out.println("ID: "+ doc.id);
        doc.words.display();

    }
}
