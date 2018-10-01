/**
 * 
 */



/**
 * @author Ashutosh,Aditya
 *
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.json.simple.JSONObject;

import com.sun.jndi.cosnaming.IiopUrl.Address;

public class log_to_object {
	
	//Log to Output Class File, Whatever input is provided it ll log it to the file.
   //Mapping tuples directly to JSON objects
	//private static final String FILENAME = "C:\\Users\\Ashutosh\\eclipse-workspace\\OpenCV2-Test\\output\\Relation_object.txt";
	private static final String FILENAME = read_gray.object_path;
//    
// 
  public static BufferedWriter bwobject = null;
  public static FileWriter fwobject = null;
// 
 
	public static void log_object(JSONObject output) {
 
		   File log = new File(FILENAME);

		    
		try {
  
			
        
			fwobject = new FileWriter(log,true);
			bwobject = new BufferedWriter(fwobject);
			bwobject.write(output.toString());
			bwobject.write("\n");
          
			System.out.println("Object_written");
       
			
			 
			 
			 
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bwobject != null)
					bwobject.close();

				if (fwobject != null)
					fwobject.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
	}
	

	

}

