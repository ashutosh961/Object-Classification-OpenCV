import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

public class process_objects {
	 
	  public static String frame_storage = "frame_storage_dir";
	  public static String object_storage = "object_storage_dir";
	  public static String min_width = "min_object_width";
	  public static String min_length = "min_object_length";
	  public static String max_object_frame = "max_object_per_frame";
	 // static List<Rect> BBList;
	  public static int object_id=0;
	  public static String frame_path = "";
	  public static String object_path  = "";
	  public static Integer max_object;
	  public static Integer minwidth;
	  public static Integer minlength;
	// public static Map<String,String> Object_Map = new LinkedHashMap<String,String>();
	 public static List<String> object_list;
	  
     static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
     
     public static  void GetConfigParametersframe( ) {
    		
		  HashMap<String, String> config = new read_config().readconfig();//Getting the config file object details.
		//  System.out.println(config.toString());
			for (String key : config.keySet())
		    {
				if(key.contains(frame_storage)) {
					
		 		 frame_path = config.get(key).toString().replaceAll("\\s+","");
				    System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(object_storage)) {
					
					 object_path = config.get(key).toString().replaceAll("\\s+","");
					   System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(min_width)) {	
					 minwidth = Integer.valueOf(config.get(key).toString().replaceAll("\\s+",""));
				 //   System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(min_length)) {	
					 minlength = Integer.valueOf(config.get(key).toString().replaceAll("\\s+",""));
			//	    System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(max_object_frame)) {	
					 max_object = Integer.valueOf(config.get(key).toString().replaceAll("\\s+",""));
				//    System.out.println(key + ":" + config.get(key));
				}
		      
		    }
	 }
     
  	 //Process Objects
     
	 public static List<String> process_object(List<Rect> BBList,Mat frame_m_Color,int frame_id) {
		
		process_objects.GetConfigParametersframe(); 
		 //ObjectList to get all the given objects
		
		 object_list = new ArrayList<String>();
		 if(BBList.size()>0) { 		
		     double MaxArea = BBList.get(BBList.size()-1).area();
		     for(int bb=0;bb<BBList.size();bb++) { 
		      //If BoundBox has width and height above min_length and min_width just plot the given boundbox     	 
		    	 if( BBList.get(bb).width > minwidth && BBList.get(bb).height  > minlength) {
		    		 
		    	 if(BBList.get(bb).area()>0.4*(MaxArea) || BBList.get(bb).area()==MaxArea) 
		    	 { 
		    		 Core.rectangle(frame_m_Color, BBList.get(bb).tl(),BBList.get(bb).br(), new Scalar(255,255, 255),1, 8,0); Mat image_roi = new Mat(frame_m_Color,BBList.get(bb));	     
		    		// Highgui.imwrite("output/object_storage_directory/"+"cropped"+"_"+frame_id+"_"+object_id+".jpg",image_roi);
		    		 Highgui.imwrite("output/object_storage_directory/"+"cropped_"+frame_id+"_"+object_id+".jpg",image_roi);
		    		 //Persist all cropped bound_box images
		    		 String path = " path="+"../output/object_storage_directory/cropped_"+frame_id +"_"+ object_id+ ".jpg"+";";
		    		 String path1 = "../output/object_storage_directory/cropped_"+frame_id +"_"+ object_id+ ".jpg"+";";
		    		 //Store tuples to the group_map object
		    		 
                     read_gray.Frame_Map.put("frame_id",String.valueOf(frame_id));
                     read_gray.Frame_Map.put("path", path1);
                     

		    		 String val ="BB=" +BBList.get(bb).toString()+";" ;
		    		 String tuple = val + path;
		    		 if(!tuple.isEmpty()) {
		    		 read_gray.Object_Map.put(String.valueOf(object_id), tuple);
		    		 read_gray.Object_Frame_Map.put("object-id",String.valueOf(object_id));
		    		 read_gray.Object_Frame_Map.put("BoundBox",BBList.get(bb).toString());
		    		 read_gray.Object_Frame_Map.put("frame-map", read_gray.Frame_Map.toString());
		    		 object_list.add(String.valueOf(object_id));
		    		 //Increment Object Id
		    		 object_id++;
		    		 }    
		    		 
			      }
		    }
		  } // System.out.println(Object_Map.toString());
		    // System.out.println(object_list.toString());
		}
		 return object_list;
	}
	 
	 
	 
	
	
}
