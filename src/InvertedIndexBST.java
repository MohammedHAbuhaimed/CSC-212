//inverted by binary search tree
public class InvertedIndexBST { 
    BST<Word> inverted_index;
    
    public InvertedIndexBST(){
        inverted_index=new BST<Word>();
    }
    
    public void addWord(String text, int id) {
        //If the word isn't found 
        if (!searchWord(text) ){
            Word word = new Word(text);
            word.doc_IDS.insert(id);
            inverted_index.insert(text,word);
        }
        else {
            Word existingWord = inverted_index.retrieve();
            existingWord.addID(id);
        }
    }
    
    public void addFromInvertedList(InvertedIndex inverted){
        if(inverted.inverted_index.empty())
            return;
        
        inverted.inverted_index.findFirst();
        while(!inverted.inverted_index.last()){
            inverted_index.insert(inverted.inverted_index.retrieve().text, inverted.inverted_index.retrieve());
            
            inverted.inverted_index.findNext();
        }
        
        inverted_index.insert(inverted.inverted_index.retrieve().text, inverted.inverted_index.retrieve());
    }

    public boolean searchWord(String word) {
        return inverted_index.search(word);
    }
    
    public void displayInvertedIndexBST(){
        if (inverted_index==null) {
            System.out.println("Null inverted index");
            return;
        }
        else if (inverted_index.empty()){
            System.out.println("Empty inverted index");
            return;
        }
       inverted_index.inOrder();
    }
    
    

}
