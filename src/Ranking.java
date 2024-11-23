public class Ranking {
    public static String Query;
    public static InvertedIndexBST invertedIndexBST;
    public static index index;
    public static LinkedList<Integer> allDocInQuery;
    public static LinkedList<DocumentRank> allDocRanked;

public Ranking(InvertedIndexBST bst,index i,String Q){
    invertedIndexBST = bst;
    index = i;
    Query = Q;
    allDocInQuery = new LinkedList<Integer>();
    allDocRanked = new LinkedList<DocumentRank>();
}

public static void displayAllDocWithScoreUsingList(){
    if(allDocRanked.empty()){
        System.out.println("Empty");
        return;
    }
    System.out.printf("%-8s%-8s\n","DocID ","Score");
    allDocRanked.findFirst();
    while(!allDocRanked.last()){
    allDocRanked.retrieve().display();
    allDocRanked.findNext();
    }
    allDocRanked.retrieve().display();
}//



public static Document getDocGivenId(int id){
    return  index.getDocumentGivinId(id);
}



public static int termFrequencyInDoc(Document d , String term){
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

public static int getDocRankScore(Document d, String Query){
    if(Query.length()==0)
        return 0;
    String terms[]= Query.split(" ");
    int sumFrequency = 0;
    for (int i = 0; i < terms.length; i++) {
            sumFrequency+= termFrequencyInDoc(d,terms[i].trim().toLowerCase());
    }
return sumFrequency;
}

public static void RankQuery(String query){
    LinkedList<Integer> L = new LinkedList<Integer>();
    if(Query.length()==0) return;
    String terms[]= Query.split("\\s+");
    boolean found = false;
    for (int i = 0; i < terms.length; i++) {
            found= invertedIndexBST.searchWordInInverted(terms[i].trim().toLowerCase());
            if(found)
                L= invertedIndexBST.invertedIndex.retrieve().docIDS;
            addingInOneListSorted(L);
    }
}

public static void addingInOneListSorted(LinkedList<Integer>L){
if(L.empty())
    return;

L.findFirst();
    while (!L.last()) {
        boolean found = existsInResult(allDocInQuery,L.retrieve());
        if(!found){
            insertSortedIdList(L.retrieve());
        }
        if(!L.last())
            L.findNext();
        else
            break;
    }
}

public static boolean existsInResult(LinkedList<Integer>result , Integer id){
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

    public static void insertSortedIdList(Integer id){
    if(allDocInQuery.empty()){
        allDocInQuery.insert(id);
        return;
    }

        allDocInQuery.findFirst();
    while (!allDocInQuery.last()) {
        if (id < allDocInQuery.retrieve()) {
            Integer id1 = allDocInQuery.retrieve();
            allDocInQuery.update(id);
            allDocInQuery.insert(id1);
            return;
        } else
            allDocInQuery.findNext();
    }
    if(id< allDocInQuery.retrieve()) {
        Integer id1 = allDocInQuery.retrieve();
        allDocInQuery.update(id);
        allDocInQuery.insert(id1);
        return;
    }
    else {
        allDocInQuery.insert(id);
    }
    }
    public static void insertSortedInList(){
    RankQuery(Query);
    if(allDocInQuery.empty()){
        System.out.println("Empty query");
        return;
    }
        allDocInQuery.findFirst();
    while (!allDocInQuery.last()) {
        Document d = getDocGivenId(allDocInQuery.retrieve());
        int rank = getDocRankScore(d, Query);
        insertSortedList(new DocumentRank(allDocInQuery.retrieve(), rank));
        allDocInQuery.findNext();
    }
        Document d = getDocGivenId(allDocInQuery.retrieve());
        int rank = getDocRankScore(d, Query);
        insertSortedList(new DocumentRank(allDocInQuery.retrieve(), rank));
    }
    public static void insertSortedList(DocumentRank dr){
    if(allDocRanked.empty()){
        allDocRanked.insert(dr);
        return;
    }
    allDocRanked.findFirst();
    while (!allDocRanked.last()) {
        if(dr.rank > allDocRanked.retrieve().rank){
            DocumentRank dr1 = allDocRanked.retrieve();
            allDocRanked.update(dr);
            allDocRanked.insert(dr1);
            return;
        }
        else
            allDocRanked.findNext();
        }
        if(dr.rank > allDocRanked.retrieve().rank) {
            DocumentRank dr1 = allDocRanked.retrieve();
            allDocRanked.update(dr);
            allDocRanked.insert(dr1);
            return;
        }
        else
            allDocRanked.insert(dr);

    }
}
