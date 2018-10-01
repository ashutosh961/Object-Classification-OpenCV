import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
public class Hist_detector {

	/**
	 * @param args
	 */
	
	static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	
	public static double hist_match(String path1,String path2) {
		
		  Mat Image1 = Highgui.imread(path1,Highgui.CV_LOAD_IMAGE_COLOR);		
		  Mat Image2 = Highgui.imread(path2,Highgui.CV_LOAD_IMAGE_COLOR);

		  Mat hsvRef = new Mat(Image1.height(), Image1.width(), CvType.CV_8UC3, new Scalar(4));
		  Mat hsvSource = new Mat(Image2.height(), Image2.width(), CvType.CV_8UC3, new Scalar(4));

		      /// Convert to HSV/	
		  if(!Image1.empty()) {
		  Imgproc.cvtColor(Image1, hsvRef, Imgproc.COLOR_BGR2RGB);
		  }
		  if(!Image2.empty()) {
		  Imgproc.cvtColor(Image2, hsvSource, Imgproc.COLOR_BGR2RGB);
		  }

		  
		  MatOfInt histsize = new MatOfInt(256);
		 MatOfFloat rangesHist =  new MatOfFloat(0.0f, 255.0f); 
		  Mat RHist = new Mat();
		  Mat GHist = new Mat();
		  Mat BHist = new Mat();
		  Mat RHist1 = new Mat();
		  Mat GHist1 = new Mat();
		  Mat BHist1 = new Mat();
		  MatOfInt channelsHist = new MatOfInt(0);   
		  List<Mat> Rgb_Planes_Image1 = new ArrayList<Mat>();
	      List<Mat> Rgb_Planes_Image2 = new ArrayList<Mat>();
		  
		 Core.split(hsvRef, Rgb_Planes_Image1);
		 Core.split(hsvSource, Rgb_Planes_Image2);
		  
		// System.out.println(Rgb_Planes_Image1.get(0).dump());
		 List<Mat> FeatureListHist = new ArrayList<>();
		 List<Mat> FeatureListHist1 = new ArrayList<>();
		if(!Rgb_Planes_Image1.isEmpty()) {
			   List<Mat> RList = new ArrayList<>();
		        RList.add(Rgb_Planes_Image1.get(0));
		        List<Mat> GList = new ArrayList<>();
		        GList.add(Rgb_Planes_Image1.get(1));
		        List<Mat> BList = new ArrayList<>();
		        BList.add(Rgb_Planes_Image1.get(2));
		        
		 Imgproc.calcHist(RList,channelsHist,new Mat(),RHist,histsize,rangesHist,false);
		 Imgproc.calcHist(GList,channelsHist,new Mat(),GHist,histsize,rangesHist,false);
		 Imgproc.calcHist(BList,channelsHist,new Mat(),BHist,histsize,rangesHist,false);
		  
		    Core.normalize(RHist,RHist,0,1,Core.NORM_MINMAX,-1,new Mat()); 
		    Core.normalize(GHist,GHist,0,1,Core.NORM_MINMAX,-1,new Mat()); 
		    Core.normalize(BHist,BHist,0,1,Core.NORM_MINMAX,-1,new Mat()); 
		
		    
		    
		   
			   FeatureListHist.add(RHist);
			   FeatureListHist.add(GHist);
			   FeatureListHist.add(BHist);
//			   System.out.println(RHist.dump());
//			   System.out.println(GHist.dump());
//			   System.out.println(BHist.dump());
		    
		}
		
		
	   if(!Rgb_Planes_Image2.isEmpty()) {
		   
		   List<Mat> RList1 = new ArrayList<>();
	        RList1.add(Rgb_Planes_Image2.get(0));
	        List<Mat> GList1 = new ArrayList<>();
	        GList1.add(Rgb_Planes_Image2.get(1));
	        List<Mat> BList1 = new ArrayList<>();
	        BList1.add(Rgb_Planes_Image2.get(2));
	        
		   Imgproc.calcHist(RList1,channelsHist,new Mat(),RHist1,histsize,rangesHist,false);
		   Imgproc.calcHist(GList1,channelsHist,new Mat(),GHist1,histsize,rangesHist,false);
		   Imgproc.calcHist(BList1,channelsHist,new Mat(),BHist1,histsize,rangesHist,false);
			  
			 Core.normalize(RHist1,RHist1,0,1,Core.NORM_MINMAX,-1,new Mat()); 
			  Core.normalize(GHist1,GHist1,0,1,Core.NORM_MINMAX,-1,new Mat()); 
			  Core.normalize(BHist1,BHist1,0,1,Core.NORM_MINMAX,-1,new Mat()); 

	   
	   FeatureListHist1.add(RHist1);
	   FeatureListHist1.add(GHist1);
	   FeatureListHist1.add(BHist1);
	//   System.out.println(FeatureListHist1.get(0).size());
//	   
//	   System.out.println(RHist1.dump());
//	   System.out.println(GHist1.dump());
//	   System.out.println(BHist1.dump());
	   }	
	if(!FeatureListHist.isEmpty() && !FeatureListHist1.isEmpty()) {	
	double sc_red =  Imgproc.compareHist(FeatureListHist.get(0),FeatureListHist1.get(0),Imgproc.CV_COMP_CORREL);
	double sc_green =  Imgproc.compareHist(FeatureListHist.get(1),FeatureListHist1.get(1),Imgproc.CV_COMP_CORREL);
	double sc_blue =  Imgproc.compareHist(FeatureListHist.get(2),FeatureListHist1.get(2),Imgproc.CV_COMP_CORREL);

	
	
	double sc =  0.1140*sc_blue + 0.2989*sc_red + 0.5870*sc_green;
	
  //System.out.println("HIST MATCH is: "+sc+" for"+path1+" and"+path2);
  
  return sc;
	
	}
	
		 System.out.println("HIST ALGORITHMS.");
		    return 0; 
	}
	
	
	//Second Order Histogram Algorithm, Uncomment if needed
	
//	
//	@SuppressWarnings("unchecked")
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		 
//		  String path2 = "";
//		 String object_path =  "../OpenCV2-Test/output/object_storage_directory/Object";
//		
//	
//		 for(int ob=0;ob<138;ob++) {
//		
//			 int ob1 = ob+1;
//			 while(ob1<138) { path2 = object_path + (ob1)+".jpg";break;} 
//				String path1 = object_path + ob+".jpg";
//	        // match these two keypoints sets
//		
//		  Mat Image1 = Highgui.imread(path1,Highgui.CV_LOAD_IMAGE_COLOR);		
//		  Mat Image2 = Highgui.imread(path2,Highgui.CV_LOAD_IMAGE_COLOR);
//
//		  Mat hsvRef = new Mat(Image1.height(), Image1.width(), CvType.CV_8UC3, new Scalar(4));
//		  Mat hsvSource = new Mat(Image2.height(), Image2.width(), CvType.CV_8UC3, new Scalar(4));
//
//		      /// Convert to HSV/	
//		  if(!Image1.empty()) {
//		  Imgproc.cvtColor(Image1, hsvRef, Imgproc.COLOR_BGR2RGB);}
//		  if(!Image2.empty()) {
//		  Imgproc.cvtColor(Image2, hsvSource, Imgproc.COLOR_BGR2RGB);}
//
//		  
//		  MatOfInt histsize = new MatOfInt(256);
//		 MatOfFloat rangesHist =  new MatOfFloat(0.0f, 255.0f); 
//		  Mat RHist = new Mat();
//		  Mat GHist = new Mat();
//		  Mat BHist = new Mat();
//		  Mat RHist1 = new Mat();
//		  Mat GHist1 = new Mat();
//		  Mat BHist1 = new Mat();
//		  MatOfInt channelsHist = new MatOfInt(0);   
//		  List<Mat> Rgb_Planes_Image1 = new ArrayList<Mat>();
//	      List<Mat> Rgb_Planes_Image2 = new ArrayList<Mat>();
//		  
//		 Core.split(hsvRef, Rgb_Planes_Image1);
//		 Core.split(hsvSource, Rgb_Planes_Image2);
//		  
//		// System.out.println(Rgb_Planes_Image1.get(0).dump());
//		 List<Mat> FeatureListHist = new ArrayList<>();
//		 List<Mat> FeatureListHist1 = new ArrayList<>();
//		if(!Rgb_Planes_Image1.isEmpty()) {
//			   List<Mat> RList = new ArrayList<>();
//		        RList.add(Rgb_Planes_Image1.get(0));
//		        List<Mat> GList = new ArrayList<>();
//		        GList.add(Rgb_Planes_Image1.get(1));
//		        List<Mat> BList = new ArrayList<>();
//		        BList.add(Rgb_Planes_Image1.get(2));
//		        
//		 Imgproc.calcHist(RList,channelsHist,new Mat(),RHist,histsize,rangesHist,false);
//		 Imgproc.calcHist(GList,channelsHist,new Mat(),GHist,histsize,rangesHist,false);
//		 Imgproc.calcHist(BList,channelsHist,new Mat(),BHist,histsize,rangesHist,false);
//		  
//		    Core.normalize(RHist,RHist,0,1,Core.NORM_MINMAX,-1,new Mat()); 
//		    Core.normalize(GHist,GHist,0,1,Core.NORM_MINMAX,-1,new Mat()); 
//		    Core.normalize(BHist,BHist,0,1,Core.NORM_MINMAX,-1,new Mat()); 
//		
//		   
//			   FeatureListHist.add(RHist);
//			   FeatureListHist.add(GHist);
//			   FeatureListHist.add(BHist);
//			 //  System.out.println(FeatureListHist.get(0).dump());
//		    
//		}
//		
//		
//	   if(!Rgb_Planes_Image2.isEmpty()) {
//		   
//		   List<Mat> RList1 = new ArrayList<>();
//	        RList1.add(Rgb_Planes_Image2.get(0));
//	        List<Mat> GList1 = new ArrayList<>();
//	        GList1.add(Rgb_Planes_Image2.get(1));
//	        List<Mat> BList1 = new ArrayList<>();
//	        BList1.add(Rgb_Planes_Image2.get(2));
//	        
//		   Imgproc.calcHist(RList1,channelsHist,new Mat(),RHist1,histsize,rangesHist,false);
//		   Imgproc.calcHist(GList1,channelsHist,new Mat(),GHist1,histsize,rangesHist,false);
//		   Imgproc.calcHist(BList1,channelsHist,new Mat(),BHist1,histsize,rangesHist,false);
//			  
//			 Core.normalize(RHist1,RHist1,0,1,Core.NORM_MINMAX,-1,new Mat()); 
//			  Core.normalize(GHist1,GHist1,0,1,Core.NORM_MINMAX,-1,new Mat()); 
//			  Core.normalize(BHist1,BHist1,0,1,Core.NORM_MINMAX,-1,new Mat()); 
//
//	   
//	   FeatureListHist1.add(RHist1);
//	   FeatureListHist1.add(GHist1);
//	   FeatureListHist1.add(BHist1);
//	//   System.out.println(FeatureListHist1.get(0).size());
//	   }	
//	if(!FeatureListHist.isEmpty() && !FeatureListHist1.isEmpty()) {	
//	double sc_red =  Imgproc.compareHist(FeatureListHist.get(0),FeatureListHist1.get(0),Imgproc.CV_COMP_CORREL);
//	double sc_green =  Imgproc.compareHist(FeatureListHist.get(1),FeatureListHist1.get(1),Imgproc.CV_COMP_CORREL);
//	double sc_blue =  Imgproc.compareHist(FeatureListHist.get(2),FeatureListHist1.get(2),Imgproc.CV_COMP_CORREL);
//
//	
//	
//	double sc =  0.1140*sc_blue + 0.2989*sc_red + 0.5870*sc_green;
//	
//    System.out.println("sc is: "+sc+" for"+ob+" and"+ob1);}
//	
//		 }
//		 System.out.println("Garbage Collector Called.");
//         System.gc();
//         System.runFinalization();
//	}
//	
//	 System.out.println("Garbage Collector Called.");
//     System.gc();
//     System.runFinalization();

}
