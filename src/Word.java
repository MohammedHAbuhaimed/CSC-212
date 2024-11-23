public class Word { //This class is mainly used for inverted index1
    String text; // The word itself, Ex: National
    LinkedList<Integer> doc_IDS; //linked list of ids of each doc

    public Word(String text){
        this.text = text;
        doc_IDS = new LinkedList<Integer>();
    }
    public void add_ID(int id){ // This method will add the ID into the list after checking if it's already exists or not
        if(!existIn_doc_IDS(id)) // checking if the id doesn't exist in the linked list "documentIDs" by calling the method searchIdExists
            doc_IDS.insert(id); // inserting it after checking it is not already in the list
    }
    public boolean existIn_doc_IDS(Integer id){ // This method will search if the id exists in the list or not
        if(doc_IDS.empty()) // list is empty
            return false;
        doc_IDS.findFirst(); // moving the current to the head to start searching
        while(!doc_IDS.last()) { // keep searching while the current is not the last
            if (doc_IDS.retrieve().equals(id)) {
                return true;
            }
            doc_IDS.findNext();
        }
        if(doc_IDS.retrieve().equals(id)) { // checking for the last element
            return true;
        }

        return false;
    }


    public void display(){
        System.out.println("\n------------------------------");
        System.out.print("Word:"+ text);
        System.out.print("[");
        doc_IDS.display();
        System.out.println("]");
    }



//


}