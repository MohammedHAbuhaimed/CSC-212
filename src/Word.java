public class Word { //This class is mainly used for inverted index1
    String text; // The word itself, Ex: National
    LinkedList<Integer> docIDS; //linked list of ids of each doc

    public Word(String text){
        this.text = text;
        docIDS = new LinkedList<Integer>();
    }
    public void addID(int id){ // This method will add the ID into the list after checking if it's already exists or not
        if(!existInDocIDS(id)) // checking if the id doesn't exist in the linked list "documentIDs" by calling the method searchIdExists
            docIDS.insert(id); // inserting it after checking it is not already in the list
    }
    public boolean existInDocIDS(Integer id){ // This method will search if the id exists in the list or not
        if(docIDS.empty()) // list is empty
            return false;
        docIDS.findFirst(); // moving the current to the head to start searching
        while(!docIDS.last()) { // keep searching while the current is not the last
            if (docIDS.retrieve().equals(id)) {
                return true;
            }
            docIDS.findNext();
        }
        if(docIDS.retrieve().equals(id)) { // checking for the last element
            return true;
        }

        return false;
    }


    public void display(){
        System.out.println("\n----------------------------------------------");
        System.out.print("Word: "+ text);
        System.out.print("[");
        docIDS.display();
        System.out.println("]");
    }



//


}