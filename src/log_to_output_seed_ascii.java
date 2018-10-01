/**
 * 
 */

/**
 * @author Ashutosh
 *
 */

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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class log_to_output_seed_ascii {
	
	//Log to Output Class File, Whatever input is provided it ll log it to the file.

//	private static final String FILENAME = "C:\\Users\\Ashutosh\\eclipse-workspace\\OpenCV2-Test\\output\\Relation_seed_ascii.txt";
	private static final String FILENAME = read_gray.seed_ascii_path;
//    
// 
  public static BufferedWriter bw = null;
  public static FileWriter fw = null;
// 
 
	public static void log_output_seed_ascii(String output) {
 
		   File log = new File(FILENAME);

		   
		   String[] outputascii = output.split(" ");
		   String output_ascii_frame  =  Arrays.toString(outputascii[0].getBytes(StandardCharsets.US_ASCII)).replace(",","").replace("[", "").replace("]", "").replace(" ", "");
		   String output_ascii_group  =  Arrays.toString(outputascii[1].getBytes(StandardCharsets.US_ASCII)).replace(",", "").replace("[", "").replace("]", "").replace(" ", "");
		   String[] output_ascii_BoundBox  =  outputascii[2].toString().split(",");
		   
		   String x1 = Arrays.toString(output_ascii_BoundBox[0].substring(1).getBytes(StandardCharsets.US_ASCII)).replace(",","").replace("[", "").replace("]", "").trim().replace(" ", "");
		   String x2 = Arrays.toString(output_ascii_BoundBox[1].getBytes(StandardCharsets.US_ASCII)).replace(",","").replace("[", "").replace("]", "").trim().replace(" ", "");
		   String y1 = Arrays.toString(output_ascii_BoundBox[2].getBytes(StandardCharsets.US_ASCII)).replace(",","").replace("[", "").replace("]", "").trim().replace(" ", "");
		   String y2 = Arrays.toString(output_ascii_BoundBox[3].substring(0,output_ascii_BoundBox.length-1).getBytes(StandardCharsets.US_ASCII)).replace(",","").replace("[", "").replace("]", "").replace(" ", "");
		 
		   
		   //String output_ascii_featurevector  = Arrays.toString(outputascii[3].substring(1, outputascii[3].length()-1).getBytes(StandardCharsets.US_ASCII));
		    
		   String[] output_ascii_featurevector = outputascii[3].substring(1, outputascii[3].length()-1).split(",");
		   String feature = "";
		   for(int k=0;k<output_ascii_featurevector.length;k++) {
			   
			   String s = Arrays.toString(output_ascii_featurevector[k].getBytes(StandardCharsets.US_ASCII)).replace(",","").replace("[", "").replace("]", "").replace(" ", "");;
			   feature += s+",";
		   }
		   feature = "[" + feature + "]";
		   
		 //  String boundboxstring = "["+x1+","+x2+","+y1+","+y2+"]";
		//   String ascii_relation = output_ascii_frame +" "+ output_ascii_group+" "+ boundboxstring+" "+feature;
		   
		   //System.out.println(ascii_relation);
		   
		  // System.out.println(feature);
		try {
  
			
        
			fw = new FileWriter(log,true);
			bw = new BufferedWriter(fw);
			bw.write(feature);
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
