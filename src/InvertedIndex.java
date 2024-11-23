//inverted by linkedList
public class InvertedIndex {
    LinkedList<Word> invertedIndex;
    
    public InvertedIndex(){
        invertedIndex =new LinkedList<Word>();
    }
    
    public void addWord(String text, int id) {
        //If the word isn't found 
        if (!searchWord(text) ){
            Word word = new Word(text);
            word.docIDS.insert(id);
            invertedIndex.insert(word);
        }
        else {
            Word existingWord = invertedIndex.retrieve();
            existingWord.addID(id);
        }
    }    

    public boolean searchWord(String word) {
        if (invertedIndex ==null || invertedIndex.empty())
            return false;
        invertedIndex.findFirst();
        while (!invertedIndex.last()) {
            if (invertedIndex.retrieve().text.equals(word))
                return true;
            invertedIndex.findNext();
        }
        if(invertedIndex.retrieve().equals(word)) //For the last word
            return true;
        return false;
    }
    
    public void displayInvertedIndex(){
        if (invertedIndex ==null) {
            System.out.println("Null inverted index");
            return;
        }
        else if (invertedIndex.empty()){
            System.out.println("Empty inverted index");
            return;
        }
        invertedIndex.findFirst();
        while (!invertedIndex.last()){
            invertedIndex.retrieve().display();
            invertedIndex.findNext();
        }
        //For the last word
        invertedIndex.retrieve().display();
     
    }
    
    
    
}
