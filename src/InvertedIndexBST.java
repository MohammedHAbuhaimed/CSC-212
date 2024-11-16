public class InvertedIndexBST {
    BST<Word> invertedindexBST;
    public InvertedIndexBST(){
        invertedindexBST = new BST<Word>();
    }
    public void add(String text, int id){
        if(!searchWordInInvertedIndex(text)){
            Word word = new Word(text);
            word.documentIDs.insert(id);
            invertedindexBST.insert(text,word);
        }
        else{
            Word wordExists = invertedindexBST.retrieve();
            wordExists.addID(id);
        }
    }

    public boolean searchWordInInvertedIndex(String w){
        return invertedindexBST.findkey(w);
    }
    public void display() {
        if (invertedindexBST == null) {
            System.out.println("Null inverted index list");
            return;
        } else if (invertedindexBST.empty()) {
            System.out.println("Empty inverted index list");
            return;
        }

        invertedindexBST.inOrder();


    }
}