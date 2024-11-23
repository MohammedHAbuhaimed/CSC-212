//inverted by binary search tree
public class InvertedIndexBST { 
    BST<Word> invertedIndexBST;
    
    public InvertedIndexBST(){
        invertedIndexBST =new BST<Word>();
    }
    
    public void addWord(String text, int id) {
        //If the word isn't found 
        if (!searchWord(text) ){
            Word word = new Word(text);
            word.docIDS.insert(id);
            invertedIndexBST.insert(text,word);
        }
        else {
            Word existingWord = invertedIndexBST.retrieve();
            existingWord.addID(id);
        }
    }
    
    public void addFromInvertedList(InvertedIndex inverted){
        if(inverted.invertedIndex.empty())
            return;
        
        inverted.invertedIndex.findFirst();
        while(!inverted.invertedIndex.last()){
            invertedIndexBST.insert(inverted.invertedIndex.retrieve().text, inverted.invertedIndex.retrieve());
            
            inverted.invertedIndex.findNext();
        }
        
        invertedIndexBST.insert(inverted.invertedIndex.retrieve().text, inverted.invertedIndex.retrieve());
    }

    public boolean searchWord(String word) {
        return invertedIndexBST.search(word);
    }
    
    public void displayInvertedIndexBST(){
        if (invertedIndexBST ==null) {
            System.out.println("Null inverted index");
            return;
        }
        else if (invertedIndexBST.empty()){
            System.out.println("Empty inverted index");
            return;
        }
       invertedIndexBST.inOrder();
    }
    
    

}
