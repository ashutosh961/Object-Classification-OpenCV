import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
public class read_config  {

	
	   public HashMap<String,String> readconfig() {
		   
		  //  String path =  "C:/Users/Ashutosh/eclipse-workspace/OpenCV-Test/input/config.txt";	   
		    String path =  "../Object-Classification-OpenCV/input/config.txt";
			BufferedReader reader;
			String line="";
		HashMap<String,String> configobject =  new HashMap<String,String>();
			
			try {
			     reader = new BufferedReader(new FileReader(path));
			     
				 while((line = reader.readLine())!=null) {
					 
					 String[] parts = line.split("=",2);
					 
					 if(parts.length>=2) {
						 
						 String key = parts[0];
						 String value  = parts[1];
					//	 System.out.println("key:" +key+"Value:"+value);
					//	 System.out.println(value);
						 configobject.put(key,value);
					 }
					 
					 else {
						 System.out.println("Ignoring Line:"+line);
					 }
				 }
				 
				 
				//	for (String key : configobject.keySet())
				   // {
				   //     System.out.println(key + ":" + configobject.get(key));
				  //  }
				    reader.close();
				
				}catch(IOException e) {
					
					System.out.println("File not found.Unable to open given file.");
				}
			
//			for (String key : configobject.keySet())
//		    {
//		        System.out.println(key + ":" + configobject.get(key));
//		    }
			
		   
		   
		   return configobject;
	   }
	
	
}
