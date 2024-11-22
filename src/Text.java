
import java.util.Scanner;
import java.io.*;
import java.io.File;

public class Text {
    static LinkedList<String> stopWords;
    static Index index;
    InvertedIndex invertedIndex;
    InvertedIndex invertedIndexBST;
    int numTokens;
    int numOfUniqueTokens;
    LinkedList<String> uniqueWords=new LinkedList<>();
    public Text() {
        stopWords = new LinkedList();
        index = new Index();
        invertedIndex = new InvertedIndex();
        invertedIndexBST = new InvertedIndex();
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
    public void loadDocuments(String fileName) {
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
                System.out.println(line);

                String x = line.substring(0,line.indexOf(',')); // we take the id's of the docs
                int id = Integer.parseInt(x.trim());
               // System.out.println("line= "+line);//id = document# // trim --> cuts the spaces // parseInt --> converting String into int
               String content = line.substring(line.indexOf(',')+1).trim(); // content = content of the document after the ","
               // System.out.println("content= "+content);
                LinkedList<String> docWords = createInvertedIndexList(content, id);
                index.addDocument(new Document(id, docWords, content));
            }
        }
        catch (Exception e){
            System.out.println("This is the end of the file");
        }
    }
    public LinkedList<String> createInvertedIndexList(String content, int id) {
        LinkedList<String> docWords = new LinkedList<>();
        processContentForIndexing(content, docWords, id);
        return docWords;
    }
    public void processContentForIndexing(String content, LinkedList<String> docWords, int id) {
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
        for(int i=0 ; i<tokens.length ; i++) {
            if(!uniqueWords.search(tokens[i])) {
                uniqueWords.insert(tokens[i]);
                numOfUniqueTokens++;
            }
            if(!existStopWords(tokens[i])){
                docWords.insert(tokens[i]);
                invertedIndex.add(tokens[i], id);
                invertedIndexBST.add(tokens[i], id);
            }
        }
    }
    public boolean existStopWords(String word) {
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
        loadDocuments(documentsFile);

    }
    public void displayStopWords() {
        stopWords.display();
    }

    public void displayDocumentIDs(LinkedList<Integer>IDs) {
        if(IDs.isEmpty()){
            System.out.println("No documents found");
            return;
        }
        IDs.findFirst();
        while (!IDs.last()){
            Document doc = index.getDocumentID(IDs.retrieve());
            if(doc!=null){
                System.out.println("Document "+ doc.id+": "+doc.content);
            }
            IDs.findNext();
        }
        Document doc = index.displayDocumentsWithGivenIDs(IDs.retrieve());
        if(doc!=null){
            System.out.println("Document "+ doc.id+": "+doc.content);
        }
        System.out.println("");
    }
   /* public static void main(String[] args){
        Text t = new Text();
        t.loadAllFiles("stop.txt", "dataset.csv");
        t.index.displayDocuments();
        System.out.println("\n==========================================");
        t.invertedIndex.display();
        System.out.println("num of tokens: "+ t.numTokens);
        System.out.println("num of unique: "+ t.uniqueWords.n);
        Query q = new Query(t.invertedIndex);
        LinkedList res = Query.andQuery("colorANDpole");
        t.displayDocumentIDs(res);
        System.out.println("--------------------OR--------------------------");
        LinkedList res2 = Query.andQuery("Arabia OR pole ORcolor");
        t.displayDocumentIDs(res2);
        System.out.println("=================Ranking market sports using List========================");
        Ranking R1 = new Ranking(new InvertedIndexBST(), index, "market sports");
        R1.insertSortedInList();
        R1.display();
        System.out.println("\n==========================");
        Query q2 = new Query(t.invertedIndex);
        System.out.println("=======================market AND sports=======================");
        //LinkedList res3 = Query.booleanQuery("market AND sports");
      //  t.displayDocumentIDs(res3);
        stopWords.display();



    }*/


}
