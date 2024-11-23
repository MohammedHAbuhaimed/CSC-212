import java.util.Scanner;

public class Main {
//    public static void display_Menu(){
//        System.out.println("1-Retrieve a term ( there are choices"
//                +":using index1 with lists"
//                +"-inverted index1 with lists"
//                + "-inverted index1 with BST.");
//        System.out.println("2-Boolean Retrieval.");
//        System.out.println("3-Ranked Retrieval.");
//        System.out.println("4-Index Documents:print all all_doc.");
//        System.out.println("5-number of all_doc in the index1.");
//        System.out.println("6-number of unique words in the indexed.");
//        System.out.println("7-show inverted index1 with list of lists.");
//        System.out.println("8-show inverted index1 with Bst.");
//        System.out.println("9-Index Tokens:to show number of vocabulary and tokens in the index1.");
//        System.out.println("10-Exit.");
//
//    }//
//    public static void Test5withMenu()
//    {
//        Text d=new Text();
//        d.Load_all_files( "stop.txt","dataset.csv");
//        Scanner s=new Scanner(System.in);
//        int ch=0;
//        do{
//            display_Menu();
//            ch=s.nextInt();
//            switch(ch)
//            {
//                case 1:
//                    System.out.println("enter a term to retrieve");
//                    String term=s.next();
//                    term=term.toLowerCase().trim();
//                    System.out.println(":using index with lists");
//                    LinkedList<Integer>res=Text.index1.get_all_document_given_terms(term);
//                    System.out.print("word:"+term+"[");
//                    res.display();
//                    System.out.println("]");
//                    System.out.println("------------------------");
//                    System.out.println("-inverted index with lists");
//                    boolean found=d.inverted.search_word_in_inverted(term);
//                    if(found)
//                        d.inverted.inverted_index.retrieve().display();
//                    else
//                        System.out.println("not found in inverted index with lists");
//
//                    System.out.println("-inverted index with BST.");
//                    boolean found2=d.invertedBST.search_word_in_inverted(term);
//                    if(found2)
//                        d.inverted.inverted_index.retrieve().display();
//                    else
//                        System.out.println("not found in inverted index with lists");
//                    break;
//                case 2:
//                    s.nextLine();
//                    System.out.println("enter a query to retrieve");
//                    String query=s.nextLine();
//                    query=query.toLowerCase();
//                    query=query.replaceAll(" and "," AND ");
//                    query=query.replaceAll(" or "," OR ");
//                    System.out.println("\nwhich method you want to make query enter:\n"
//                            + "1-for using index \n"
//                            + "2-for using inverted index list of lists \n"
//                            + "3-for using BST\n");
//                    int x=s.nextInt();
//                    do{
//                        if(x==1){
//                            QueryIndex q=new QueryIndex(Text.index1);
//                            System.out.println("========"+query+"=======");
//                            LinkedList res1= QueryIndex.mixedQuery(query);
//                            d.display_doc_with_given_IDS(res1);
//                        }
//                        else if(x==2){
//                            Query q=new Query(d.inverted);
//                            System.out.println("========"+query+"=======");
//                            LinkedList res1= Query.mixedQuery(query);
//                            d.display_doc_with_given_IDS(res1);
//                        }
//                        else if(x==3){
//                            QueryBST q=new QueryBST(d.invertedBST);
//                            System.out.println("========"+query+"=======");
//                            LinkedList res1= QueryBST.mixedQuery(query);
//                            d.display_doc_with_given_IDS(res1);
//                        }
//                        else if(x==4)
//                            break;
//                        else
//                            System.out.println("wrong query");
//
//                        System.out.println("\nwhich method you want to make query enter:\n"
//                                + "1-for using index \n"
//                                + "2-for using inverted index list of lists \n"
//                                + "3-for using BST\n");
//                        x=s.nextInt();
//                    }while(x!=4);
//
//                    break;
//                case 3:
//                    s.nextLine();
//                    System.out.println("enter a query to Rank");
//                    String query2=s.nextLine();
//                    query2=query2.toLowerCase();
//                    Ranking R5=new Ranking(d.invertedBST, index1,query2);
//                    R5.insert_sorted_in_list();
//                    R5.display_all_doc_with_score_usingList();
//                    break;
//                case 4:
//                    d.index1.displayDocuments();
//                    System.out.println("---------------");
//                    break;
//                case 5:
//                    System.out.println("num of documents="+Text.index1.all_doc.n);
//                    System.out.println("---------------");
//                    break;
//                case 6:
//                    System.out.println("num of unique words without stop words="+d.inverted.inverted_index.n);
//                    System.out.println("---------------");
//                    break;
//                case 7:
//                    d.inverted.display_inverted_index();
//                    break;
//                case 8:
//                    d.invertedBST.display_inverted_index();
//                    break;
//                case 9:
//                    System.out.println("num of tokens="+d.numTokens);
//                    System.out.println("num of unique words including stop words="+d.uniqueWords.n);
//                    break;
//                case 10:
//                    System.out.println("goodbye");
//                    break;
//                default:
//                    System.out.println("error input try again");
//                    break;
//            }
//        }while(ch!=10);
//
//    }
//    public static void main(String[]args)
//    {
//
//        Test5withMenu();
//
//    }
//    public void displayStopWords(){
//        stopWords.display_inverted_index();
//    }


    }//end of class
