import java.io.File;
import java.util.Scanner;

public class Reading { // This class will read the Excel file that contain the all_doc
    public static void Load(String fileName){
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
                System.out.println(line);  //prints the result from reading

                String x = line.substring(0,line.indexOf(',')); // we take the id's of the docs
                int id = Integer.parseInt(x.trim());  //id = document# // trim --> cuts the spaces // parseInt --> converting String into int
                String content = line.substring(line.indexOf(',')+1).trim(); // content = content of the document after the ","

            }
        }
        catch (Exception e){
            System.out.println("This is the end of the file");
        }
    }
}//