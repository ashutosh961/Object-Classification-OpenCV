/**
 * 
 */

/**
 * @author Ashutosh,Aditya
 *
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class log_to_output {
	
	//Log to Output Class File, Whatever input is provided it ll log it to the file.

	//private static final String FILENAME = "C:\\Users\\Ashutosh\\eclipse-workspace\\OpenCV2-Test\\output\\Relation.txt";
	private static final String FILENAME = read_gray.result_path;
// 
  public static BufferedWriter bw = null;
  public static FileWriter fw = null;
// 
 
	public static void log_output(String output) {
 
		   File log = new File(FILENAME);

		    
		try {
  
			
        
			fw = new FileWriter(log,true);
			bw = new BufferedWriter(fw);
			
			bw.write(output);
			bw.write("\n");
          
			System.out.println("Done");
       
          
			 
			 
			 
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

}
