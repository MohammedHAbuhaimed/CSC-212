import java.util.Scanner;
import java.io.*;

public class Text {
    LinkedList<String> stopWords;
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
                    System.out.println("End of line");
                    break;
                }


                String x = line.substring(0,line.indexOf(',')); // we take the id's of the docs
                int id = Integer.parseInt(x.trim());  //id = document# // trim --> cuts the spaces // parseInt --> converting String into int
                String content = line.substring(line.indexOf(',')+1).trim(); // content = content of the document after the ","
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
        content=content.replaceAll("\'"," ");
        content=content.replaceAll("-"," ");
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
            Document doc = index.displayDocumentsWithGivenIDs(IDs.retrieve());
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

}
