public class InvertedIndex {
    LinkedList<Word> inverted_index;

    public InvertedIndex(){
        inverted_index = new LinkedList<Word>();
    }
    public void add(String text, int id){
        if(!search_word_in_inverted(text)){
            Word w = new Word(text);
            w.doc_IDS.insert(id);
            inverted_index.insert(w);
        }
        else{
            Word existing_word = inverted_index.retrieve();
            existing_word.add_ID(id);
        }
    }

    public boolean search_word_in_inverted(String word){
        if(inverted_index==null || inverted_index.empty()) //checks if the list is empty
            return false;

        inverted_index.findFirst();
        while(!inverted_index.last()) {
            if (inverted_index.retrieve().text.equals(word)) {
                return true;
            }
            inverted_index.findNext();
        }
        if(inverted_index.retrieve().equals(word))
            return true;

        return false;
    }

    public void display_inverted_index(){
        if(inverted_index==null) {
            System.out.println("Null inverted index1 list");
            return;
        }
        else if (inverted_index.empty()) {
            System.out.println("Empty inverted index1 list");
            return;
        }

            inverted_index.findFirst();
            while(!inverted_index.last()){
                inverted_index.retrieve().display();

                inverted_index.findNext();
            }
            inverted_index.retrieve().display();

    }


}//