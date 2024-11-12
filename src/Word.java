public class Word { //This class is mainly used for inverted index
    String text; // The word itself, Ex: National
    LinkedList<Integer> documentIDs; //linked list of ids of each doc

    public Word(String text){
        this.text = text;
        documentIDs = new LinkedList<Integer>();
    }
    public void addID(int id){ // This method will add the ID into the list after checking if it's already exists or not
        if(!searchIdExists(id)) // checking if the id doesn't exist in the linked list "documentIDs" by calling the method searchIdExists
            documentIDs.insert(id); // inserting it after checking it is not already in the list
    }
    public boolean searchIdExists(Integer id){ // This method will search if the id exists in the list or not
        if(documentIDs.empty()) // list is empty
            return false;
        documentIDs.findFirst(); // moving the current to the head to start searching
        while(!documentIDs.last()) // keep searching while the current is not the last
            if(documentIDs.retrieve().equals(id)) {
                return true;
            }
        documentIDs.findNext();

        if(documentIDs.retrieve().equals(id)) { // checking for the last element
            return true;
        }

        return false;
    }


    public void display(){
        System.out.println("\n------------------------------");
        System.out.print("Word:"+ text);
        System.out.print("[");
        documentIDs.display();
        System.out.println("]");
    }






}