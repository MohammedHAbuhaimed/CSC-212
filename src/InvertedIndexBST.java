public class InvertedIndexBST {
    BST<Word> inverted_index;
    public InvertedIndexBST(){
        inverted_index = new BST<Word>();
    }
    public void add(String text, int id){
        if(!search_word_in_inverted(text)){
            Word w = new Word(text);
            w.doc_IDS.insert(id);
            inverted_index.insert(text,w);
        }
        else{
            Word wordExists = inverted_index.retrieve();
            wordExists.add_ID(id);
        }
    }
    public void add_from_inverted_list(InvertedIndex inverted){
        if(inverted.inverted_index.empty())
            return;
        inverted.inverted_index.findFirst();
        while(!inverted.inverted_index.last()){
            inverted_index.insert(inverted.inverted_index.retrieve().text, inverted.inverted_index.retrieve());
            inverted.inverted_index.findNext();
        }
        inverted_index.insert(inverted.inverted_index.retrieve().text, inverted.inverted_index.retrieve());
    }
//
    public boolean search_word_in_inverted(String word){
        return inverted_index.findkey(word);
    }
    public void display_inverted_index() {
        if (inverted_index == null) {
            System.out.println("Null inverted index1 list");
            return;
        } else if (inverted_index.empty()) {
            System.out.println("Empty inverted index1 list");
            return;
        }

        inverted_index.inOrder();


    }
}