import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Text {
LinkedList<String> stopWords;
static index index;
InvertedIndex invertedIndex;
InvertedIndexBST invertedIndexBST;
int numberOfToken=0;
LinkedList<String> uniqueWords=new LinkedList<>();

    public Text(){
        stopWords=new LinkedList<>();
        index =new index();
        invertedIndex =new InvertedIndex();
        invertedIndexBST = new InvertedIndexBST();
    }

    public void LoadStopWords(String fileName){
        try{
            File file=new File (fileName);
            Scanner Read=new Scanner(file);
            while (Read.hasNextLine()){
                String line=Read.nextLine();
                stopWords.insert(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void LoadAllDoc(String fileName){
        String line=null;
        try{
            File file=new File(fileName);
            Scanner Read=new Scanner(file);

            Read.nextLine(); //to skip first line
            while (Read.hasNextLine()){
                line=Read.nextLine();

                if (line.trim().length()<3){
                    //   System.out.println();
                    break;
                }

                String F =line.substring(0, line.indexOf(',')).trim();
                int id = Integer.parseInt(F);

                String content = line.substring(line.indexOf(',')+1).trim();



                LinkedList<String>wordsInDoc = createListOfWords(content, id) ;
                index.addDocuemnt(new Document (id,wordsInDoc, content));
            }
        }catch(Exception e) {
            System.out.println("End of file");
        }
    }

    public LinkedList<String> createListOfWords(String content, int id){
            LinkedList<String>wordsInDoc =new LinkedList<String>();
            processIndexAndInvertedIndex(content, wordsInDoc, id);
            return wordsInDoc;
    }

    public LinkedList<String > InvertedIndexDoc (String WORDS, int id){
        LinkedList<String>WordsINDoc=new LinkedList<String>();
        InvertedIndex(WORDS, WordsINDoc, id);
        return WordsINDoc;
    }

    public void InvertedIndex(String WORDS, LinkedList<String>WordsINDoc, int id){
        WORDS = WORDS.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] tokens = WORDS.split("\\s+");
        for (String w: tokens) {
            if (!IsStopWord(w)){
                WordsINDoc.insert(w);
                invertedIndex.addWord(w , id);
                invertedIndexBST.addWord(w, id);
            }
        }
    }

    public boolean IsStopWord (String word) {
        if (stopWords==null||stopWords.empty())
            return false;
        stopWords.findFirst();

        while(!stopWords.last()){
            if(stopWords.retrieve().equals(word))
                return true;
            stopWords.findNext();
        }
        if(stopWords.retrieve().equals(word))
            return true;

        return false;
    }

    public void LoadFiles(String stopWordsFile , String doucment){
        LoadStopWords(stopWordsFile);
        LoadAllDoc(doucment);

    }

    public void displayStopWords(){
        stopWords.display ();
    }

    public void displayDocById(LinkedList<Integer> IDs) {
        if (IDs.empty()){
            System.err.print("IDs list is empty, document doesn't exist.");
            return;
        }
        IDs.findFirst();
        System.out.print("Result: {");
        while (!IDs.last()){
            index.getAllDocGivenID(IDs.retrieve());
            System.out.print(",");
            IDs.findNext();
        }
        index.getAllDocGivenID(IDs.retrieve());
        System.out.println("}");
    }

    public void processIndexAndInvertedIndex(String content, LinkedList<String>wordsInDoc, int id){
        while (content.contains("-")) {
            if (content.charAt(content.indexOf("-")-2)==' ')
                content=content.replaceFirst("-", "");
            else
                content = content.replaceFirst("-", " ");
        }
        //to count unique words
        content =content.replaceAll("\'", "");
        content =content.replaceAll("-", "");
        content= content.replaceAll("'", "");
        content =content.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
        String[] tokens = content.split("\\s+");
        numberOfToken+= tokens.length;
        for (String w: tokens){
            if (!uniqueWords.search(w))
                uniqueWords.insert(w);

            if (!existsInStopWords(w)){
                wordsInDoc.insert(w);
                invertedIndex.addWord(w, id);
                invertedIndexBST.addWord(w, id);
            }
        }
    }

    public boolean existsInStopWords(String word) {
        if (stopWords==null||stopWords.empty())
            return false;

        stopWords.findFirst();
        while (!stopWords.last()){
            if (stopWords.retrieve().equals(word))
                return true;

            stopWords.findNext();
        }

        if (stopWords.retrieve().equals(word))
            return true;
        return false;
    }

    public static void displayMenu(){
        System.out.println("============================ Welcome to the Simple Search Engine System! ======================================================== ");
        System.out.println("1-Retrieve a term (choose a method)\n"
                +"-Using index (lists) "
                +"-Using inverted index (lists) "
                +"-Using inverted index with (BST) ");
        System.out.println("2- Boolean Retrieval");
        System.out.println("3- Ranked Retrieval");
        System.out.println("4- Index Documents: Print all documents without stop words.");
        System.out.println("5- Number of documents in the index.");
        System.out.println("6- Number of unique words in the indexed.");
        System.out.println("7- Show inverted index with list. ");
        System.out.println("8- Show inverted index with BST. ");
        System.out.println("9- Index Tokens: to show number of vocabulary and tokens in the index.");
        System.out.println("0- Exit.");

    }
    public static void Test(){
        Text text = new Text();
        text.LoadFiles( "stop.txt","dataset.csv");
        Scanner read =new Scanner(System.in);
        int choice=0;
        do{
            displayMenu();
            choice=read.nextInt();
            switch(choice){
                case 1:
                    System.out.println("Enter a term to retrieve");
                    String term=read.next();
                    term=term.toLowerCase().trim();
                    System.out.println("word: "+term);
                    LinkedList<Integer>res= Text.index.getAllDocGivenTerm(term);
                    System.out.print("Document IDs: "+"[");
                    res.display();
                    System.out.println("]");
                    System.out.println("----------------------------------------");
                    System.out.println("Inverted index with lists");
                    boolean found= text.invertedIndex.searchWord(term);
                    if(found)
                        text.invertedIndex.invertedIndex.retrieve().display();
                    else
                        System.out.println("Not found in inverted index ( List ).");

                    System.out.println("Inverted index with BST.");
                    boolean found2= text.invertedIndexBST.searchWord(term);
                    if(found2)
                        text.invertedIndex.invertedIndex.retrieve().display();
                    else
                        System.out.println("Not found in inverted index.");
                    break;
                case 2:
                    read.nextLine();
                    System.out.println("Enter a query to retrieve: ");
                    String query=read.nextLine();
                    query=query.toLowerCase();
                    query=query.replaceAll(" and "," AND ");
                    query=query.replaceAll(" or "," OR ");
                    System.out.println("\nWhich method you want to make a query: \n"
                            + "1- index \n"
                            + "2-inverted index  \n"
                            + "3- BST\n"
                            + "4- Exit \n");
                    int opinion=read.nextInt();
                    do{
                        if(opinion==1){ // Query from index
                            QueryIndex q=new QueryIndex(Text.index);
                            System.out.println("===================="+query+"====================");
                            LinkedList res1= QueryIndex.MixedQuery(query);
                            text.displayDocById(res1);
                        }
                        else if(opinion==2){ // Normal Query
                            Query q=new Query(text.invertedIndex);
                            System.out.println("===================="+query+"====================");
                            LinkedList res1= Query.MixedQuery(query);
                            text.displayDocById(res1);
                        }
                        else if(opinion==3){ // QueryProcessingBST
                            QueryBST q=new QueryBST(text.invertedIndexBST);
                            System.out.println("===================="+query+"====================");
                            LinkedList res1= QueryBST.MixedQuery(query);
                            text.displayDocById(res1);
                        }
                        else if(opinion==4)
                            break;
                        else
                            System.out.println("invalid query.");

                        System.out.println("\nwhich method you want to make a query? \n"
                                + "1- index \n"
                                + "2- inverted index \n"
                                + "3- BST\n"+
                                "4- Exit");
                        opinion=read.nextInt();
                    }while(opinion!=4);

                    break;
                case 3:
                    read.nextLine();
                    System.out.println("Enter a query to Rank");
                    String query2=read.nextLine();
                    query2=query2.toLowerCase();
                    Ranking ranking=new Ranking(text.invertedIndexBST, index,query2);
                    ranking.insertSortedList();
                    ranking.displayAllDocList();
                    break;
                case 4:
                    text.index.displayDocuments();
                    System.out.println("----------------------------------------------------");
                    break;
                case 5:
                    System.out.println("Number of documents: "+ Text.index.allDocuemnts.count);
                    System.out.println("----------------------------------------------------");
                    break;
                case 6:
                    System.out.println("Number of unique words without stop words: "+ text.invertedIndex.invertedIndex.count);
                    System.out.println("----------------------------------------------------");
                    break;
                case 7:
                    text.invertedIndex.displayInvertedIndex();
                    break;
                case 8:
                    text.invertedIndexBST.displayInvertedIndexBST();
                    break;
                case 9:
                    System.out.println("Number of tokens: "+ text.numberOfToken);
                    System.out.println("Number of unique words including stop words: "+ text.uniqueWords.count);
                    break;
                case 0:
                    System.out.println("==============================Thank you for using our search engine!==============================");
                    break;
                default:
                    System.out.println("Error input number, please try again.");
                    break;
            }
        }while(choice!=0);
    }

    public static void main(String [] args) {
        try {
            Test();
        }catch(Exception e) {
            System.out.println("Please enter correct input");
            Test();
        }
    }

}