import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Text {
LinkedList<String> stopWords;
static index index1;
InvertedIndex inverted;
InvertedIndexBST invertedBST;
int numberOfToken=0;
LinkedList<String> uniqueWords=new LinkedList<>();

    public Text(){
        stopWords=new LinkedList<>();
        index1=new index();
        inverted=new InvertedIndex();
        invertedBST= new InvertedIndexBST();
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
                    System.out.println();
                    break;
                }

                String F =line.substring(0, line.indexOf(',')).trim();
                int id = Integer.parseInt(F);
              System.out.println("Docuemnt ID: "+ line);
                String content = line.substring(line.indexOf(',')+1).trim();



                LinkedList<String>WordsINDoc =makeLinkedListOfWords(content, id) ;
                index1.addDocuemnt(new Document (id,WordsINDoc, content));
            }
        }catch(Exception e) {
            System.out.println("End of file");
        }
    }

    public LinkedList<String> makeLinkedListOfWords(String content, int id){
            LinkedList<String>words_in_doc =new LinkedList<String>();
            makeIndexAndInvertedIndex(content, words_in_doc, id);
            return words_in_doc;
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
                inverted.addWord(w , id);
                invertedBST.addWord(w, id);
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
            index1.getAllDocGivenID(IDs.retrieve());
            System.out.print(",");
            IDs.findNext();
        }
        index1.getAllDocGivenID(IDs.retrieve());
        System.out.println("}");
    }

    public void makeIndexAndInvertedIndex(String content, LinkedList<String>words_in_doc, int id){
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
                words_in_doc.insert(w);
                inverted.addWord(w, id);
                invertedBST.addWord(w, id);
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

    }
    public static void main(String args[]){
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
                    System.out.println("word:"+term);
                    LinkedList<Integer>res= Text.index1.getAllDocGivenTerm(term);
                    System.out.print("Document IDs: "+"[");
                    res.display();
                    System.out.println("]");
                    System.out.println("-inverted index with lists");
                    boolean found= text.inverted.searchWord(term);
                    if(found)
                        text.inverted.inverted_index.retrieve().display();
                    else
                        System.out.println("Not found in inverted index with lists.");

                    System.out.println("-inverted index with BST.");
                    boolean found2= text.invertedBST.searchWord(term);
                    if(found2)
                        text.inverted.inverted_index.retrieve().display();
                    else
                        System.out.println("Not found in inverted index with lists.");
                    break;
                case 2:
                    read.nextLine();
                    System.out.println("Enter a query to retrieve");
                    String query=read.nextLine();
                    query=query.toLowerCase();
                    query=query.replaceAll(" and "," AND ");
                    query=query.replaceAll(" or "," OR ");
                    System.out.println("""
                                       Please choose the query method:
                                            1-Using index. 
                                            2-Using inverted index list of lists. 
                                            3-Using BST.
                                            4-exit.
                                       """);
                    int opinion=read.nextInt();
                    do{
                    if(opinion==1){ // Query from index
                      QueryIndex q=new QueryIndex(Text.index1);
                       System.out.println("========"+query+"=======");
                    LinkedList res1= QueryIndex.MixedQuery(query);
                    text.displayDocById(res1);
                    }
                    else if(opinion==2){ // Normal Query
                    Query q=new Query(text.inverted);
                     System.out.println("========"+query+"=======");
                    LinkedList res1= Query.MixedQuery(query);
                    text.displayDocById(res1);
                    }
                    else if(opinion==3){ // QueryProcessingBST
                      QueryBST q=new QueryBST(text.invertedBST);
                      System.out.println("========"+query+"=======");
                      LinkedList res1= QueryBST.MixedQuery(query);
                      text.displayDocById(res1);
                    }
                    else if(opinion==4)
                        break;
                    else
                        System.out.println("invalid query.");

                    System.out.println("""  
                                       Please choose the query method:
                                            1-Using index. 
                                            2-Using inverted index list of lists. 
                                            3-Using BST.
                                            4-exit.
                                       """);
                    opinion=read.nextInt();
                    }while(opinion!=4);

                    break;
                case 3:
                    read.nextLine();
                    System.out.println("Enter a query to Rank");
                    String query2=read.nextLine();
                    query2=query2.toLowerCase();
                    Ranking ranking=new Ranking(text.invertedBST, index1,query2);
                    ranking.insertSortedList();
                    ranking.displayAllDocList();
                    break;
                case 4:
                    text.index1.displayDocuments();
                    System.out.println("=================================");
                    break;
                case 5:
                    System.out.println("Number of documents="+ Text.index1.allDocuemnts.count);
                    System.out.println("=================================");
                    break;
                case 6:
                    System.out.println("Number of unique words without stop words="+ text.inverted.inverted_index.count);
                    System.out.println("=================================");
                    break;
                case 7:
                    text.inverted.displayInvertedIndex();
                    break;
                case 8:
                    text.invertedBST.displayInvertedIndexBST();
                    break;
                case 9:
                 System.out.println("Number of tokens="+ text.numberOfToken);
                 System.out.println("Number of unique words including stop words="+ text.uniqueWords.count);
                    break;
                case 10:
                    System.out.println("==========Thank you for using our search engine!==========");
                    break;
                default:
                    System.out.println("Error input number, please try again.");
                    break;
                }
            }while(choice!=10);
    }

}