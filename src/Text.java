
import java.util.Scanner;
import java.io.*;
import java.io.File;

public class Text {
    static LinkedList<String> stopWords;
    static index index;
    InvertedIndex invertedIndex;
    InvertedIndexBST invertedIndexBST;
    int numOfTokens;
  //  int numOfUniqueTokens;
    LinkedList<String> uniqueWords=new LinkedList<>();
    public Text() {
        stopWords = new LinkedList();
        index = new index();
        invertedIndex = new InvertedIndex();
        invertedIndexBST = new InvertedIndexBST();
    }
    public void loadStopWords(String fileName) {

        try{ // try block
            File f= new File(fileName); // Creating a file with the path as a parameter
            Scanner s = new Scanner(f); // Reading the file f using scanner


            while(s.hasNextLine()) { // while it is not the last line " has next line"
                String line = s.nextLine();  // stores the data in the variable line
                stopWords.insert(line);

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadAllDoc(String fileName) {
        String line=null;
        try{ // try block
            File f= new File(fileName); // Creating a file with the path as a parameter
            Scanner s = new Scanner(f); // Reading the file f using scanner
            s.nextLine(); // this skips the first row in Excel file (Header)

            while(s.hasNextLine()) { // while it is not the last line " has next line"
                line = s.nextLine();  // stores the data in the variable line
                if(line.trim().length()<3) { // Skipping the empty 3 lines after document 49 in Excel file.
                    //System.out.println("Empty line found, skipping this line "+ line);
                    break;
                }
               // System.out.println(line);

                String x = line.substring(0,line.indexOf(',')); // we take the id's of the docs
                int id = Integer.parseInt(x.trim());
               // System.out.println("line: "+line);//id = document# // trim --> cuts the spaces // parseInt --> converting String into int
               String content = line.substring(line.indexOf(',')+1).trim(); // content = content of the document after the ","
                //System.out.println("content: "+content);
                LinkedList<String> words_in_doc = createListOfWords(content, id);
                index.addDocument(new Document(id, words_in_doc, content));
            }
        }
        catch (Exception e){
            System.out.println("This is the end of the file");
        }
    }
    public LinkedList<String> createListOfWords(String content, int id) {
        LinkedList<String> words_in_doc = new LinkedList<String>();
        processIndexAndInvertedIndex(content, words_in_doc, id);
        return words_in_doc;
    }
    public void processIndexAndInvertedIndex(String content, LinkedList<String> words_in_doc, int id) {
       //e-sports
        while(content.contains("-")){
            if(content.charAt(content.indexOf("-")-2)==' '){
                content=content.replaceFirst("-", "");}
            else
            content=content.replaceFirst("-", " ");

        }
//        content=content.replaceAll("\'"," ");
//        content=content.replaceAll("-"," ");
       content=content.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
        String[] tokens = content.split("\\s+");
        numOfTokens +=tokens.length;
        for(String w : tokens) {
            if(!uniqueWords.search(w)) {
                uniqueWords.insert(w);
                //numOfUniqueTokens++;
            }
            if(!existInStopWords(w)){
                words_in_doc.insert(w);
                invertedIndex.add(w, id);
                invertedIndexBST.add(w, id);
            }
        }
    }
    public boolean existInStopWords(String word) {
        if(stopWords==null || stopWords.empty())
            return false;
        stopWords.findFirst();
        while (!stopWords.last()){
            if(stopWords.retrieve().equals(word))
                return true;

            stopWords.findNext();
        }
        if(stopWords.retrieve().equals(word))
            return true;

        return false;
    }
    public void loadAllFiles(String stopFile, String documentsFile){
        loadStopWords(stopFile);
        loadAllDoc(documentsFile);

    }
   // public void displayStopWords() {
    //    stopWords.display_all_doc_with_score_usingList();
   // }

    public void displayDocWithGivenIDS(LinkedList<Integer>IDs) {
        if(IDs.isEmpty()){
            System.out.println("No documents found");
            return;
        }
        IDs.findFirst();
        while (!IDs.last()){
            Document doc = index.getDocumentGivinId(IDs.retrieve());
            if(doc!=null){
                System.out.println("Document "+ doc.id+": "+doc.content);
            }
            IDs.findNext();
        }
        Document doc = index.getDocumentGivinId(IDs.retrieve());
        if(doc!=null){
            System.out.println("Document "+ doc.id+": "+doc.content);
        }
        System.out.println("");
    }
    public static void displayMenu(){
        System.out.println("============================ Welcome to the Simple Search Engine System! ============================ ");
        System.out.println("1-Retrieve a term (choose a method)\n"
                +"-Using index (lists) "
                +"-Using inverted index (lists) "
                +"-Using inverted index with (BST) ");
        System.out.println("2- Boolean Retrieval");
        System.out.println("3- Ranked Retrieval");
        System.out.println("4- Index Documents: Print all documents.");
        System.out.println("5- Number of documents in the index.");
        System.out.println("6- Number of unique words in the indexed.");
        System.out.println("7- Show inverted index with list. ");
        System.out.println("8- Show inverted index with BST. ");
        System.out.println("9- Index Tokens: to show number of vocabulary and tokens in the index.");
        System.out.println("0- Exit.");

    }//
    public static void Test()
    {
        Text d=new Text();
        d.loadAllFiles( "stop.txt","dataset.csv");
        Scanner s=new Scanner(System.in);
        int choice =0;
        do{
            displayMenu();
            choice=s.nextInt();
            switch(choice)
            {
                case 1:
                    System.out.println("Enter a term to retrieve");
                    String term=s.next();
                    term=term.toLowerCase().trim();
                    System.out.println("Using index ");
                    LinkedList<Integer>res=Text.index.getAllDocumentGivenTerms(term);
                    System.out.print("word: "+term+" [");
                    res.display();
                    System.out.println("]");
                    System.out.println("----------------------------------------");
                    System.out.println("-inverted index with lists");
                    boolean found=d.invertedIndex.searchWordInInvertedIndex(term);
                    if(found)
                        d.invertedIndex.invertedIndex.retrieve().display();
                    else
                        System.out.println("Not found in inverted index ");

                    System.out.println("inverted index with BST.");
                    boolean found2=d.invertedIndexBST.searchWordInInverted(term);
                    if(found2)
                        d.invertedIndex.invertedIndex.retrieve().display();
                    else
                        System.out.println("Not found in inverted index ");
                    break;
                case 2:
                    s.nextLine();
                    System.out.println("Enter a query to retrieve: ");
                    String query=s.nextLine();
                    query=query.toLowerCase();
                    query=query.replaceAll(" and "," AND ");
                    query=query.replaceAll(" or "," OR ");
                    System.out.println("\nWhich method you want to make a query: \n"
                            + "1- index \n"
                            + "2-inverted index  \n"
                            + "3- BST\n");
                    int x=s.nextInt();
                    do{
                        if(x==1){
                            QueryIndex q=new QueryIndex(Text.index);
                            System.out.println("===================="+query+"====================");
                            LinkedList res1= QueryIndex.mixedQuery(query);
                            d.displayDocWithGivenIDS(res1);
                        }
                        else if(x==2){
                            Query q=new Query(d.invertedIndex);
                            System.out.println("===================="+query+"====================");
                            LinkedList res1= Query.mixedQuery(query);
                            d.displayDocWithGivenIDS(res1);
                        }
                        else if(x==3){
                            QueryBST q=new QueryBST(d.invertedIndexBST);
                            System.out.println("===================="+query+"====================");
                            LinkedList res1= QueryBST.mixedQuery(query);
                            d.displayDocWithGivenIDS(res1);
                        }
                        else if(x==4)
                            break;
                        else
                            System.out.println("Wrong query");

                        System.out.println("\nwhich method you want to make a query? \n"
                                + "1- index \n"
                                + "2- inverted index \n"
                                + "3- BST\n"+
                                "4- Exit");
                        x=s.nextInt();
                    }while(x!=4);

                    break;
                case 3:
                    s.nextLine();
                    System.out.println("Enter a query to Rank: ");
                    String query2=s.nextLine();
                    query2=query2.toLowerCase();
                    Ranking R5=new Ranking(d.invertedIndexBST, index,query2);
                    R5.insertSortedInList();
                    R5.displayAllDocWithScoreUsingList();
                    break;
                case 4:
                    d.index.displayDocuments();
                    System.out.println("----------------------------------------------------");
                    break;
                case 5:
                    System.out.println("Number of documents: "+Text.index.all_doc.n);
                    System.out.println("----------------------------------------------------");
                    break;
                case 6:
                    System.out.println("Number of unique words without stop words: "+d.invertedIndex.invertedIndex.n);
                    System.out.println("----------------------------------------------------");
                    break;
                case 7:
                    d.invertedIndex.displayInvertedIndex();
                    break;
                case 8:
                    d.invertedIndexBST.display_inverted_index();
                    break;
                case 9:
                    System.out.println("Number of tokens: "+d.numOfTokens);
                    System.out.println("Number of unique words including stop words: "+d.uniqueWords.n);
                    break;
                case 0:
                    System.out.println("Thanks! See you later.");
                    break;
                default:
                    System.out.println("Incorrect input, Please try again.");
                    break;
            }
        }while(choice !=0);

    }
    public static void main(String[]args)
    {
        try {
            Test();
        }catch(Exception e) {
            System.out.println("Please enter correct input");
            Test();
    }
    }
   /* public static void main(String[] args){
        Text t = new Text();
        t.Load_all_files("stop.txt", "dataset.csv");
        t.index1.displayDocuments();
        System.out.println("\n==========================================");
        t.inverted.display_inverted_index();
        System.out.println("num of tokens: "+ t.numTokens);
        System.out.println("num of unique: "+ t.uniqueWords.n);
        Query q = new Query(t.inverted);
        LinkedList res = Query.andQuery("colorANDpole");
        t.displayDocumentIDs(res);
        System.out.println("--------------------OR--------------------------");
        LinkedList res2 = Query.andQuery("Arabia OR pole ORcolor");
        t.displayDocumentIDs(res2);
        System.out.println("=================Ranking market sports using List========================");
        Ranking R1 = new Ranking(new InvertedIndexBST(), index1, "market sports");
        R1.insertSortedInList();
        R1.display_inverted_index();
        System.out.println("\n==========================");
        Query q2 = new Query(t.inverted);
        System.out.println("=======================market AND sports=======================");
        //LinkedList res3 = Query.booleanQuery("market AND sports");
      //  t.displayDocumentIDs(res3);
        stopWords.display_inverted_index();



    }*/


}
