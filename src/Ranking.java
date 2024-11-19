import com.sun.jdi.connect.spi.TransportService;

import javax.print.Doc;

public class Ranking {
    public static String Query;
    public static InvertedIndexBST inverted;
    public static Index index;
    public static LinkedList<Integer> DocumentsInQuery;
    public static LinkedList<DocumentRank> DocumentRanked;

public Ranking(InvertedIndexBST bst,Index i,String Q){
    inverted = bst;
    index = i;
    Query = Q;
    DocumentsInQuery = new LinkedList<Integer>();
    DocumentRanked = new LinkedList<DocumentRank>();
}

public static void display(){
    if(DocumentRanked.empty()){
        System.out.println("Empty");
        return;
    }
    System.out.printf("%-8s%-8s\n","DocumentID","Score");
    DocumentRanked.findFirst();
    while(!DocumentRanked.last()){
    DocumentRanked.retrieve().display();
    DocumentRanked.findNext();
    }
    DocumentRanked.retrieve().display();
}



public static Document getDocumentGivenID(int id){
    return  index         // should be fixed
}



public static int termCounterInDocument(Document d , String term){
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

public static int getDocumentScore(Document d, String Query){
    if(Query.length()==0)
        return 0;
    String terms[]= Query.split(" ");
    int sumFrequency = 0;
    for (int i = 0; i < terms.length; i++) {
            sumFrequency+=termCounterInDocument(d,terms[i].trim().toLowerCase());
    }
return sumFrequency;
}

public static void RankQuery(){
    LinkedList<Integer> L = new LinkedList<Integer>();
    if(Query.length()==0) return;
    String terms[]= Query.split(" ");
    boolean found = false;
    for (int i = 0; i < terms.length; i++) {
            found=inverted.searchWordInInvertedIndex(terms[i].trim().toLowerCase());
            if(found)
                L=inverted.invertedindexBST.retrieve().documentIDs;
            addingInOneListSorted(L);
    }
}

public static void addingInOneListSorted(LinkedList<Integer>L){
if(L.empty())
    return;

L.findFirst();
    while (!L.last()) {
        boolean found = existsInResult(DocumentsInQuery,L.retrieve());
        if(!found){
            insertSortedIDs(L.retrieve());
        }
        if(!L.last())
            L.findNext();
        else
            break;
    }
}

public static boolean existsInResult(LinkedList<Integer>result ,int id){
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

    public static void insertSortedIDs(Integer id){
    if(DocumentsInQuery.empty()){
        DocumentsInQuery.insert(id);
        return;
    }

    DocumentsInQuery.findFirst();
    while (!DocumentsInQuery.last()) {
        if (id < DocumentsInQuery.retrieve()) {
            Integer ID = DocumentsInQuery.retrieve();
            DocumentsInQuery.update(id);
            DocumentsInQuery.insert(id);
            return;
        } else
            DocumentsInQuery.findNext();
    }
    if(id<DocumentsInQuery.retrieve()) {
        Integer ID = DocumentsInQuery.retrieve();
        DocumentsInQuery.update(id);
        DocumentsInQuery.insert(id);
        return;
    }
    else
        DocumentsInQuery.insert(id);
    }
    public static void insertSortedInList(){
    RankQuery(Query);
    if(DocumentsInQuery.empty()){
        System.out.println("Empty query");
        return;
    }
    DocumentsInQuery.findFirst();
    while (!DocumentsInQuery.last()) {
        Document d = getDocumentGivenID(DocumentsInQuery.retrieve());
        int rank = getDocumentScore(d, Query);
        insertSortedInList (new DocumentRank(DocumentsInQuery.retrieve(), rank));
        DocumentsInQuery.findNext();
    }
        Document d = getDocumentGivenID(DocumentsInQuery.retrieve());
        int rank = getDocumentScore(d, Query);
        insertSortedInList (new DocumentRank(DocumentsInQuery.retrieve(), rank));
    }
    public static void insertSortedInList(DocumentRank rank){
    if(DocumentRanked.empty()){
        DocumentRanked.insert(rank);
        return;
    }
    DocumentRanked.findFirst();
    while (!DocumentRanked.last()) {
        if(rank.rank > DocumentRanked.retrieve().rank){
            DocumentRank rank2 = DocumentRanked.retrieve();
            DocumentRanked.update(rank);
            DocumentRanked.insert(rank2);
            return;
        }
        else
            DocumentRanked.findNext();
        }
        if(rank.rank > DocumentRanked.retrieve().rank) {
            DocumentRank rank2 = DocumentRanked.retrieve();
            DocumentRanked.update(rank);
            DocumentRanked.insert(rank2);
            return;
        }
        else
            DocumentRanked.insert(rank);

    }
}
