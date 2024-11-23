//
class DocumentRanking {
    int id;
    int rank;
    public DocumentRanking(int id , int rank) {
        this.id = id;
        this.rank=rank;
    }
    public void display () {
        System.out.printf("%-8d%-8d\n",id,rank);        
    }
}
//Using sorted list with ordering ids
public class Ranking {
    static String Query; 
    static InvertedIndexBST invertedIndexBST;
    static index index;
    static LinkedList<Integer> queryDocs;
    static LinkedList<DocumentRanking> rankedDocs;
    
    public Ranking (InvertedIndexBST invertedIndexBST, index index, String Query ) {
        this.invertedIndexBST = invertedIndexBST;
        this.index = index;
        this.Query= Query;
        queryDocs=new LinkedList<Integer>();
        rankedDocs=new LinkedList<DocumentRanking>();
    }
    
    public void displayAllDocList() {


        if (rankedDocs.empty()) {
            System.out.println("Empty list, no ranked documents found.");
            return;
        }
        
        System.out.printf("%-8s%-8s\n","DocID ","Score");
        rankedDocs.findFirst();
        while(!rankedDocs.last()){
            rankedDocs.retrieve().display();
            rankedDocs.findNext();
        }
        rankedDocs.retrieve().display();

    }
    
    public static Document getDocGivenID(int id) {
        return index.getAllDocGivenID(id);
    }
    
    //عدد مرات تكرار الكلمة بالملف
    public static int termFrequency(Document document, String term) {
        int frequency = 0;
        LinkedList<String> words= document.words;
        if (words.empty())
            return 0;
        words.findFirst();
        while (!words.last()){
            if (words.retrieve().equalsIgnoreCase(term))
                frequency++;
            words.findNext();
        }
        if (words.retrieve().equalsIgnoreCase(term)) //last wors
                frequency++;
        return frequency;      
    }
    
    public static int getRankScore(Document document, String Query) {
        if (Query.isEmpty())
            return 0;
        String terms[]=Query.split("\\s+");
        int totalFreq=0;
        for(int i =0; i<terms.length; i++) //loops to all words and count word frequency
            totalFreq+= termFrequency(document,terms[i].trim().toLowerCase());
        return totalFreq;
    }
    
    public static void RankQuery(String Query) {
        LinkedList<Integer> docIDs= new LinkedList<Integer>();
        if (Query.isEmpty())
            return;

        String[] terms = Query.split("\\s+"); //if there is more than one space
        boolean found=false;

        for (int i=0; i< terms.length; i++) {
            found = invertedIndexBST.searchWord(terms[i].trim().toLowerCase());
            if (found)
                docIDs = invertedIndexBST.invertedIndexBST.retrieve().docIDS;
            AddingInListSorted(docIDs);

        }
    }

    
    public static void AddingInListSorted (LinkedList<Integer> A) { //Addtpquerydoc
        if (A.empty()) 
            return;

        A.findFirst();
        while (!A.last()) {
            if (!existInResult(queryDocs, A.retrieve()))
                insertInSortedList(A.retrieve());
            
            A.findNext();
        }
        if (!existInResult(queryDocs, A.retrieve()))
            insertInSortedList(A.retrieve());
    }
    
    public static boolean existInResult(LinkedList<Integer> result,Integer id){
        if (result.empty())
            return false;
        result.findFirst();
        while (!result.last()) {
            if (result.retrieve().equals(id))
                return true;
            result.findNext();
        }
        if(result.retrieve().equals(id))
            return true;
        return false;
    }
    
    public static void  insertInSortedList(Integer id) {
        if (queryDocs.empty()){
            queryDocs.insert(id);
            return;
        }
        
        queryDocs.findFirst();
        while(!queryDocs.last()){
            if (id<queryDocs.retrieve()){
                Integer id1 = queryDocs.retrieve();
                queryDocs.update(id);
                queryDocs.insert(id1);
                return;
            }
            else
                queryDocs.findNext();
        }
        if (id<queryDocs.retrieve()){
            Integer id1 = queryDocs.retrieve();
            queryDocs.update(id);
            queryDocs.insert(id1);
            return;
        }
        else
            queryDocs.insert(id);
            
    }
    
    public static void insertSortedList(){
        RankQuery(Query);//Finding queryDocs

        if (queryDocs.empty()){
            System.out.println("No matches for this query.");
            return;
        }
        queryDocs.findFirst();
        while(!queryDocs.last()){
            Document document = getDocGivenID(queryDocs.retrieve());
            int Rank = getRankScore(document, Query);
            insertSortedList(new DocumentRanking(queryDocs.retrieve(),Rank)); //Adding the document in order
            queryDocs.findNext();
        }
            Document document = getDocGivenID(queryDocs.retrieve());
            int Rank = getRankScore(document, Query);
            insertSortedList(new DocumentRanking(queryDocs.retrieve(),Rank));

    } 
    
        public static void insertSortedList(DocumentRanking documentRanked) {
            if (rankedDocs.empty()){
                rankedDocs.insert(documentRanked);
                return;
            }
            rankedDocs.findFirst();
            while(!rankedDocs.last()){
                if (documentRanked.rank>rankedDocs.retrieve().rank) {
                    DocumentRanking documentRanked1 = rankedDocs.retrieve();
                    rankedDocs.update(documentRanked);
                    rankedDocs.insert(documentRanked1);
                    return;
                }
                else
                    rankedDocs.findNext();
            }
            if (documentRanked.rank>rankedDocs.retrieve().rank) {
                DocumentRanking documentRanked1 = rankedDocs.retrieve();
                rankedDocs.update(documentRanked);
                rankedDocs.insert(documentRanked1);
                return;
            }
            else
                rankedDocs.insert(documentRanked);
        }
}
