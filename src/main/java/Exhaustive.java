/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.acl.Group;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */

/**
 * @author Ashutosh,Aditya
 *
 */
public class Exhaustive {

	/**
	 * @param args
	 */
	  static boolean match = false;
	  static String Hist_Identifier = "hist_match"; 
	  static String Sift_Identifier = "sift_match";
	  static String Surf_Identifier = "surf_match";
	  static double dist;
	  static String Algorithm = read_gray.feature_extractor_algorithm;
	  


   public static int Exhaustive_heuristic(int object_id,int frame_id) {
	  
	   
		double Threshold = Double.valueOf(read_gray.match_threshold); 
	    
			   String[] path = read_gray.Object_Map.get(String.valueOf(object_id)).trim().split("=");
			//String[] path = read_gray.Object_Map.get(String.valueOf(read_gray.objectlist.get(ol))).trim().split("=");
		          
			     Path path_abs = Paths.get(path[2].substring(3));
			    String path_abs1 =   path_abs.toAbsolutePath().toString().substring(0,path_abs.toAbsolutePath().toString().length()-1);
	     
	
		for(int ol=0;ol <read_gray.objectlist.size();ol++) {
		
			
			if(object_id>=0) {
	           
				for(Object values:read_gray.Group_Frame_Map.entries()) {
					
				//   System.out.println("values:"+values.toString());
				   
					String[] value = values.toString().split(" ");
				 
					String[] value2= value[5].toString().split("=");
					
					Path path_abs2 = Paths.get(value2[1].substring(3).toString().replace("}","").replace(";",""));
					
					String path_abs_final = path_abs2.toAbsolutePath().toString();
//					 System.out.println(path_abs2.toAbsolutePath().toString());
//				      System.out.println("Exhautive path1:"+path_abs1);
//					 System.out.println("Exhaustive path2:"+path_abs_final);
					
						
					  if(Algorithm.contains("hist")) {
						   
						  
						      Threshold = 0.94 ;
							  dist =Hist_detector.hist_match(path_abs1, path_abs_final);}
					  
					  else if(Algorithm.contains("sift")) {
						//  Threshold = 0.3 ;
						  //System.out.println(" Exhuustive Algorithm"+Algorithm);
						  dist =sift_detector.sift_match(path_abs1, path_abs_final);}
				//	  System.out.println("Exhautive path1:"+path_abs1);
					//	System.out.println("Exhaustive path2:"+path_abs_final);        
					  
					//  System.out.println("Exhaustive Dist"+dist);
						
							 if(dist>=Threshold) {
//								 System.out.println("Match path1:"+path_abs1);
//									System.out.println("Match path2:"+path_abs_final);
								 match = true;
								 
								System.out.println("Comparison done");	
								System.out.println("ehaustive dist"+dist);
								System.out.println("VALUES:"+values);
								
								//read_gray.Group_Frame_Map.entries();
								
								String[] group = values.toString().split("=");
								String group1 = group[0].toString();
								System.out.println("Group:"+group1);

								universal_struct.group_id = Integer.valueOf(group1);
								break;
								
							}
	 
				}
		
	 
			 universal_struct.comparison_count++;

		}
			
	}
		
		if(match==false) {	universal_struct.group_id = object_id;}
	
	return universal_struct.group_id;   
   }

}
