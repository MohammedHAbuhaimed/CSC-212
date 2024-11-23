public class Word {
    String text;
    LinkedList<Integer> docIDS;
    
    public Word(String W){
        text=W;
        docIDS =new LinkedList<Integer>();
        
    }
    
    public void addID(int id){
        if(!searchIdExists(id))
            docIDS.insert(id);
        
    }
    public boolean searchIdExists(Integer id){
        if(docIDS.empty())
            return false;
        
        docIDS.findFirst();
        while (!docIDS.last()){
            if(docIDS.retrieve().equals(id)){
                return true;
            }
            docIDS.findNext();
        }
        
        if (docIDS.retrieve().equals(id)){
                return true;
        }
      return false;
    }
    
        @Override
    public String toString() {
        // Format Word object for meaningful output
        return "";
    }

    public void display() {
        System.out.println("\n----------------------------------------------");
        System.out.println("Word: " + text);
        System.out.print("Document IDs: [");

        // Display doc_IDS contents
        docIDS.findFirst();
        while (!docIDS.last()) {
            System.out.print(docIDS.retrieve() + ", ");
            docIDS.findNext();
        }
        System.out.print(docIDS.retrieve()); // Last ID without a trailing comma
        System.out.println("]");
    }
    

}