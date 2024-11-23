
import java.util.Scanner;
import java.io.*;
import java.io.File;

public class Text {
    static LinkedList<String> stopWords;
    static index index1;
    InvertedIndex inverted;
    InvertedIndexBST invertedBST;
    int numTokens;
    int numOfUniqueTokens;
    LinkedList<String> uniqueWords=new LinkedList<>();
    public Text() {
        stopWords = new LinkedList();
        index1 = new index();
        inverted = new InvertedIndex();
        invertedBST = new InvertedIndexBST();
    }
    public void Load_stopWords(String fileName) {

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
    public void Load_all_doc(String fileName) {
        String line=null;
        try{ // try block
            File f= new File(fileName); // Creating a file with the path as a parameter
            Scanner s = new Scanner(f); // Reading the file f using scanner
            s.nextLine(); // this skips the first row in Excel file (Header)

            while(s.hasNextLine()) { // while it is not the last line " has next line"
                line = s.nextLine();  // stores the data in the variable line
                if(line.trim().length()<3) { // Skipping the empty 3 lines after document 49 in Excel file.
                    System.out.println("Empty line found, skipping this line "+ line);
                    break;
                }
               // System.out.println(line);

                String x = line.substring(0,line.indexOf(',')); // we take the id's of the docs
                int id = Integer.parseInt(x.trim());
                System.out.println("line= "+line);//id = document# // trim --> cuts the spaces // parseInt --> converting String into int
               String content = line.substring(line.indexOf(',')+1).trim(); // content = content of the document after the ","
                System.out.println("content= "+content);
                LinkedList<String> words_in_doc = make_Linked_List_of_words_in_doc_index_inverted_index(content, id);
                index1.add_Document(new Document(id, words_in_doc, content));
            }
        }
        catch (Exception e){
            System.out.println("This is the end of the file");
        }
    }
    public LinkedList<String> make_Linked_List_of_words_in_doc_index_inverted_index(String content, int id) {
        LinkedList<String> words_in_doc = new LinkedList<String>();
        make_index_and_inverted_index(content, words_in_doc, id);
        return words_in_doc;
    }
    public void make_index_and_inverted_index(String content, LinkedList<String> words_in_doc, int id) {
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
        numTokens+=tokens.length;
        for(String w : tokens) {
            if(!uniqueWords.search(w)) {
                uniqueWords.insert(w);
                //numOfUniqueTokens++;
            }
            if(!existIn_stop_words(w)){
                words_in_doc.insert(w);
                inverted.add(w, id);
                invertedBST.add(w, id);
            }
        }
    }
    public boolean existIn_stop_words(String word) {
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
    public void Load_all_files(String stopFile, String documentsFile){
        Load_stopWords(stopFile);
        Load_all_doc(documentsFile);

    }
   // public void displayStopWords() {
    //    stopWords.display_all_doc_with_score_usingList();
   // }

    public void display_doc_with_given_IDS(LinkedList<Integer>IDs) {
        if(IDs.isEmpty()){
            System.out.println("No documents found");
            return;
        }
        IDs.findFirst();
        while (!IDs.last()){
            Document doc = index1.get_document_givin_id(IDs.retrieve());
            if(doc!=null){
                System.out.println("Document "+ doc.id+": "+doc.content);
            }
            IDs.findNext();
        }
        Document doc = index1.get_document_givin_id(IDs.retrieve());
        if(doc!=null){
            System.out.println("Document "+ doc.id+": "+doc.content);
        }
        System.out.println("");
    }
    public static void display_Menu(){
        System.out.println("1-Retrieve a term ( there are choices"
                +":using index1 with lists"
                +"-inverted index1 with lists"
                + "-inverted index1 with BST.");
        System.out.println("2-Boolean Retrieval.");
        System.out.println("3-Ranked Retrieval.");
        System.out.println("4-Index Documents:print all all_doc.");
        System.out.println("5-number of all_doc in the index1.");
        System.out.println("6-number of unique words in the indexed.");
        System.out.println("7-show inverted index1 with list of lists.");
        System.out.println("8-show inverted index1 with Bst.");
        System.out.println("9-Index Tokens:to show number of vocabulary and tokens in the index1.");
        System.out.println("10-Exit.");

    }//
    public static void Test5withMenu()
    {
        Text d=new Text();
        d.Load_all_files( "stop.txt","dataset.csv");
        Scanner s=new Scanner(System.in);
        int ch=0;
        do{
            display_Menu();
            ch=s.nextInt();
            switch(ch)
            {
                case 1:
                    System.out.println("enter a term to retrieve");
                    String term=s.next();
                    term=term.toLowerCase().trim();
                    System.out.println(":using index with lists");
                    LinkedList<Integer>res=Text.index1.get_all_document_given_terms(term);
                    System.out.print("word:"+term+"[");
                    res.display();
                    System.out.println("]");
                    System.out.println("------------------------");
                    System.out.println("-inverted index with lists");
                    boolean found=d.inverted.search_word_in_inverted(term);
                    if(found)
                        d.inverted.inverted_index.retrieve().display();
                    else
                        System.out.println("not found in inverted index with lists");

                    System.out.println("-inverted index with BST.");
                    boolean found2=d.invertedBST.search_word_in_inverted(term);
                    if(found2)
                        d.inverted.inverted_index.retrieve().display();
                    else
                        System.out.println("not found in inverted index with lists");
                    break;
                case 2:
                    s.nextLine();
                    System.out.println("enter a query to retrieve");
                    String query=s.nextLine();
                    query=query.toLowerCase();
                    query=query.replaceAll(" and "," AND ");
                    query=query.replaceAll(" or "," OR ");
                    System.out.println("\nwhich method you want to make query enter:\n"
                            + "1-for using index \n"
                            + "2-for using inverted index list of lists \n"
                            + "3-for using BST\n");
                    int x=s.nextInt();
                    do{
                        if(x==1){
                            QueryIndex q=new QueryIndex(Text.index1);
                            System.out.println("========"+query+"=======");
                            LinkedList res1= QueryIndex.mixedQuery(query);
                            d.display_doc_with_given_IDS(res1);
                        }
                        else if(x==2){
                            Query q=new Query(d.inverted);
                            System.out.println("========"+query+"=======");
                            LinkedList res1= Query.mixedQuery(query);
                            d.display_doc_with_given_IDS(res1);
                        }
                        else if(x==3){
                            QueryBST q=new QueryBST(d.invertedBST);
                            System.out.println("========"+query+"=======");
                            LinkedList res1= QueryBST.mixedQuery(query);
                            d.display_doc_with_given_IDS(res1);
                        }
                        else if(x==4)
                            break;
                        else
                            System.out.println("wrong query");

                        System.out.println("\nwhich method you want to make query enter:\n"
                                + "1-for using index \n"
                                + "2-for using inverted index list of lists \n"
                                + "3-for using BST\n");
                        x=s.nextInt();
                    }while(x!=4);

                    break;
                case 3:
                    s.nextLine();
                    System.out.println("enter a query to Rank");
                    String query2=s.nextLine();
                    query2=query2.toLowerCase();
                    Ranking R5=new Ranking(d.invertedBST, index1,query2);
                    R5.insert_sorted_in_list();
                    R5.display_all_doc_with_score_usingList();
                    break;
                case 4:
                    d.index1.displayDocuments();
                    System.out.println("---------------");
                    break;
                case 5:
                    System.out.println("num of documents="+Text.index1.all_doc.n);
                    System.out.println("---------------");
                    break;
                case 6:
                    System.out.println("num of unique words without stop words="+d.inverted.inverted_index.n);
                    System.out.println("---------------");
                    break;
                case 7:
                    d.inverted.display_inverted_index();
                    break;
                case 8:
                    d.invertedBST.display_inverted_index();
                    break;
                case 9:
                    System.out.println("num of tokens="+d.numTokens);
                    System.out.println("num of unique words including stop words="+d.uniqueWords.n);
                    break;
                case 10:
                    System.out.println("goodbye");
                    break;
                default:
                    System.out.println("error input try again");
                    break;
            }
        }while(ch!=10);

    }
    public static void main(String[]args)
    {

        Test5withMenu();

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
