/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.opencv.core.Rect;

/**
 * 
 */

/**
 * @author Ashutosh,Aditya
 *
 */
public class Heuristic2 {

	/**
	 * @param args
	 */

   public static int heuristic2(int object_id,int frame_id) {
	   
	     double Threshold = Double.valueOf(read_gray.boundbox_match_threshold);
		 int group_id = 0;
	     String[] path = read_gray.Object_Map.get(String.valueOf(object_id)).trim().split(",=");
	       String[] Actualpath = path[0].toString().split(";");
	       String BoundBox1 = Actualpath[0].toString().substring(3);
		
	        String[] xy_coor1  = BoundBox1.split(",");
	        int  x1  = Integer.valueOf(xy_coor1[0].substring(1).trim());
	        int y1 = Integer.valueOf(xy_coor1[1].trim());
	   //  System.out.println("Frame 1:Bound:"+BoundBox1.toString()+x1+y1);
	
		for(int ol=0;ol <read_gray.objectlist.size();ol++) {
			
			if(object_id>0) {
			 String[] path1 = read_gray.Object_Map.get((String.valueOf(object_id-1))).trim().split(",=");
			 String[] Actualpath1  =  path1[0].toString().split(";");
			 String BoundBox2 = Actualpath1[0].toString().substring(3);
			
			 
			 
			 String[] xy_coor2  = BoundBox2.split(",");
		        int x2  = Integer.valueOf(xy_coor2[0].substring(1).trim());
		        int y2 = Integer.valueOf(xy_coor2[1].trim());
		  //   System.out.println("Frame 1:Bound:"+BoundBox2.toString()+x2+y2);
			 
			 
			 universal_struct.comparison_count++;
			
			// System.out.println("Preexisting map "+read_gray.Object_Map.get(read_gray.objectlist.get(ol)));
			// String[] tuples = read_gray.Object_Map.get(read_gray.objectlist.get(ol)).split("=");
		//		System.out.println("tuples:"+tuples[2].toString());
			 
			if(x2-x1 >= Threshold && y2-y1 >= Threshold) {
				
			// universal_struct.common_group =Integer.valueOf((read_gray.objectlist.get(ol)));
				System.out.println("Hueristic 2 GRPID:"+group_id);
		//	universal_struct.group_id = group_id;
				universal_struct.group_id  = Integer.valueOf((read_gray.objectlist.get(ol)));
				System.out.println("Hueristic 2 GRPID:"+universal_struct.group_id);
				break;
			}
			
		}
			
		}
	   
	return universal_struct.group_id;   
   }

}

