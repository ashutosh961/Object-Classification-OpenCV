import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.opencv.core.Mat;
//Author Ashutosh,Aditya
public class get_group {
	
	
	static int group_count_local  = universal_struct.group_count;
	static int group_id = universal_struct.group_id;
	static String feature_Extractor = "feature_extractor_output";
	static String Algorithm = "";
	static int object_count = 0;
    public static Map<Integer,String> ObjectMap = new LinkedHashMap<Integer,String>(); 
	 public static  void GetConfigParameters( ) {
			
		  HashMap<String, String> config = new read_config().readconfig();//Getting the config file object details.
		//  System.out.println(config.toString());
			for (String key : config.keySet())
		    {
				if(key.contains(feature_Extractor)) {
					
					Algorithm = config.get(key).toString();
				    System.out.println(Algorithm);
				}		
		      
		    }
	 }
	
	
   @SuppressWarnings("unchecked")
public static int  Get_Group(int object_id,int frame_id,String heuristic){
	//   Map<String,universal_struct> Object_Frame_Map = new LinkedHashMap<String,universal_struct>();   
	   universal_struct.Object_Frame_Map = new LinkedHashMap<String,String>(); 
	   
	   GetConfigParameters();
	   
	   int available_size = read_gray.Group_Map.size();
	if(available_size==0) {
			
		
		     universal_struct.Object_Frame_Map.put(String.valueOf(group_id),String.valueOf(frame_id));
			 group_count_local++;
			universal_struct.group_count = group_count_local;
		    
			
			universal_struct.Group_Object_Map.put(String.valueOf(object_id), universal_struct.Object_Frame_Map);
			
		}
	   
	  if(heuristic.equals("heuristic1")) {
		//  System.out.println("Heuristic 1 called.");
		  group_id =  Heuristic2.heuristic2(object_id, frame_id);
          universal_struct.group_id = group_id;
		   group_id=Heuristic1.heuristic1(object_id, frame_id);
		   universal_struct.group_id = group_id;
          group_id=Exhaustive.Exhaustive_heuristic(object_id, frame_id);
           universal_struct.group_id = group_id;         
	  }
	  
	  
	  
	  
	  
	   //Get corresponding image paths in the directories for object ids
	     String[] path = read_gray.Object_Map.get(String.valueOf(object_id)).trim().split("=");
         
	     Path path_abs = Paths.get(path[2].substring(3));
	    String path_abs1 =   path_abs.toAbsolutePath().toString().substring(0,path_abs.toAbsolutePath().toString().length()-1);
	    
	    //Get Bounding Boxes of all object_ids
	    String[] BBpath = read_gray.Object_Map.get(String.valueOf(object_id)).trim().split(",=");
	       String[] BBActualpath = BBpath[0].toString().split(";");
	       String BoundBox1 = BBActualpath[0].toString().substring(3);
	    
	  
	     
	       
	    if(Algorithm.contains("sift")) {
	    	
	    Mat Descriptor = Sift_Feature.sift_features(path_abs1);
	  //  System.out.println("SIFT OUTPUT"); 
	    
	    
	  //  Attribute_Object.objectCategory = ObjTypeCategory.SIFT;
	    
	    Attribute_Object objectFrame = new Attribute_Object();
	    objectFrame.Object_Name  = "frame_id";
	    objectFrame.objectDataType = "number(long)";
	    objectFrame.AttributeValue = String.valueOf(frame_id);
	   
//	    Attribute_Object object_objectid = new Attribute_Object();
//	    object_objectid.Object_Name  = "object_id";
//	    object_objectid.objectDataType = "int";
//	    object_objectid.AttributeValue = String.valueOf(object_id);
	 
	    Attribute_Object object_groupid = new Attribute_Object();
	    object_groupid.Object_Name  = "group_id";
	    object_groupid.objectDataType = "number(long)";
	    object_groupid.AttributeValue = String.valueOf(group_id);
	    
	    Attribute_Object object_boundbox = new Attribute_Object();
	    object_boundbox.Object_Name  = "boundbox";
	    object_boundbox.objectDataType = "vector number(long) [4]";
	    object_boundbox.AttributeValue = String.valueOf(BoundBox1.replace("x",", ").replaceAll("\\s","").replace("{", "[").replace("}","]").trim());
	    

	    Attribute_Object object_metric = new Attribute_Object();
	    object_metric.Object_Name  = "similarity metric";
	    object_metric.objectDataType = "string";
	    object_metric.AttributeValue  = "Sift_match";
	    
	    Attribute_Object object_threshold = new Attribute_Object();
	    object_threshold.Object_Name  = "similarity_threshold";
	    object_threshold.objectDataType = "number(float)";
	    object_threshold.AttributeValue  = String.valueOf(read_gray.match_threshold);
	
	    Attribute_Object object_signature = new Attribute_Object();
	    object_signature.Object_Name  = "signature_method";
	    object_signature.objectDataType = "string";
	    object_signature.AttributeValue  = "Sift_Features";
	    
	   // String relation  = frame_id+" "+object_id+" "+group_id+" "+Integer.valueOf(object_id-1).toString()+" "+BoundBox1.replace("x",",")+" "+Descriptor.dump().toString()+"\n";
	  //  System.out.println(frame_id+">>>>>"+object_id+">>>>>>"+group_id+">>>>>"+BoundBox1);
	   
	    JSONObject jsonframe= new JSONObject();
	    jsonframe.put(object_groupid.Object_Name, object_groupid.AttributeValue); 
	    jsonframe.put("datatype",object_groupid.objectDataType); 
	    
	    JSONObject jsongroup= new JSONObject();
	    jsongroup.put(objectFrame.Object_Name, objectFrame.AttributeValue); 
	    jsongroup.put("datatype",objectFrame.objectDataType); 
	    
//	    JSONObject jsonobjectid= new JSONObject();
//	    jsonobjectid.put(object_objectid.Object_Name, object_objectid.AttributeValue); 
//	    jsonobjectid.put("datatype",object_objectid.objectDataType); 
	  
	    JSONObject jsonbound= new JSONObject();
	    jsonbound.put(object_boundbox.Object_Name, object_boundbox.AttributeValue); 
	    jsonbound.put("datatype",object_boundbox.objectDataType); 
	    
	    
	    JSONObject jsonthreshold =new JSONObject();
	    jsonthreshold.put(object_threshold.Object_Name,object_threshold.AttributeValue);
	    jsonthreshold.put("datatype",object_threshold.objectDataType); 
	  
	    JSONObject jsonmetric =new JSONObject();
	    jsonmetric.put(object_metric.Object_Name,object_metric.AttributeValue);
	    jsonmetric.put("datatype",object_metric.objectDataType); 
	    
	 
	    JSONObject jsonsignature =new JSONObject();
	    jsonsignature.put(object_signature.Object_Name,object_signature.AttributeValue);
	    jsonsignature.put("datatype",object_signature.objectDataType); 
	    
	    
	    JSONArray array = new JSONArray();
	     array.add(Descriptor.dump().toString());
//	     jsonobject.put("feature_vector",array);
//	     System.out.println(jsonobject.toString());
	     
	     String relation  = frame_id+" "+group_id+" "+BoundBox1.replace("x",", ").replaceAll("\\s","").replace("{", "[").replace("}","]").trim()+" "+"["+array.toString().replaceAll("\\s","").trim().replace("[", "").replace("]", "").replace("\"", "")+"]";
	     
	     Attribute_Object object_feature = new Attribute_Object();
		    object_feature.Object_Name  = "feature_vector";
		    object_feature.objectDataType = "vector number(double)"+" ["+Descriptor.dump().length()+"]";
		    object_feature.AttributeValue = String.valueOf(array.toString().replaceAll("\\s","").trim());
	     
		    JSONObject jsonfeature= new JSONObject();
		    jsonfeature.put(object_feature.Object_Name, object_feature.AttributeValue); 
		    jsonfeature.put("datatype",object_feature.objectDataType); 
		 //   jsonfeature.put("signature", jsonsignature);
		    
		    
		    JSONArray jsonmaxarray= new JSONArray();
		    jsonmaxarray.add(jsonframe);
		//    jsonmaxarray.add(jsonobjectid);
		    jsonmaxarray.add(jsongroup);
		    jsonmaxarray.add(jsonbound);
		    jsonmaxarray.add(jsonmetric);
		    jsonmaxarray.add(jsonthreshold);
		    jsonmaxarray.add(jsonsignature);
		    jsonmaxarray.add(jsonfeature);
		    
		    JSONObject jsonobject= new JSONObject();
		    jsonobject.put("Video", jsonmaxarray);
		    
		
		  
		log_to_output_ascii.log_output(relation);   
	    log_to_output.log_output(relation);
	    log_to_object.log_object(jsonobject);
	    ObjectMap.put(object_count,jsonobject.toJSONString());
	    object_count++;
	    log_to_serialize_object.serializeAddress(jsonobject);
	    
	    }
	
	    if(Algorithm.contains("hist")) {
		  //  List<Mat> Descriptor = Hist_Feature.hist_features(path_abs1);
		    String Descriptor = Hist_Feature.hist_features(path_abs1);
		 //   System.out.println(Descriptor.get(0).dump());
		   
    
		//    System.out.println("HIST OUTPUT");
		    
		    Attribute_Object objectFrame = new Attribute_Object();
		    objectFrame.Object_Name  = "frame_id";
		    objectFrame.objectDataType = "number(long)";
		    objectFrame.AttributeValue = String.valueOf(frame_id);
		   
//		    Attribute_Object object_objectid = new Attribute_Object();
//		    object_objectid.Object_Name  = "object_id";
//		    object_objectid.objectDataType = "int";
//		    object_objectid.AttributeValue = String.valueOf(object_id);
//		 
		    Attribute_Object object_groupid = new Attribute_Object();
		    object_groupid.Object_Name  = "group_id";
		    object_groupid.objectDataType = "number(long)";
		    object_groupid.AttributeValue = String.valueOf(group_id);
		    
		    Attribute_Object object_boundbox = new Attribute_Object();
		    object_boundbox.Object_Name  = "boundbox";
		    object_boundbox.objectDataType = "vector number(long) [4]";
		    object_boundbox.AttributeValue = String.valueOf(BoundBox1.replace("x",", ").replaceAll("\\s","").replace("{", "[").replace("}","]").trim());
		    
		    Attribute_Object object_metric = new Attribute_Object();
		    object_metric.Object_Name  = "similarity metric";
		    object_metric.objectDataType = "string";
		    object_metric.AttributeValue  = "hist_match";
		    
		    Attribute_Object object_threshold = new Attribute_Object();
		    object_threshold.Object_Name  = "similarity_threshold";
		    object_threshold.objectDataType = "number(float)";
		    object_threshold.AttributeValue  = String.valueOf(object_threshold.AttributeValue);

		    Attribute_Object object_signature = new Attribute_Object();
		    object_signature.Object_Name  = "signature_method";
		    object_signature.objectDataType = "string";
		    object_signature.AttributeValue  = "Hist_Features";
		    
		    JSONObject jsonframe= new JSONObject();
		    jsonframe.put(object_groupid.Object_Name, object_groupid.AttributeValue); 
		    jsonframe.put("datatype",object_groupid.objectDataType); 
		    
		    JSONObject jsongroup= new JSONObject();
		    jsongroup.put(objectFrame.Object_Name, objectFrame.AttributeValue); 
		    jsongroup.put("datatype",objectFrame.objectDataType); 
		    
//		    JSONObject jsonobjectid= new JSONObject();
//		    jsonobjectid.put(object_objectid.Object_Name, object_objectid.AttributeValue); 
//		    jsonobjectid.put("datatype",object_objectid.objectDataType); 
//		  
		    JSONObject jsonbound= new JSONObject();
		    jsonbound.put(object_boundbox.Object_Name, object_boundbox.AttributeValue); 
		    jsonbound.put("datatype",object_boundbox.objectDataType); 
		    
		    JSONObject jsonthreshold =new JSONObject();
		    jsonthreshold.put(object_threshold.Object_Name,object_threshold.AttributeValue);
		    jsonthreshold.put("datatype",object_threshold.objectDataType); 
		  
		    JSONObject jsonmetric =new JSONObject();
		    jsonmetric.put(object_metric.Object_Name,object_metric.AttributeValue);
		    jsonmetric.put("datatype",object_metric.objectDataType); 
		    
		    JSONObject jsonsignature =new JSONObject();
		    jsonsignature.put(object_signature.Object_Name,object_signature.AttributeValue);
		    jsonsignature.put("datatype",object_signature.objectDataType); 
		   
		    
		    JSONArray array = new JSONArray();
		     array.add(Descriptor.toString());
		    // jsonobject.put("feature_vector",array);
		     //System.out.println(jsonobject.toString());
		     
		     Attribute_Object object_feature = new Attribute_Object();
			    object_feature.Object_Name  = "feature_vector";
			    object_feature.objectDataType = "vector number(long) [768]";
			    object_feature.AttributeValue = String.valueOf(array.toString().replaceAll("\\s","").trim());
		     
			    JSONObject jsonfeature= new JSONObject();
			    jsonfeature.put(object_feature.Object_Name, object_feature.AttributeValue); 
			    jsonfeature.put("datatype",object_feature.objectDataType);  
			   // jsonfeature.put("signature", jsonsignature);
			    
			    JSONArray jsonmaxarray= new JSONArray();
			    jsonmaxarray.add(jsonframe);
			 //   jsonmaxarray.add(jsonobjectid);
			    jsonmaxarray.add(jsongroup);
			    jsonmaxarray.add(jsonbound);
			    jsonmaxarray.add(jsonmetric);
			    jsonmaxarray.add(jsonthreshold);
			    jsonmaxarray.add(jsonsignature);
			    jsonmaxarray.add(jsonfeature);
			    
			    JSONObject jsonobject= new JSONObject();
			    jsonobject.put("Video", jsonmaxarray);

			    
		     String relation  = frame_id+" "+group_id+" "+BoundBox1.replace("x",", ").replaceAll("\\s","").replace("{", "[").replace("}","]").trim()+" "+"["+array.toString().replace("\\n","").replaceAll("\\s","").trim().replace("[", "").replace("]", "").replace("\"", "")+"]";

		    log_to_output_ascii.log_output(relation); 
		    log_to_output.log_output(relation);//Log tuple to file
		    log_to_object.log_object(jsonobject);//Log object to file
		    log_to_serialize_object.serializeAddress(jsonobject);//Log serialised object output to file
		    ObjectMap.put(object_count,jsonobject.toJSONString());
		    object_count++;
		//    log_to_serialize_object.deserializeAddress();
		    }
	   
	   // log_to_serialize_object.serializeAddress(ObjectMap);    
		   
		   // System.out.println(available_size);
	 if(available_size>=0){
		
		if(universal_struct.Group_Object_Map.containsKey(String.valueOf(group_id))) {
			System.out.println("Inside Group Matching");
			universal_struct.Object_Frame_Map = universal_struct.Group_Object_Map.get(String.valueOf(group_id));
		}
		
		universal_struct.Object_Frame_Map.put(String.valueOf(object_id), String.valueOf(frame_id));
		universal_struct.Group_Object_Map.put(String.valueOf(group_id), universal_struct.Object_Frame_Map);
		
		
		universal_struct.group_count++;
	}   
	universal_struct.group_id = group_id;
	//System.out.println("GroupID:"+group_id);
	
	
	return group_id;
   }

}
   
