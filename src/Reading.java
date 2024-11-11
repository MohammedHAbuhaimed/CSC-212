import java.io.File;
import java.util.Scanner;
public class Reading {
    public static void Load(String fileName){
        String line;
        try{
            File f= new File(fileName);
            Scanner s = new Scanner(f);
            s.nextLine(); // this skips the first row in Excel file

            while(s.hasNextLine()){
                line = s.nextLine();  //stores the data
                if(line.trim().length()<3){
                    System.out.println("End of line");
                    break;
                }
                System.out.println(line);  //prints the result from reading

                String x = line.substring(0,line.indexOf(','));
                int id = Integer.parseInt(x.trim());  //id = document#
                String content = line.substring(line.indexOf(',')+1).trim(); // content = content of the document
            }
        }
        catch (Exception e){
            System.out.println("end of file");
        }
    }
}





