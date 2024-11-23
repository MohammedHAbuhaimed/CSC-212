public class InvertedIndex {
    LinkedList<Word> invertedIndex;

    public InvertedIndex(){
        invertedIndex = new LinkedList<Word>();
    }
    public void add(String text, int id){
        if(!searchWordInInvertedIndex(text)){
            Word w = new Word(text);
            w.docIDS.insert(id);
            invertedIndex.insert(w);
        }
        else{
            Word existing_word = invertedIndex.retrieve();
            existing_word.addID(id);
        }
    }

    public boolean searchWordInInvertedIndex(String word){
        if(invertedIndex ==null || invertedIndex.empty()) //checks if the list is empty
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

    public void displayInvertedIndex(){
        if(invertedIndex ==null) {
            System.out.println("Null inverted index1 list");
            return;
        }
        else if (invertedIndex.empty()) {
            System.out.println("Empty inverted index1 list");
            return;
        }

            invertedIndex.findFirst();
            while(!invertedIndex.last()){
                invertedIndex.retrieve().display();

                invertedIndex.findNext();
            }
            invertedIndex.retrieve().display();

    }


}//