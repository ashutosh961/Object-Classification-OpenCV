/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.opencv.core.Core;
import org.opencv.core.Mat;


public class seed_generation {
 
  static List<String> fid=new ArrayList<String>(100);
  static List<String> object_id=new ArrayList<String>(100);
  static List<String> group_id=new ArrayList<String>(100);
  static List<String> bound_box=new ArrayList<String>(100);
  static List<String> feature_vector=new ArrayList<String>(100);
//  int rows = 256;
  static int c_condition = 0;
  static int c_condition1 =0;
  static String core_filename = "";
  static String seed_filename = "";
//  int cols =3;
  int number_of_times = 90;
  static String seed = "";
  static String seed_features = "";
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    
	@SuppressWarnings("unchecked")
	public static void get_seed() {
		
		
		 core_filename = read_gray.result_path;
		 seed_filename = read_gray.seed_ascii_path;
		// File file = new File("C:\\Users\\Ashutosh\\eclipse-workspace\\OpenCV2-Test\\output\\Relation.txt");
		 File file = new File(core_filename);
		 
		 Map<String, ArrayList<String>> hmap = new HashMap<String, ArrayList<String>>();
		 
		 //ArrayList<String>object_id,group_id,bound_box,feature_vector;
		// File  seedFile= new File("C:\\Users\\Ashutosh\\eclipse-workspace\\OpenCV2-Test\\output\\output_tuple_seed.txt");
		File  seedFile= new File(seed_filename);
         try {
        	 BufferedReader br = new BufferedReader(new FileReader(file));
        	 String st;
        	 while((st = br.readLine())!= null){
        		 String s[] = st.split(" ");
        		     fid.add(s[0]);
        		    // object_id.add(s[1]);
        		     
        		     group_id.add(s[1]);
        		     bound_box.add(s[2]);
        		     feature_vector.add(s[3]); 
            }


        
        	 hmap.put("fid",(ArrayList<String>) fid);
    		// hmap.put("object_id", (ArrayList<String>) object_id);
    		 hmap.put("group_id", (ArrayList<String>) group_id);
    		 hmap.put("bound_box",(ArrayList<String>) bound_box);
    		 hmap.put("feature_vector",(ArrayList<String>) feature_vector);
    		 
    		//System.out.println("fid:"+fid);
    		 //System.out.println("object_id:"+object_id);
    		// System.out.println("group_id:"+group_id);
    		 //System.out.println("bound_box:"+bound_box);
    		//System.out.println("bound_box:"+feature_vector);
    		 
    	 }
    		 catch (Exception e) {
    			// TODO: handle exception
    		}
          
         Set<String> SortedSet = new LinkedHashSet<String>();
         SortedSet.addAll(group_id);
         Iterator<String> it = SortedSet.iterator();
         while(it.hasNext()) {
        	 String compare = it.next().toString();
        	 System.out.println("compare:"+compare);
        		 
        	 int occurrences = Collections.frequency(hmap.get("group_id"), compare);
        	 System.out.println("occurrences:"+occurrences);

        	 
        	//To remove one occurence mapping 
        		  
//        		  if(occurrences==1) {
//        			//  int position = new ArrayList<String>(hmap.get("group_id")).indexOf(compare);
//        			  int position = group_id.indexOf(compare);
//        			  System.out.println("position"+position);
//        			  fid.remove(fid.get(position));
//        			  group_id.remove(group_id.get(position));
//        			  bound_box.remove(bound_box.get(position));
//        			  feature_vector.remove(feature_vector.get(position));
//
//        			  //SortedSet.remove(compare);
//        		  }
        	  
        		  
           
        	 }
         
         
         System.out.println(SortedSet.toString());
         FileWriter fw = null;
         BufferedWriter bw = null;
         try {
 			fw = new FileWriter(seedFile);
 			bw = new BufferedWriter(fw);	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}       		
         
         for(String temp:SortedSet)
         {
        	 
        	for(int i=0;i<group_id.size();i++){
        		
        	if(group_id.get(i).equals(temp)){
        		
        		//System.out.println(temp+"--->"+feature_vector.get(i));
        		seed  =fid.get(i)+" "+group_id.get(i)+" "+bound_box.get(i)+" "+feature_vector.get(i);
        		if(!feature_vector.get(i).isEmpty()) {
        		 seed_features = feature_vector.get(i);
        		}
        		    if(get_group.Algorithm.contains("sift")) {
        		    	
        		    //Mat Descriptor = Sift_Feature.sift_features(path_abs1);
        		  //  System.out.println("SIFT OUTPUT"); 
        		    
        		    
        		  //  Attribute_Object.objectCategory = ObjTypeCategory.SIFT;
        		    
        		    Attribute_Object objectFrame = new Attribute_Object();
        		    objectFrame.Object_Name  = "frame_id";
        		    objectFrame.objectDataType = "number(long)";
        		    objectFrame.AttributeValue = String.valueOf(fid.get(i));
        		   
        		    Attribute_Object object_objectid = new Attribute_Object();
        		    object_objectid.Object_Name  = "object_id";
        		    object_objectid.objectDataType = "number(long)";
        		    object_objectid.AttributeValue = String.valueOf(object_id);
        		 
        		    Attribute_Object object_groupid = new Attribute_Object();
        		    object_groupid.Object_Name  = "group_id";
        		    object_groupid.objectDataType = "number(long)";
        		    object_groupid.AttributeValue = String.valueOf(group_id.get(i));
        		    
        		    Attribute_Object object_boundbox = new Attribute_Object();
        		    object_boundbox.Object_Name  = "boundbox";
        		    object_boundbox.objectDataType = "vector number(long) [4]";
        		    object_boundbox.AttributeValue = String.valueOf(bound_box.get(i).replace("x",", ").replaceAll("\\s","").replace("{", "[").replace("}","]").trim());
        		    

        		    Attribute_Object object_metric = new Attribute_Object();
        		    object_metric.Object_Name  = "similarity metric";
        		    object_metric.objectDataType = "String";
        		    object_metric.AttributeValue  = "Sift_match";
        		    
        		    Attribute_Object object_threshold = new Attribute_Object();
        		    object_threshold.Object_Name  = "similarity_threshold";
        		    object_threshold.objectDataType = "number(float)";
        		    object_threshold.AttributeValue  = String.valueOf(read_gray.match_threshold);
        		
        		    Attribute_Object object_signature = new Attribute_Object();
        		    object_signature.Object_Name  = "signature_method";
        		    object_signature.objectDataType = "String";
        		    object_signature.AttributeValue  = "Sift_Features";
        		    
        		   // String relation  = frame_id+" "+object_id+" "+group_id+" "+Integer.valueOf(object_id-1).toString()+" "+BoundBox1.replace("x",",")+" "+Descriptor.dump().toString()+"\n";
        		  //  System.out.println(frame_id+">>>>>"+object_id+">>>>>>"+group_id+">>>>>"+BoundBox1);
        		   
        		    JSONObject jsonframe= new JSONObject();
        		    jsonframe.put(object_groupid.Object_Name, object_groupid.AttributeValue); 
        		    jsonframe.put("datatype",object_groupid.objectDataType); 
        		    
        		    JSONObject jsongroup= new JSONObject();
        		    jsongroup.put(objectFrame.Object_Name, objectFrame.AttributeValue); 
        		    jsongroup.put("datatype",objectFrame.objectDataType); 
        		    
        		    JSONObject jsonobjectid= new JSONObject();
        		    jsonobjectid.put(object_objectid.Object_Name, object_objectid.AttributeValue); 
        		    jsonobjectid.put("datatype",object_objectid.objectDataType); 
        		  
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
        		     array.add(feature_vector.get(i).toString());
//        		     jsonobject.put("feature_vector",array);
//        		     System.out.println(jsonobject.toString());
        		     
        		 
        		     Attribute_Object object_feature = new Attribute_Object();
        			    object_feature.Object_Name  = "feature_vector";
        			    object_feature.objectDataType =  "vector number(long)"+"["+feature_vector.get(i).length()+"]";
        			    object_feature.AttributeValue = String.valueOf(array.toString().replaceAll("\\s","").trim());
        		     
        			    JSONObject jsonfeature= new JSONObject();
        			    jsonfeature.put("datatype",object_feature.objectDataType); 
        			  //  jsonfeature.put("signature", jsonsignature);
        			    jsonfeature.put(object_feature.Object_Name, object_feature.AttributeValue); 
        			  
        			    
        			    
        			    JSONArray jsonmaxarray= new JSONArray();
        			    jsonmaxarray.add(jsonframe);
        			   // jsonmaxarray.add(jsonobjectid);
        			    jsonmaxarray.add(jsongroup);
        			    jsonmaxarray.add(jsonbound);
        			    jsonmaxarray.add(jsonmetric);
        			    jsonmaxarray.add(jsonthreshold);
        			    jsonmaxarray.add(jsonsignature);
        			    jsonmaxarray.add(jsonfeature);
        			    
        			    JSONObject jsonobject= new JSONObject();
        			    jsonobject.put("Video", jsonmaxarray);
        			    
        			  //  log_to_object_ascii.log_object_ascii(jsonobject);
        			    log_to_object_ascii.log_object_ascii(feature_vector.get(i).toString());
        			    log_to_ascii_serialized.serializeObject(jsonobject);
        			    System.out.println(jsonobject.toJSONString());
        		    }
        		    if(get_group.Algorithm.contains("hist")) {
        		    	
            		    //Mat Descriptor = Sift_Feature.sift_features(path_abs1);
            		  //  System.out.println("SIFT OUTPUT"); 
            		    
            		    
            		  //  Attribute_Object.objectCategory = ObjTypeCategory.SIFT;
            		    
            		    Attribute_Object objectFrame = new Attribute_Object();
            		    objectFrame.Object_Name  = "frame_id";
            		    objectFrame.objectDataType = "number(long)";
            		    objectFrame.AttributeValue = String.valueOf(fid.get(i));
            		   
            		    Attribute_Object object_objectid = new Attribute_Object();
            		    object_objectid.Object_Name  = "object_id";
            		    object_objectid.objectDataType = "number(long)";
            		    object_objectid.AttributeValue = String.valueOf(object_id);
            		 
            		    Attribute_Object object_groupid = new Attribute_Object();
            		    object_groupid.Object_Name  = "group_id";
            		    object_groupid.objectDataType = "number(long)";
            		    object_groupid.AttributeValue = String.valueOf(group_id.get(i));
            		    
            		    Attribute_Object object_boundbox = new Attribute_Object();
            		    object_boundbox.Object_Name  = "boundbox";
            		    object_boundbox.objectDataType = "vector number(long) [4]";
            		    object_boundbox.AttributeValue = String.valueOf(bound_box.get(i).replace("x",", ").replaceAll("\\s","").replace("{", "[").replace("}","]").trim());
            		    

            		    Attribute_Object object_metric = new Attribute_Object();
            		    object_metric.Object_Name  = "similarity metric";
            		    object_metric.objectDataType = "string";
            		    object_metric.AttributeValue  = "Hist_match";
            		    
            		    Attribute_Object object_threshold = new Attribute_Object();
            		    object_threshold.Object_Name  = "similarity_threshold";
            		    object_threshold.objectDataType = "number(float)";
            		    object_threshold.AttributeValue  = String.valueOf(read_gray.match_threshold);
            		
            		    Attribute_Object object_signature = new Attribute_Object();
            		    object_signature.Object_Name  = "signature_method";
            		    object_signature.objectDataType = "string";
            		    object_signature.AttributeValue  = "Hist_Features";
            		    
            		   // String relation  = frame_id+" "+object_id+" "+group_id+" "+Integer.valueOf(object_id-1).toString()+" "+BoundBox1.replace("x",",")+" "+Descriptor.dump().toString()+"\n";
            		  //  System.out.println(frame_id+">>>>>"+object_id+">>>>>>"+group_id+">>>>>"+BoundBox1);
            		   
            		    JSONObject jsonframe= new JSONObject();
            		    jsonframe.put(object_groupid.Object_Name, object_groupid.AttributeValue); 
            		    jsonframe.put("datatype",object_groupid.objectDataType); 
            		    
            		    JSONObject jsongroup= new JSONObject();
            		    jsongroup.put(objectFrame.Object_Name, objectFrame.AttributeValue); 
            		    jsongroup.put("datatype",objectFrame.objectDataType); 
            		    
            		    JSONObject jsonobjectid= new JSONObject();
            		    jsonobjectid.put(object_objectid.Object_Name, object_objectid.AttributeValue); 
            		    jsonobjectid.put("datatype",object_objectid.objectDataType); 
            		  
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
            		     array.add(feature_vector.get(i).toString());
//            		     jsonobject.put("feature_vector",array);
//            		     System.out.println(jsonobject.toString());
            		     
            		 
            		     Attribute_Object object_feature = new Attribute_Object();
            			    object_feature.Object_Name  = "feature_vector";
            			    object_feature.objectDataType = "vector number(long) [768]";
            			    object_feature.AttributeValue = String.valueOf(array.toString().replaceAll("\\s","").trim());
            		     
            			    JSONObject jsonfeature= new JSONObject();
            			    jsonfeature.put("datatype",object_feature.objectDataType); 
            			 //   jsonfeature.put("signature", jsonsignature);
            			    jsonfeature.put(object_feature.Object_Name, object_feature.AttributeValue); 
            			   
            			    
            			    
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
            			   //Change 1.0 writing normal seed features instead of json object. 
            			  //  log_to_object_ascii.log_object_ascii(jsonobject);
            			    log_to_object_ascii.log_object_ascii(feature_vector.get(i).toString());
            			    log_to_ascii_serialized.serializeObject(jsonobject);   
            			    System.out.println(jsonobject.toJSONString());
            		    }
        		 
        		
        		//System.out.println(seed);
		    		try{
		    		//	bw.write(seed_features);
		    			 System.out.println("Seed Ascii Generated");
        	        	 log_to_output_seed_ascii.log_output_seed_ascii(seed);
        	        	
		    		//	bw.write("\n");
		    		}catch(Exception e){
		    			e.printStackTrace();
		    		}
        		
        		
        		break;
        		}	
        	}
        	
         }
         try{
        	 bw.close();
        	 fw.close();
        	 
         }catch(Exception e){
        	 e.printStackTrace();
         }
	  
         System.out.println("Seeds Generated.");
   }

        	
}


			



