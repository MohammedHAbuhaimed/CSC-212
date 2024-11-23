public class InvertedIndexBST {
    BST<Word> invertedIndex;
    public InvertedIndexBST(){
        invertedIndex = new BST<Word>();
    }
    public void add(String text, int id){
        if(!searchWordInInverted(text)){
            Word w = new Word(text);
            w.docIDS.insert(id);
            invertedIndex.insert(text,w);
        }
        else{
            Word wordExists = invertedIndex.retrieve();
            wordExists.addID(id);
        }
    }
    public void addFromInvertedList(InvertedIndex inverted){
        if(inverted.invertedIndex.empty())
            return;
        inverted.invertedIndex.findFirst();
        while(!inverted.invertedIndex.last()){
            invertedIndex.insert(inverted.invertedIndex.retrieve().text, inverted.invertedIndex.retrieve());
            inverted.invertedIndex.findNext();
        }
        invertedIndex.insert(inverted.invertedIndex.retrieve().text, inverted.invertedIndex.retrieve());
    }
//
    public boolean searchWordInInverted(String word){
        return invertedIndex.findkey(word);
    }
    public void display_inverted_index() {
        if (invertedIndex == null) {
            System.out.println("Null inverted index1 list");
            return;
        } else if (invertedIndex.empty()) {
            System.out.println("Empty inverted index1 list");
            return;
        }

        invertedIndex.inOrder();


    }
}