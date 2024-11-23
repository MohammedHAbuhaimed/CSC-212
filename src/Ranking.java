public class Ranking {
    public static String Query;
    public static InvertedIndexBST inverted;
    public static index index1;
    public static LinkedList<Integer> all_doc_in_query;
    public static LinkedList<DocumentRank> all_doc_ranked;

public Ranking(InvertedIndexBST bst,index i,String Q){
    inverted = bst;
    index1 = i;
    Query = Q;
    all_doc_in_query = new LinkedList<Integer>();
    all_doc_ranked = new LinkedList<DocumentRank>();
}

public static void display_all_doc_with_score_usingList(){
    if(all_doc_ranked.empty()){
        System.out.println("Empty");
        return;
    }
    System.out.printf("%-8s%-8s\n","DocumentID","Score");
    all_doc_ranked.findFirst();
    while(!all_doc_ranked.last()){
    all_doc_ranked.retrieve().display();
    all_doc_ranked.findNext();
    }
    all_doc_ranked.retrieve().display();
}//



public static Document get_doc_given_id(int id){
    return  index1.get_document_givin_id(id);
}



public static int term_frequency_in_doc(Document d , String term){
   int counter = 0;
   LinkedList<String> words = d.words;
   if(words.empty())
       return 0;

   words.findFirst();
   while (!words.last()){
       if(words.retrieve().equalsIgnoreCase(term))
           counter++;
       words.findNext();
   }
   if(words.retrieve().equalsIgnoreCase(term))
       counter++;

   return counter;
}

public static int get_doc_rank_score(Document d, String Query){
    if(Query.length()==0)
        return 0;
    String terms[]= Query.split(" ");
    int sumFrequency = 0;
    for (int i = 0; i < terms.length; i++) {
            sumFrequency+= term_frequency_in_doc(d,terms[i].trim().toLowerCase());
    }
return sumFrequency;
}

public static void RankQuery(String query){
    LinkedList<Integer> L = new LinkedList<Integer>();
    if(Query.length()==0) return;
    String terms[]= Query.split("\\s+");
    boolean found = false;
    for (int i = 0; i < terms.length; i++) {
            found=inverted.search_word_in_inverted(terms[i].trim().toLowerCase());
            if(found)
                L=inverted.inverted_index.retrieve().doc_IDS;
            Adding_in_1_list_sorted(L);
    }
}

public static void Adding_in_1_list_sorted(LinkedList<Integer>L){
if(L.empty())
    return;

L.findFirst();
    while (!L.last()) {
        boolean found = existsIn_Result(all_doc_in_query,L.retrieve());
        if(!found){
            insert_sorted_Id_list(L.retrieve());
        }
        if(!L.last())
            L.findNext();
        else
            break;
    }
}

public static boolean existsIn_Result(LinkedList<Integer>result ,Integer id){
    if(result.empty())
        return false;

    result.findFirst();
    while (!result.last()) {
        if (result.retrieve().equals(id)) {
            return true;
        }
        result.findNext();
    }
    if(result.retrieve().equals(id))
        return true;

    return false;
    }

    public static void insert_sorted_Id_list(Integer id){
    if(all_doc_in_query.empty()){
        all_doc_in_query.insert(id);
        return;
    }

        all_doc_in_query.findFirst();
    while (!all_doc_in_query.last()) {
        if (id < all_doc_in_query.retrieve()) {
            Integer id1 = all_doc_in_query.retrieve();
            all_doc_in_query.update(id);
            all_doc_in_query.insert(id1);
            return;
        } else
            all_doc_in_query.findNext();
    }
    if(id<all_doc_in_query.retrieve()) {
        Integer id1 = all_doc_in_query.retrieve();
        all_doc_in_query.update(id);
        all_doc_in_query.insert(id1);
        return;
    }
    else {
        all_doc_in_query.insert(id);
    }
    }
    public static void insert_sorted_in_list(){
    RankQuery(Query);
    if(all_doc_in_query.empty()){
        System.out.println("Empty query");
        return;
    }
        all_doc_in_query.findFirst();
    while (!all_doc_in_query.last()) {
        Document d = get_doc_given_id(all_doc_in_query.retrieve());
        int rank = get_doc_rank_score(d, Query);
        insert_sorted_list (new DocumentRank(all_doc_in_query.retrieve(), rank));
        all_doc_in_query.findNext();
    }
        Document d = get_doc_given_id(all_doc_in_query.retrieve());
        int rank = get_doc_rank_score(d, Query);
        insert_sorted_list (new DocumentRank(all_doc_in_query.retrieve(), rank));
    }
    public static void  insert_sorted_list(DocumentRank dr){
    if(all_doc_ranked.empty()){
        all_doc_ranked.insert(dr);
        return;
    }
    all_doc_ranked.findFirst();
    while (!all_doc_ranked.last()) {
        if(dr.rank > all_doc_ranked.retrieve().rank){
            DocumentRank dr1 = all_doc_ranked.retrieve();
            all_doc_ranked.update(dr);
            all_doc_ranked.insert(dr1);
            return;
        }
        else
            all_doc_ranked.findNext();
        }
        if(dr.rank > all_doc_ranked.retrieve().rank) {
            DocumentRank dr1 = all_doc_ranked.retrieve();
            all_doc_ranked.update(dr);
            all_doc_ranked.insert(dr1);
            return;
        }
        else
            all_doc_ranked.insert(dr);

    }
}
