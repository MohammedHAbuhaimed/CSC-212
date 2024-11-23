public class Query {
    public static InvertedIndex inverted;
    public Query(InvertedIndex inverted) {
        this.inverted = inverted;
    }
    public static LinkedList<Integer> booleanQuery(String query) {
        if(!query.contains("AND") && !query.contains("OR")) {
            return andQuery(query);
        } else if (query.contains("AND") && !query.contains("OR")) {
            return andQuery(query);
        } else if (query.contains("OR") && !query.contains("AND")) {
            return orQuery(query);

        }
        else
            return mixedQuery(query);
    }
    public static LinkedList<Integer> mixedQuery(String query) {
        LinkedList<Integer> A = new LinkedList<Integer>();
        LinkedList<Integer> B = new LinkedList<Integer>();
        if(query.length()==0)
            return A;
        //
        String ORs [] = query.split("OR");
        A=andQuery(ORs[0]);
        for(int i=1;i<ORs.length;i++){
            B=andQuery(ORs[i]);
            A=orQuery(A,B);
        }
        return A;
    }

    public static LinkedList<Integer> andQuery(String Query){
        LinkedList<Integer> A = new LinkedList<Integer>();
        LinkedList<Integer> B = new LinkedList<Integer>();
        String terms[] = Query.split("AND");
        if(terms.length == 0){
            return A;
        }
        boolean found = inverted.search_word_in_inverted(terms[0].trim().toLowerCase());
        if(found){
            A=inverted.inverted_index.retrieve().doc_IDS;
        }
        for(int i = 1; i< terms.length; i++){
            found = inverted.search_word_in_inverted(terms[i].trim().toLowerCase());
            if(found){
                B=inverted.inverted_index.retrieve().doc_IDS;
            }
            A=andQuery(A,B);//
        }
        return A;

    }
    public static LinkedList<Integer> andQuery(LinkedList<Integer> A, LinkedList<Integer> B){
        LinkedList<Integer> result = new LinkedList<Integer>();
        if(A.empty() || B.empty()){
            return result;
        }
        A.findFirst();
        while(true){
            boolean found = existIn_result(result, A.retrieve());
            if(!found){
                B.findFirst();
                while(true){
                    if(B.retrieve().equals(A.retrieve())){
                        result.insert(A.retrieve());
                        break;
                    }
                    if(!B.last())
                        B.findNext();
                    else
                        break;
                } // end of inner loop
            } // end of if not found
            if(!A.last())
                A.findNext();
            else
                break;
        }// end of outer loop
        return result;
    }
    public static LinkedList<Integer> orQuery(String Query){
        LinkedList<Integer> A = new LinkedList<Integer>();
        LinkedList<Integer> B = new LinkedList<Integer>();
        String terms[] = Query.split("OR");
        if(terms.length == 0){
            return A;
        }
        boolean found = inverted.search_word_in_inverted(terms[0].trim().toLowerCase());
        if(found){
            A=inverted.inverted_index.retrieve().doc_IDS;
        }
        for(int i = 1; i< terms.length; i++){
            found = inverted.search_word_in_inverted(terms[i].trim().toLowerCase());
            if(found){
                B=inverted.inverted_index.retrieve().doc_IDS;
            }
            A=orQuery(A,B);
        }
        return A;
    }
    public static LinkedList<Integer> orQuery(LinkedList<Integer> A, LinkedList<Integer> B){
        LinkedList<Integer> result = new LinkedList<Integer>();
        if(A.empty() && B.empty()){
            return result;
        }
        A.findFirst();
        while(!A.empty()){
            boolean found = existIn_result(result, A.retrieve());
            if(!found){
                result.insert(A.retrieve());
            }
            if(!A.last())
                A.findNext();
            else
                break;

        }
        B.findFirst();
        while(!B.empty()){
            boolean found = existIn_result (result, B.retrieve());
            if(!found){
                result.insert(B.retrieve());
            }
            if(!B.last())
                B.findNext();
            else
                break;
        }
        return result;
    }
    public static boolean existIn_result(LinkedList<Integer> result, Integer ID){
        if(result.empty())
            return false;
        result.findFirst();
        while(!result.empty()){
            if(result.retrieve().equals(ID))
                return true;

          result.findNext();
        }
        if(result.retrieve().equals(ID))
            return true;

        return false;
    }

}
