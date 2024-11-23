public class QueryIndex {
    public static index index;
    public QueryIndex(index Index) {
        this.index = Index;
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
        String words[] = Query.split("AND");
        if(words.length == 0){
            return A;
        }
        A= index.get_all_document_given_terms(words[0].trim().toLowerCase());


        for(int i=1;i<words.length;i++){
           B= index.get_all_document_given_terms(words[i].trim().toLowerCase());
            A=andQuery(A,B);
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
            boolean found = existInResult(result, A.retrieve());
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
        String words[] = Query.split("OR");
        if(words.length == 0){
            return A;
        }
       A=index.get_all_document_given_terms(words[0].trim().toLowerCase());

        for(int i=1;i<words.length;i++){
            B= index.get_all_document_given_terms(words[i].trim().toLowerCase());
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
            boolean found = existInResult(result, A.retrieve());
            if(!found){
                result.insert(A.retrieve());
            }
            if(!A.last())
                A.findNext();
            else
                break;

        }//
        B.findFirst();
        while(!B.empty()){
            boolean found = existInResult (result, B.retrieve());
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
    public static boolean existInResult(LinkedList<Integer> result, Integer ID){
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
