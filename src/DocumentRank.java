public class DocumentRank {
    public int id;
    public int rank;
    public DocumentRank(int id,int rank){
        this.id = id;
        this.rank = rank;
    }

    public void display(){
        System.out.printf("%-8d%-8d\n",id,rank);
    }

}

