import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * 
 */

/**
 * @author Ashutosh,Aditya
 *
 */
public class Heuristic1 {

	/**
	 * @param args
	 */
	
	  static String Hist_Identifier = "hist_match"; 
	  static String Sift_Identifier = "sift_match";
	  static String Surf_Identifier = "surf_match";
	  static double dist;
	  static String algorithm = read_gray.feature_extractor_algorithm;
	  
	

   public static int heuristic1(int object_id,int frame_id) {
	    
	    
	    // double Threshold = 0.3 ;
	     double Threshold  = Double.valueOf(read_gray.match_threshold);
		
	
			   String[] path = read_gray.Object_Map.get(String.valueOf(object_id)).trim().split("=");
			//String[] path = read_gray.Object_Map.get(String.valueOf(read_gray.objectlist.get(ol))).trim().split("=");
		          
			     Path path_abs = Paths.get(path[2].substring(3));
			    String path_abs1 =   path_abs.toAbsolutePath().toString().substring(0,path_abs.toAbsolutePath().toString().length()-1);
	     
	
		for(int ol=0;ol <read_gray.objectlist.size();ol++) {
		
//			//   String[] path = read_gray.Object_Map.get(String.valueOf(object_id)).trim().split("=");
//			String[] path = read_gray.Object_Map.get(String.valueOf(read_gray.objectlist.get(ol))).trim().split("=");
//		          
//			     Path path_abs = Paths.get(path[2].substring(3));
//			    String path_abs1 =   path_abs.toAbsolutePath().toString().substring(0,path_abs.toAbsolutePath().toString().length()-1);
			
			
			if(object_id>0) {
			 String[] path1 = read_gray.Object_Map.get((String.valueOf(object_id-1))).trim().split("=");
			 String[] path_1 = path1[2].split(" ");
			  Path path_abs_2 = Paths.get(path_1[0].substring(3)).toAbsolutePath(); 
			  String path_abs2 = path_abs_2.toString().substring(0,path_abs_2.toString().length()-1);
		  //    System.out.println("path1:"+path_abs1);
			// System.out.println("path2:"+path_abs2);
			
			  if(algorithm.contains("hist")) {
				 // System.out.println("Algorithm"+algorithm);
			  dist =Hist_detector.hist_match(path_abs1, path_abs2);} 
			  
			  else if(algorithm.contains("sift")) {	 // System.out.println("Algorithm"+algorithm);
				  dist =sift_detector.sift_match(path_abs1, path_abs2);}
			 
			 universal_struct.comparison_count++;
			 System.out.println("Dist is:"+dist);
 
			if(dist <= Threshold) {
				
			int  group_id =Integer.valueOf((read_gray.objectlist.get(ol)));
				System.out.println("GRPID Hueristic 1:"+group_id);
			return group_id;
			}
			
		}
			
		}
	   
	return universal_struct.group_id;   
   }

}
