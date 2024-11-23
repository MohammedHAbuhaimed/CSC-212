public class index {
    LinkedList<Document> allDocuemnts;
    
    public index(){
        allDocuemnts =new LinkedList<Document>();
    }

    ///helping methods//
    public void addDocuemnt(Document d){
        allDocuemnts.insert(d);
    }
    
    public void displayDocuments(){ 
      if (allDocuemnts ==null){
          System.out.println("null docs");
          return;    
      }else if (allDocuemnts.empty()){
          System.out.println("empty docs");
          return;
      }
      
      allDocuemnts.findFirst();
      while (!allDocuemnts.last()){
          Document doc= allDocuemnts.retrieve();
          System.out.println("\n ------------------------");
          System.out.println("ID:"+ doc.id);
          doc.words.display();
          allDocuemnts.findNext();
      }
      
      Document doc= allDocuemnts.retrieve();
       System.out.println("\n ------------------------");
       System.out.println("ID:"+ doc.id);
       doc.words.display();       
    }
    
    public void findAndDisplayDoc(int id) {
    allDocuemnts.findFirst();
    while (!allDocuemnts.last()) {
        Document doc = allDocuemnts.retrieve();
        if (doc.id == id) {
            System.out.print(id);
            return;
        }
        allDocuemnts.findNext();
    }
}
    
//    Method to retrun document given the id, used in class Ranked
    public Document getAllDocGivenID(int id) {
        if(allDocuemnts.empty()){
            System.out.println("No document exisit");
            return null;
        }
        
        allDocuemnts.findFirst();
        while (!allDocuemnts.last()) {
            if(allDocuemnts.retrieve().id == id){
                System.out.print(id);
                return allDocuemnts.retrieve();
            }
            allDocuemnts.findNext();
        }
        if(allDocuemnts.retrieve().id == id){
                System.out.print(id);
                return allDocuemnts.retrieve();
            }
        return null; //if not found
    }
    
    public LinkedList<Integer> getAllDocGivenTerm(String term){
        LinkedList<Integer>result = new LinkedList<>();
        if(allDocuemnts.empty()){
            System.out.println("No documents found.");
            return null;
        }
        allDocuemnts.findFirst();
        while(!allDocuemnts.last()){
            if (allDocuemnts.retrieve().words.search(term.toLowerCase().trim()))
                result.insert(allDocuemnts.retrieve().id);
            allDocuemnts.findNext();
        }
        if (allDocuemnts.retrieve().words.search(term.toLowerCase().trim()))
            result.insert(allDocuemnts.retrieve().id);
        return result;
    }
  
    }
