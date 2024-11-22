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
            System.out.println("\n-----------------------------------------"); // Splitting
            System.out.println("ID: "+ doc.id); // displaying the id for each document
            doc.words.display(); // display all the words in the document
            //System.out.println("\n-----------------------------------------"); // Splitting
            documents.findNext(); // move the current to the next document
        }
//
        // Displaying the last document
        Document doc = documents.retrieve();
        System.out.println();
        System.out.println("ID: "+ doc.id);
        doc.words.display();

    }
    public Document displayDocumentsWithGivenIDs(Integer ids) {
        if (documents.empty()) { // Check if the list is empty
            System.out.println("No documents found.");
            return null;
        }

        documents.findFirst(); // Move the current pointer to the head of the list
        boolean found = false;

        // Iterate through the list and check if the document ID matches any in the list
        while (!documents.last()) { // While not at the last document
            Document doc = documents.retrieve(); // Retrieve the current document
            if (documents.retrieve().equals(ids)) { // Check if the current document's ID is in the given list of IDs
                System.out.println("\n-----------------------------------------");
                System.out.println("ID: " + doc.id); // Display the document's ID
                doc.words.display(); // Display the words in the document
                System.out.println("\n-----------------------------------------");
                found = true;
            }
            documents.findNext(); // Move to the next document
        }

        // Display the last document in the list
        Document doc = documents.retrieve();
        if (documents.retrieve().equals(ids)) {
            System.out.println("\n-----------------------------------------");
            System.out.println("ID: " + doc.id); // Display the last document's ID
            doc.words.display(); // Display the words in the document
            System.out.println("\n-----------------------------------------");
            found = true;
        }

        // If no matching documents were found
        if (!found) {
            System.out.println("No documents found with the given IDs.");
        }
        return documents.retrieve();
    }
    public LinkedList<Integer> getAllDocumentTerms(String term) {
        LinkedList<Integer> terms = new LinkedList<>();
        if (documents.empty()) {
            System.out.println("No documents found");
            return null;
        }
        documents.findFirst();
        while (!documents.last()) {
            if (documents.retrieve().words.equals(term.toLowerCase().trim())) {
                terms.insert(documents.retrieve().id);

            }
            documents.findNext();
        }
        if (documents.retrieve().words.equals(term.toLowerCase().trim())) {
            terms.insert(documents.retrieve().id);

        }
        return terms;
    }
    public Document getDocumentID(int id){
        if(documents.empty()) {
            System.out.println("No documents found");
            return null;
        }
        documents.findFirst();
        while (!documents.last()) {
            if (documents.retrieve().id == id) {
                return documents.retrieve();
            }
            documents.findNext();
        }
        if (documents.retrieve().id == id) {
            return documents.retrieve();
        }
        return null;

    }
}