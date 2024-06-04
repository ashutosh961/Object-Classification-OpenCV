import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
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
public class Sift_Feature {

	 //Load System Library 
	   static {  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	   
	   
	  
		 
		public static Mat sift_features(String path1) {
		  //Reading Mat images for the given path 
			
			  Mat object_color = Highgui.imread(path1,Highgui.CV_LOAD_IMAGE_COLOR);
			   
		        
			    Mat object = new Mat();
			    if(object_color.channels()==3) {
			    //	If color of channels is 3 then convert to grayscale image.
			    Imgproc.cvtColor(object_color,object,Imgproc.COLOR_BGR2GRAY);}
			    MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
		     	FeatureDetector featuredetector = FeatureDetector.create(FeatureDetector.SIFT);
		     	
		     //Use feature detector sift and compute objectkeypoints.
			    featuredetector.detect(object, objectKeyPoints);
		        KeyPoint[] keypoints = objectKeyPoints.toArray();
		      //  System.out.println(("keypoints:"+keypoints.toString()));
		        
		        //from objectkeypoints compute sift descriptors 
		        MatOfKeyPoint descriptors1 = new MatOfKeyPoint();
		        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		        System.out.println("Computing descriptors...");
		        descriptorExtractor.compute(object, objectKeyPoints,descriptors1);
	           // System.out.println("Descriptors:"+descriptors1.rows());
		     //   System.out.println("Descriptor1: " + descriptors1.dump());  
		      
	  
     //Call System Garbage Collector.
			
//		  System.out.println("Garbage Collector Called.");
//	      System.gc();
//	      System.runFinalization();
		        
		        //return descriptor
		        System.out.println("Descriptor size:"+descriptors1.size());
		return descriptors1;
		  
		  
		
	   }
	
	
	
}
