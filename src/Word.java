public class Word {
    String text;
    LinkedList<Integer> documentIDs;

    public Word(String text){
        this.text = text;
        documentIDs = new LinkedList<Integer>();
    }
    public void addID(int id){
        if(!searchIdExists(id))
        documentIDs.insert(id);
    }
    public boolean searchIdExists(Integer id){
    if(documentIDs.empty())
        return false;
    documentIDs.FindFirst();
    while(!documentIDs.last())
        if(documentIDs.retrieve().equals(id)) {
            return true;
        }
        documentIDs.findNext();
        if(documentIDs.retrieve().equals(id)) {
            return true;
        }
        return false;

    }

}
