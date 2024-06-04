/**
 * 
 */

/**
 * @author Ashutosh,Aditya
 *
 */

import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.security.auth.login.Configuration;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.features2d.FeatureDetector;

import java.awt.image.BufferedImage;
import org.opencv.video.*;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;

import java.awt.image.DataBufferByte;
import java.awt.image.ImageProducer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class read_gray
{
	 static private String video_identifier = "video_file_path";
	 static private String end_frame_identifier = "end_frame";
	 static private String start_frame_identifier = "start_frame";
	 static private String output_frame_path = "frames_storage_dir";
	 static private String feature_extractor_algorithm_identifier = "feature_extractor";
	 static private String background_threshold_value_identifier = "background_subtraction_threshold";
	 static private String result_output_path_identifer = "readable_tuples_file_name";
	 static private String result_object_path_identifer = "readable_object_file_name";
	 static private String result_seed_path_identifer = "readable_seed_file_name";
	 static private String seed_tuple_path_identifier = "seed_ascii_file_name";
	 static private String seed_object_path_identifier = "seed_object_file_name";
	 static private String tuple_ascii_path_identifier = "tuple_ascii_file_name";
	 static private String tuple_object_path_identifier = "tuple_object_file_name";
	 static private String match_threshold_identifier = "match_threshold";
	 static private String boundbox_match_threshold_identifier = "bounding_box_movement_threshold";
	 static public String boundbox_match_threshold = "";
	 static public String match_threshold = "";
	 static public String seed_ascii_path = "";
	 static public String seed_object_path = "";
	 static public String tuple_ascii_path = "";
	 static public String tuple_object_path = "";
	 static private String path1 = "";
	 static private String output_path = "";
	 static public String feature_extractor_algorithm  = "";
	 static public String background_threshold = "";
	 static public String result_path ="";
	 static public String object_path ="";
	 static public String seed_path ="";
	 static public int batch_no =0;
	 static private Mat frame;//Original Color Frame
//	 static private Mat GrayFrame;//GrayScale Color Frame
	 static int frame_number = 0;
	 static int max_frames_allowed = 0;
	 static int start_frames_allowed = 0;
	 static int xl =0;
	 static List<Rect> BBList;
	 static process_objects object;
	 static int frame_id;
	public static Set<String> groupset = new LinkedHashSet<String>();
	static Scalar color = new Scalar(255, 255, 255);
//Universal Struct Variables **static	
	//Frame Map,Object Map and Group_Map
	public static Map<String,String> Frame_Map = new LinkedHashMap<String,String>();
	public static Map<String,String> Object_Frame_Map =  new LinkedHashMap<String,String>();
	public static Multimap<String,String> Group_Frame_Map =  LinkedHashMultimap.create();
	public static int frame_iteration = 0;
	public static Map<String,String> Object_Map = new LinkedHashMap<String,String>();
	public static Map<String,universal_struct> Group_Map = new LinkedHashMap<String,universal_struct>();
	public static List<String> objectlist;
	private static Mat total_frame;
	public static int group_count = 1;
	public static int com_count = 1;
	public static int frame_count_total = 0;
	public static int n=0;
	public static int batch_frame_start = 0;
	public static int max_process =1;
	public static int  frame_height = 0;
	public static int frame_width = 0;
	
	//Universal Struct Variables **static
	
	
	 //To store the bufferedFrame into Jframe Matrix 
	 public static BufferedImage Mat2BufferedImage(Mat m)
	    {
	        int type = BufferedImage.TYPE_BYTE_GRAY;
	        if (m.channels() > 1)
	        {
	            type = BufferedImage.TYPE_3BYTE_BGR;
	        }
	        int bufferSize = m.channels()*m.cols()*m.rows();
	        byte[] b = new byte[bufferSize];
	        m.get(0, 0, b); // get all the pixels
	        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
	        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	        System.arraycopy(b, 0, targetPixels, 0, b.length);  
	        return image;
	    }
	 
	 
	
	 
	 //Background Subtract Code
	 public static void  BackGroundSubtract() {
		 int number=0;
		 int frame_number_total = 0;
		 if(batch_frame_start==0) {
		 number = 1+batch_frame_start;
		 frame_number_total = 1+batch_frame_start;}
		 else {number = batch_frame_start; frame_number_total = batch_frame_start;}
		// System.out.println("number"+number);
		 String path =  "../Object-Classification-OpenCV/output/frame_storage_directory/frame";
		 String path_l="";
		 String path_m="";
		 String path_n="";
		 int threshold = Integer.valueOf(background_threshold.trim());
		 List<MatOfPoint> contours;	
		 Set<MatOfPoint> Tree ; 	
	//	 Mat hierarchy = new Mat();
		 Point x=new Point(0,0);
		 objectlist = new ArrayList<String>();
		 
		//for lab-it.mp4 threshold is 4;
		 //for atrium.mp4 threshold is 6;
		 JFrame jframe1 = new JFrame("Processing");
		    jframe1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    JLabel vidpanel = new JLabel();
		    jframe1.setSize(frame_width,frame_height);
		    jframe1.setContentPane(vidpanel);
		    jframe1.setVisible(true);
		// Reading three frames at maximum to get background subtracted images 
		 
		 for(int i = 0;i < (frame_iteration/3);i++) {
			 
			 path_l = path+number+".jpg"; 
			// System.out.println("MAX_FRAMES_ALLOWED"+max_frames_allowed);
//			 if(number+1<max_frames_allowed) {frame_id = number+1; path_m = path+(frame_id)+".jpg"; }
//			 if(number+2<max_frames_allowed) { path_n = path+(number+2)+".jpg";}
			 
			 if(number+1<frame_iteration) {frame_id = number+1; path_m = path+(frame_id)+".jpg"; }
			 if(number+2<frame_iteration) { path_n = path+(number+2)+".jpg";}

			//Loading frame_l,frame_m,frame_n color images
			 
			 System.out.println(path_l);
			 System.out.println(path_m);
			 System.out.println(path_n);
		    Mat frame_l_Color = Highgui.imread(path_l,Highgui.CV_LOAD_IMAGE_COLOR);
			Mat frame_m_Color = Highgui.imread(path_m,Highgui.CV_LOAD_IMAGE_COLOR);
			Mat frame_n_Color = Highgui.imread(path_n,Highgui.CV_LOAD_IMAGE_COLOR);

			// Know the color channels of the images
			System.out.println(frame_l_Color.channels());
			
			
			Mat frame_l = new Mat();
			Mat frame_m = new Mat();
			Mat frame_n = new Mat();
			System.out.println("Color channels: "+frame_l_Color);
			  Imgproc.cvtColor(frame_l_Color,frame_l,Imgproc.COLOR_BGR2GRAY);
			  Imgproc.cvtColor(frame_m_Color,frame_m,Imgproc.COLOR_BGR2GRAY);
			  Imgproc.cvtColor(frame_n_Color,frame_n,Imgproc.COLOR_BGR2GRAY);
			 //Converting images to grayscale and getting the background subtracted images as background matrix
			 Mat diff1 = new Mat();
			 Mat diff2 = new Mat();
			 Mat diff = new Mat();
		     Core.absdiff(frame_m, frame_l, diff1);
		     Core.absdiff(frame_m, frame_n, diff2);
		 
		     Core.min(diff1, diff2, diff);
		     
		     for(int k=0;k<diff.rows();k++) {
		    	 
		    	 for(int j=0;j<diff.cols();j++) {
		    		 
		    		 {
		    			 double[] data = diff.get(k, j);
		    		//	 System.out.println(data[0]);
		    			 if(data[0]>threshold) {
		    				 data[0] = 255;
		    				 diff.put(k, j, data[0]);
		    			 }
		    			 else if(data[0] < threshold) {
		    				 data[0]=0;
		    				 diff.put(k,j,data[0]);
		    			 }
		    			// System.out.println(data.length);
		    		 }
		    		 
		    	 }
		     }  
		     contours= new ArrayList<MatOfPoint>();
		     Tree  = new TreeSet<MatOfPoint>(new ComparatorTree());
		     BBList = new ArrayList<Rect>();
		     
		   
		     // Find contours from the background subtracted image
		     
	         // Find contours
		     Imgproc.findContours(diff, contours, new Mat(),Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE,x);

		    // Sort the matrices accordingly to the largest size and only put the contours if above minimum threshold
		     
		     Collections.sort(contours, new Comparator<MatOfPoint>() {
		         public int compare(MatOfPoint p1, MatOfPoint p2) {
		            return Double.valueOf(Imgproc.contourArea(p1)).compareTo((double) Imgproc.contourArea(p2));
		         }
		 });
		     //Min BoundBox Threshold;
		     double min_temp = 300;
		     for(int co=0;co<contours.size();co++) {    	 
		    	 
		    	 if(Imgproc.contourArea(contours.get(co))>min_temp) {
		   //  Rect rect =  Imgproc.boundingRect(contours.get(co));
		     Tree.add(contours.get(co));
		 
		
		      }
		    	  
		    	 }
		     
		     //Iterate through all the given contour objects and extract all the rectangles and put them in bblist
		     Iterator<MatOfPoint> it = Tree.iterator();    	 
		    	while(it.hasNext()) {	
		    		Rect rect =  Imgproc.boundingRect(it.next()); 
				     BBList.add(rect);  
		    	} 

             
		   //TODO to compare the max elements 

		    	
		    	//Get Group code
		    	//If String is heuristic1 apply heuristic 1 on all the images that we get from object maps 
		    	
       String heuristic = "heuristic1";
//       for(int iter=0;iter<3;iter++) {
      objectlist   =  process_objects.process_object(BBList, frame_m_Color,frame_id);
//       System.out.println("number+iter"+(number+iter));
       for(int ob = 0;ob<objectlist.size();ob++) {
		    //	System.out.println(frame_id);
		      int group_id=  get_group.Get_Group(Integer.valueOf(objectlist.get(ob)), frame_id,heuristic);
//substitute number for frame_skipping issue
		        String property =  Object_Map.get(objectlist.get(ob));
		        property += " group_id="+group_id;
		        groupset.add(String.valueOf(group_id));
		        Object_Map.put(objectlist.get(ob), property);
		     
		        Group_Frame_Map.put(String.valueOf(group_id), Object_Frame_Map.toString());
		        System.out.println("GroupSet:"+groupset.toString());
		       
		  
		//    }
       
       
       }
      

		    //Display the image and bounding boxes.
		   
		     ImageIcon diffbuffer = new ImageIcon(Mat2BufferedImage(frame_m_Color));//Display the image in a video panel  
		    // System.out.println("Displaying diff buffer.");
	         vidpanel.setIcon(diffbuffer);  
	          vidpanel.repaint(); 
	          //Break when max_frames_allowed are reached
            if(number<frame_iteration) {
   			 number+=3;
   			
   			 } 
             System.out.println("frameLogic"+frame_iteration);
             System.out.println("framenumber"+frame_number);
            if(number == frame_iteration-1 || number == frame_iteration) {jframe1.dispose();break;}
           
		 }
		 

	 }
	 
	//Get Config Parameters from the file config.txt 
	 public static  void GetConfigParameters( ) {
	
		  HashMap<String, String> config = new read_config().readconfig();//Getting the config file object details.
		//  System.out.println(config.toString());
			for (String key : config.keySet())
		    {
				if(key.contains(video_identifier)) {
					
					path1 = config.get(key).toString().replaceAll("\\s+","");
				   // System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(end_frame_identifier)) {
					
					max_frames_allowed = Integer.valueOf(config.get(key).toString().replaceAll("\\s+",""));
				}
				
				if(key.contains(start_frame_identifier)) {
					
					start_frames_allowed = Integer.valueOf(config.get(key).toString().replaceAll("\\s+",""));
					System.out.println(key + ":" + config.get(key));
					if(start_frames_allowed > 0) {frame_number = start_frames_allowed;}
					else {frame_number = 0;}
				}
				
				if(key.contains(output_frame_path)) {	
					output_path = config.get(key).toString().replaceAll("\\s+","");
				    System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(feature_extractor_algorithm_identifier)) {	
					 feature_extractor_algorithm = config.get(key).toString().replaceAll("\\s+","");
				    System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(background_threshold_value_identifier)) {	
					background_threshold = config.get(key).toString().replaceAll("\\s+","");
				    System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(result_output_path_identifer)) {	
					result_path = config.get(key).toString().replaceAll("\\s+","");
					 Path result_abs = Paths.get(result_path.substring(3));
					  result_path = result_abs.toAbsolutePath().toString();
				    System.out.println(key + ":" + config.get(key));
				    System.out.println("result path:"+result_path);
				}
				
				if(key.contains(result_object_path_identifer)) {	
					object_path = config.get(key).toString().replaceAll("\\s+","");
					 Path result_abs = Paths.get(object_path.substring(3));
					  object_path = result_abs.toAbsolutePath().toString();
				    System.out.println(key + ":" + config.get(key));
				    System.out.println("object path:"+object_path);
				}
				
				if(key.contains(result_seed_path_identifer)) {	
					seed_path = config.get(key).toString().replaceAll("\\s+","");
					 Path result_abs = Paths.get(seed_path.substring(3));
					  seed_path = result_abs.toAbsolutePath().toString();
				    System.out.println(key + ":" + config.get(key));
				    System.out.println("seed path:"+seed_path);
				}
				
				
				if(key.contains(match_threshold_identifier)) {	
					match_threshold = config.get(key).toString().replaceAll("\\s+","");
				    System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(boundbox_match_threshold_identifier)) {	
					boundbox_match_threshold = config.get(key).toString().replaceAll("\\s+","");
				    System.out.println(key + ":" + config.get(key));
				}
				
				if(key.contains(seed_tuple_path_identifier)) {	
					seed_ascii_path = config.get(key).toString().replaceAll("\\s+","");
					  Path seed_abs = Paths.get(seed_ascii_path.substring(3));
					  seed_ascii_path = seed_abs.toAbsolutePath().toString();
				    System.out.println(key + ":" + config.get(key));
				    System.out.println("seed tuple path:"+seed_ascii_path);
				}
				
				if(key.contains(seed_object_path_identifier)) {	
					seed_object_path = config.get(key).toString().replaceAll("\\s+","");
					  Path seed_object_abs = Paths.get(seed_object_path.substring(3));
					  seed_object_path = seed_object_abs.toAbsolutePath().toString();
				    System.out.println(key + ":" + config.get(key));
				    System.out.println("seed object path:"+seed_object_path);
				}
				if(key.contains(tuple_ascii_path_identifier)) {	
					tuple_ascii_path = config.get(key).toString().replaceAll("\\s+","");
					  Path tuple_abs = Paths.get(tuple_ascii_path.substring(3));
					  tuple_ascii_path = tuple_abs.toAbsolutePath().toString();
				    System.out.println(key + ":" + config.get(key));
				    System.out.println("ascii tuple path:"+tuple_ascii_path);
				}
				if(key.contains(tuple_object_path_identifier)) {	
					tuple_object_path = config.get(key).toString().replaceAll("\\s+","");
					  Path tuples_object_abs = Paths.get(tuple_object_path.substring(3));
					  tuple_object_path = tuples_object_abs.toAbsolutePath().toString();
				    System.out.println(key + ":" + config.get(key));
				    System.out.println("ascii object path:"+tuple_object_path);
				}
		      
		    }
			//Calling Garbage collector to prevent memory leak issue
			 System.out.println("Garbage Collector Called.");
	         System.gc();
	         System.runFinalization();
	 }
	 
	 
	 
	 public static void capture_all_frames(String filepath) throws IOException{
		 //Calculating overall number of frames in a video -// Total frames 5000 or 6000 for 5 minutes just an example
		
		 total_frame = new Mat();
		 
		 VideoCapture camera = new VideoCapture(filepath);
		  camera.open(filepath);
		
		  if (!camera.isOpened()) {
	            System.out.println("Error! VideoFile can't be opened!");
	            return;
	        }
		  System.out.println("Please wait calculating total_frames.");  
		  while(true) {if(camera.read(total_frame)) {frame_height = total_frame.height();frame_width = total_frame.width();frame_count_total++; }if(!camera.read(total_frame)) {break;}}
		  System.out.println("Total frames for a video:"+frame_count_total);

	 }
	 
	 
	 
	  
	public static void capture(String filepath) throws IOException {
		
	    int frame_count = 0;
		
		 if (!Paths.get(filepath).toFile().exists()){
             System.out.println("File " + filepath + " does not exist!");
             return;
        }
		
		
	
		
		//OpenCV getting frames to read 
		VideoCapture camera = new VideoCapture(filepath);
		  camera.open(filepath);
		
		  if (!camera.isOpened()) {
	            System.out.println("Error! VideoFile can't be opened!");
	            return;
	        }
		  
		    frame = new Mat();
		  //  GrayFrame = new Mat();
		    JFrame jframe = new JFrame("Video"+n);
		    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    JLabel vidpanel = new JLabel();
		   
		    jframe.setContentPane(vidpanel);
		    jframe.setVisible(true);
		    
		   //Read all incoming frames and write it to the directories
		  while(true){
			     batch_frame_start  = frame_number;
			     System.out.println(batch_frame_start);
	            if (camera.read(frame)){//Keep on reading on all the frames if they exist.
	            	frame_number++;//Increment frame number 
	            	frame_count++;
//	                System.out.println("Frame Obtained");
//	                System.out.println("Captured Frame Width " +
//	                frame.width() + " Height " + frame.height());
	                jframe.setSize(frame.width(),frame.height());
	        
	               // Imgproc.cvtColor(frame,GrayFrame,Imgproc.COLOR_BGR2GRAY);//Convert the frame from RGB  to GRAYSCALE
	                ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));//Display the image in a video panel   
	              //  String frame_path = "/output/frame"+frame_number+".jpg";
	            //    System.out.println(frame_path);
	              Highgui.imwrite("../Object-Classification-OpenCV/output/frame_storage_directory/"+"frame"+frame_number+".jpg",frame);
	                vidpanel.setIcon(image);
	                vidpanel.repaint();
	              //  System.out.println("OK");
	               // System.out.println(frame_number);
    	        if(n<=max_process) {
	            	   if(frame_count_total-frame_iteration>=max_frames_allowed) {
	    	               if(!camera.read(frame)|| frame_count == (max_frames_allowed)) 
	    	               {
	    	            	  // System.out.println("frame_count_total"+frame_count_total);
	    	            	  // System.out.println("frame_iteration"+frame_iteration);
	    	            	   //System.out.println("frame_count"+frame_count);
	    	            	  // System.out.println("Frame_number:"+frame_number);
	    	            	   frame_iteration = frame_number;
	    	            	   batch_frame_start = frame_iteration-max_frames_allowed;
	    	            	   n++;
	    	            	   BackGroundSubtract(); 
	    	            	   wipe_group_map_exhaustive.wipe_group_map();
	    	            	   frame_count=0;
	    	            	   }//If no frame found.Stop the video or max frames reached.
	    	               }       
    	        
    	        
    	     
	               else  if(frame_count_total-frame_iteration <= max_frames_allowed) {
	            	 
	            	   System.out.println("stopped");
	            	   max_frames_allowed = frame_count_total-frame_iteration;
	            	   if(!camera.read(frame)|| frame_count == (max_frames_allowed))
	            	   {
	            		   //System.out.println("frame_count"+frame_count);
	            		   //System.out.println("Frame_number:"+frame_number);
	            		   frame_iteration = frame_number;frame_iteration = frame_number;
	            		   batch_frame_start = frame_iteration-max_frames_allowed;
	            		   n++;
	            		   BackGroundSubtract(); 
	            		   wipe_group_map_exhaustive.wipe_group_map();
	            		   frame_count=0;
	            		   }//If no frame found.Stop the video or max frames reached.
	               }
	            	   
	            	  	if(frame_iteration >=frame_count_total) {break;} 
    	        }
    	   
    	        
	          if(n==max_process) {break;} 
	             //if {camera.release();break;}    	
	            }
	           
	        }
		  //Calling Garbage collector to remove memory leak issue
		     System.out.println("Garbage Collector Called.");
	         System.gc();
	         System.runFinalization();
		
	}

//	static {
//		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
//	}

   public static void main( String[] args ) throws IOException
   { 
	  System.out.println(System.getProperty("java.library.path"));
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	//  nu.pattern.OpenCV.loadLibrary();
	  GetConfigParameters();

	  capture_all_frames(path1);
		
	  capture(path1);
	  System.out.println("Seed Generated");
	 
	//seed_generation.get_seed();
  

   }
}

//ComparatorTree sorts all the contours given in an contourlist according to custom comparator parameter contourArea and provides contours in a sorted manner according to area size.
class ComparatorTree implements Comparator<MatOfPoint>{
	 
    

	@Override
	public int compare(MatOfPoint o1, MatOfPoint o2) {
		// TODO Auto-generated method stub
		return Double.valueOf(Imgproc.contourArea(o1)).compareTo((Imgproc.contourArea(o2)));
	}
     
}


