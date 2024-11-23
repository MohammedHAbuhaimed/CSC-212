public class Query {
    static InvertedIndex invertedIndex;
    
    public Query(InvertedIndex invertedIndex){
    this.invertedIndex = invertedIndex;
    }
    public static LinkedList<Integer> AndQuery(String Q){//     Q=Query
    LinkedList<Integer> listA = new LinkedList<Integer> ();//first list A
    LinkedList<Integer> listB = new LinkedList<Integer> ();//second list B
    String terms[] = Q.split("AND");
    
    if(terms.length == 0 ) 
        return listA;
    boolean found = invertedIndex.searchWord(terms[0].trim().toLowerCase()); // search
    if (found)
        listA = invertedIndex.invertedIndex.retrieve().docIDS;
    for (int i=1 ; i<terms.length ; i++){
        found = invertedIndex.searchWord(terms[i].trim().toLowerCase()); // search
    if (found)
        listB = invertedIndex.invertedIndex.retrieve().docIDS;
    
    listA = AndQuery(listA,listB);
    }
    return listA;
    }
    
   public static LinkedList<Integer> AndQuery(LinkedList<Integer> listA , LinkedList<Integer> listB){
       LinkedList<Integer> result = new LinkedList<Integer>();
       if (listA.empty()|| listB.empty())
           return result;
       listA.findFirst();
       while(true)
       {
            boolean found = existsInResult(result , listA.retrieve());
            if(!found){
                listB.findFirst();
                while(true){
                    if(listB.retrieve().equals(listA.retrieve())){
                        result.insert(listA.retrieve());
                        break;
                    }
                    if(!listB.last())
                        listB.findNext();
                    else 
                        break;
                } //end S loop
            
            }//not found loop
            if(!listA.last())
                listA.findNext();
            else
                break;
       }
       return result;
       }
   
     public static LinkedList<Integer> ORQuery(String Q){//     Q=Query
        LinkedList<Integer> listA = new LinkedList<Integer> ();//first list A
        LinkedList<Integer> S = new LinkedList<Integer> ();//second listB
        String terms[] = Q.split("OR");

        if(terms.length == 0 ) 
            return listA;
        boolean found = invertedIndex.searchWord(terms[0].trim().toLowerCase()); // search
        if (found)
            listA = invertedIndex.invertedIndex.retrieve().docIDS;
        for (int i=1  ; i<terms.length ; i++){
            found = invertedIndex.searchWord(terms[i].trim().toLowerCase()); // search
        if (found)
            S = invertedIndex.invertedIndex.retrieve().docIDS;

        listA = ORQuery(listA , S);
        }
        return listA;
    }
    
   public static LinkedList<Integer> ORQuery(LinkedList<Integer> listA , LinkedList<Integer> listB){
       LinkedList<Integer> result = new LinkedList<Integer>();
       if (listA.empty() && listB.empty())
           return result;
       listA.findFirst();
       while(!listA.empty()){
            boolean found = existsInResult(result , listA.retrieve());
            if(!found){
                result.insert(listA.retrieve());
            }
            if(!listA.last()){
                listA.findNext();
            }
            else
                break;
       }
       listB.findFirst();
       while(!listB.empty())
       {
            boolean found = existsInResult(result , listB.retrieve());
            if(!found){
                result.insert(listB.retrieve());
            }
            if(!listB.last()){
                listB.findNext();
            }
            else
                break;
       }
      return result;
       }
   
    
    public static LinkedList<Integer>MixedQuery(String Query){
        LinkedList<Integer> listA= new LinkedList<Integer>();
        LinkedList<Integer> listB= new LinkedList<Integer>();
        if(Query.length() == 0)
            return listA;
        String ORs[]= Query.split("OR"); //less priorty than AND
        
        listA= AndQuery(ORs[0]);
        for(int i=1; i< ORs.length; i++){
            listB= AndQuery(ORs[i]);
            listA= ORQuery(listA, listB);
        }
        return listA;
}
    
   
   public static boolean existsInResult(LinkedList<Integer> result , Integer id   ){
       if (result.empty()) 
           return false;
       result.findFirst();
       while(!result.last()){
           if (result.retrieve().equals(id))
               return true ;
           result.findNext();
       }
       if (result.retrieve().equals(id))
           return true;

   return false ;
   }
}
