public class index {
    LinkedList<Document> all_doc; // linked list of linked lists (document)
    public index() {
        all_doc = new LinkedList<Document>();
    }
    public void addDocument(Document d) { // This method will add the document into the list
        all_doc.insert(d);
    }
    public void displayDocuments() { // This method will display_inverted_index all the all_doc in the list
        if(all_doc ==null) { // The list is null
            System.out.println("No all_doc found");
            return;
        } else if (all_doc.isEmpty()) { // The list is empty
            System.out.println("document is empty ");
            return;
        }
        all_doc.findFirst(); // moving the current to the head
        while(!all_doc.last()){ // display_inverted_index all the all_doc while we haven't reached the last document
            Document doc = all_doc.retrieve(); // store the current document into the variable doc
            System.out.println("\n----------------------------------------------------"); // Splitting
            System.out.println("ID: "+ doc.id); // displaying the id for each document
            doc.words.display(); // display_inverted_index all the words in the document
            //System.out.println("\n-----------------------------------------"); // Splitting
            all_doc.findNext(); // move the current to the next document
        }
//
        // Displaying the last document
        Document doc = all_doc.retrieve();
        System.out.println("\n----------------------------------------------------");
        System.out.println("ID: "+ doc.id);
        doc.words.display();
        System.out.println("----------------------------------------------------");
    }
//    public Document displayDocumentsWithGivenIDs(Integer ids) {
//        if (all_doc.empty()) { // Check if the list is empty
//            System.out.println("No all_doc found.");
//            return null;
//        }
//
//        all_doc.findFirst(); // Move the current pointer to the head of the list
//        boolean found = false;
//
//        // Iterate through the list and check if the document ID matches any in the list
//        while (!all_doc.last()) { // While not at the last document
//            Document doc = all_doc.retrieve(); // Retrieve the current document
//            if (all_doc.retrieve().equals(ids)) { // Check if the current document's ID is in the given list of IDs
//                System.out.println("\n-----------------------------------------");
//                System.out.println("ID: " + doc.id); // Display the document's ID
//                doc.words.display_inverted_index(); // Display the words in the document
//                System.out.println("\n-----------------------------------------");
//                found = true;
//            }
//            all_doc.findNext(); // Move to the next document
//        }
//
//        // Display the last document in the list
//        Document doc = all_doc.retrieve();
//        if (all_doc.retrieve().equals(ids)) {
//            System.out.println("\n-----------------------------------------");
//            System.out.println("ID: " + doc.id); // Display the last document's ID
//            doc.words.display_inverted_index(); // Display the words in the document
//            System.out.println("\n-----------------------------------------");
//            found = true;
//        }
//
//        // If no matching all_doc were found
//        if (!found) {
//            System.out.println("No all_doc found with the given IDs.");
//        }
//        return all_doc.retrieve();
//    }
    public LinkedList<Integer> getAllDocumentGivenTerms(String term) {
        LinkedList<Integer> terms = new LinkedList<>();
        if (all_doc.empty()) {
            System.out.println("No all_doc found");
            return null;
        }
        all_doc.findFirst();
        while (!all_doc.last()) {
            if (all_doc.retrieve().words.equals(term.toLowerCase().trim())) {
                terms.insert(all_doc.retrieve().id);

            }
            all_doc.findNext();
        }
        if (all_doc.retrieve().words.equals(term.toLowerCase().trim())) {
            terms.insert(all_doc.retrieve().id);

        }
        return terms;
    }
    public Document getDocumentGivinId(int id){
        if(all_doc.empty()) {
            System.out.println("No documents found");
            return null;
        }
        all_doc.findFirst();
        while (!all_doc.last()) {
            if (all_doc.retrieve().id == id) {
                return all_doc.retrieve();
            }
            all_doc.findNext();
        }
        if (all_doc.retrieve().id == id) {
            return all_doc.retrieve();
        }
        return null;

    }
}