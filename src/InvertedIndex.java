public class InvertedIndex extends InvertedIndexBST {
    LinkedList<Word> invertedIndex;

    public InvertedIndex(){
        invertedIndex = new LinkedList<Word>();
    }
    public void add(String text, int id){
        if(!searchWordInInvertedIndex(text)){
            Word word = new Word(text);
            word.documentIDs.insert(id);
            invertedIndex.insert(word);
        }
        else{
            Word wordExists = invertedIndex.retrieve();
            wordExists.addID(id);
        }
    }

    public boolean searchWordInInvertedIndex(String word){
        if(invertedIndex==null || invertedIndex.empty()) //checks if the list is empty
            return false;

        invertedIndex.findFirst();
        while(!invertedIndex.last()) {
            if (invertedIndex.retrieve().text.equals(word)) {
                return true;
            }
            invertedIndex.findNext();
        }
        if(invertedIndex.retrieve().equals(word))
            return true;

        return false;
    }

    public void display(){
        if(invertedIndex==null) {
            System.out.println("Null inverted index list");
            return;
        }
        else if (invertedIndex.empty()) {
            System.out.println("Empty inverted index list");
            return;
        }
        else{
            invertedIndex.findFirst();
            while(!invertedIndex.last()){
                invertedIndex.retrieve().display();

                invertedIndex.findNext();
            }
            invertedIndex.retrieve().display();
        }
    }


}//