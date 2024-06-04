import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import sun.rmi.runtime.Log;

/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
public class sift_detector {
// Call system native library
   static {  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
   
   
   // Sift_match between two images
	 
	public static float sift_match(String path1,String path2) {
         float match;		
		  Mat object_color = Highgui.imread(path1,Highgui.CV_LOAD_IMAGE_COLOR);
		    Mat img2 = Highgui.imread(path2, Highgui.CV_LOAD_IMAGE_COLOR);
	        Mat object1 = new Mat();
		    Mat object = new Mat();
		    if(object_color.channels()==3) {
		    Imgproc.cvtColor(object_color,object,Imgproc.COLOR_BGR2GRAY);}
		    MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
	     	FeatureDetector featuredetector = FeatureDetector.create(FeatureDetector.SIFT);
	     
	     //	System.out.println(path1);
	     //	System.out.println(path2);
	     	
		    featuredetector.detect(object, objectKeyPoints);
	        KeyPoint[] keypoints = objectKeyPoints.toArray();
	      //  System.out.println(("keypoints:"+keypoints.toString()));
	        
	        //Descriptors of Image 1
	        MatOfKeyPoint descriptors1 = new MatOfKeyPoint();
	        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
	     //   System.out.println("Computing descriptors...");
	        descriptorExtractor.compute(object, objectKeyPoints,descriptors1);
     // System.out.println("Descriptors:"+descriptors1.rows());
	       // System.out.println("Descriptor1: " + descriptors1.dump());  
	      
  
     
	        if(img2.channels()==3) {
	        Imgproc.cvtColor(img2,object1,Imgproc.COLOR_BGR2GRAY);}
	        //Descriptors of image 2 
	        Mat descriptors2 = new Mat();
	        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
	       // KeyPoint[] keypoints = objectKeyPoints.toArray();
	        featuredetector.detect(object1, keypoints2);
	        descriptorExtractor.compute(object1, keypoints2, descriptors2);
	     //   System.out.println("Descriptor2: " + descriptors2.dump());       
	        // match these two keypoints sets
	  
	        //Compute Descriptors for both images seperately and match them using descriptormatcher   
   if(!descriptors1.empty()  && !descriptors2.empty()) {
    // MatOfDMatch matches = new MatOfDMatch();
     List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
	   DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
	  matcher.knnMatch(descriptors1, descriptors2, (List<MatOfDMatch>) matches, 2);
	 //  matcher.match(descriptors1, descriptors2 ,(MatOfDMatch) matches);
	   
	
	//  System.out.println("Matches size are:"+matches.size()+ "\tfor" +path1+","+path2);
	  
	    LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();
	  
	         float nndrRatio = 1.5f;
	  
 	         for (int i = 0; i < matches.size(); i++) {
	             MatOfDMatch matofDMatch = matches.get(i);
	             DMatch[] dmatcharray = matofDMatch.toArray();
	             DMatch m1 = dmatcharray[0];
	             DMatch m2 = dmatcharray[1];
	  
	         //    System.out.println("Distance m1:"+m1.toString());
	          //  System.out.println("Distance m2:"+m2.toString());
	             if (m2.distance <= m1.distance * nndrRatio) {
	                 goodMatchesList.addLast(m2);
	  
	             }
	  	   }
 	         
 	        System.out.println("Garbage Collector Called.");
 	       System.gc();
 	       System.runFinalization();
 	       
 	        
 	         
 	         if(goodMatchesList.size()>0) {
 	        	// System.out.println("keypoints length:"+keypoints.length);
 	 	     //   System.out.println("GoodMatches length:"+matches.size());
 	        	 match =  (1-(float)(Double.valueOf(goodMatchesList.size())/Double.valueOf(keypoints.length)));
 	        	 System.out.println("Sift Match:"+match);
 	        	 return match;
 	        	 }
 	         else {match = 1;
 	        // System.out.println("Matches F:"+match);return match;
 	         }
	  
	  
		//Call Garbage collector
	
	  
	 
	}
return 1;
   }
	
	
//	public static void main(String[] args) {
//		
//		// TODO Auto-generated method stub
//		
//		  String path2 = "";
//		  int ob1=0;
//		 
//		 String object_path =  "../OpenCV2-Test/output/object_storage_directory/Object";
//		 String object_path1 =  "output/object_storage_directory/";
//		 Path path = Paths.get(object_path1).toAbsolutePath();
//		 System.out.println(path.toString());
//		 for(int ob=0;ob<138;ob++) {
//			ob1=ob+1;
//			while(ob1<138) { path2 = object_path + (ob1)+".jpg";break;} 
//			String path1 = object_path + ob+".jpg";
//		//	System.out.println(path1);
//			//System.out.println(path2);
//		    Mat object_color = Highgui.imread(path1,Highgui.CV_LOAD_IMAGE_COLOR);
//		    Mat img2 = Highgui.imread(path2, Highgui.CV_LOAD_IMAGE_COLOR);
//	        Mat object1 = new Mat();
//		    Mat object = new Mat();
//		    if(object_color.channels()==3) {
//		    Imgproc.cvtColor(object_color,object,Imgproc.COLOR_BGR2GRAY);}
//		    MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
//	     	FeatureDetector featuredetector = FeatureDetector.create(FeatureDetector.SIFT);
//	     
//		    featuredetector.detect(object, objectKeyPoints);
//	        KeyPoint[] keypoints = objectKeyPoints.toArray();
//	      //  System.out.println(("keypoints:"+keypoints.toString()));
//	        
//	        MatOfKeyPoint descriptors1 = new MatOfKeyPoint();
//	        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
//	        System.out.println("Computing descriptors...");
//	        descriptorExtractor.compute(object, objectKeyPoints,descriptors1);
//       // System.out.println("Descriptors:"+descriptors1.rows());
//	       // System.out.println("Descriptor1: " + descriptors1.dump());  
//	      
//    
//       
//	        if(img2.channels()==3) {
//	        Imgproc.cvtColor(img2,object1,Imgproc.COLOR_BGR2GRAY);}
//	        Mat descriptors2 = new Mat();
//	        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
//	       // KeyPoint[] keypoints = objectKeyPoints.toArray();
//	        featuredetector.detect(object1, keypoints2);
//	        descriptorExtractor.compute(object1, keypoints2, descriptors2);
//	     //   System.out.println("Descriptor2: " + descriptors2.dump());       
//	        // match these two keypoints sets
//	     
//     if(!descriptors1.empty()  && !descriptors2.empty()) {
//      // MatOfDMatch matches = new MatOfDMatch();
//       List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
//	   DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
//	  matcher.knnMatch(descriptors1, descriptors2, (List<MatOfDMatch>) matches, 2);
//	 //  matcher.match(descriptors1, descriptors2 ,(MatOfDMatch) matches);
//	   
//	
//	  System.out.println("Matches size are:"+matches.size()+ "\tfor" +ob+","+ob1);
//	   
//	   
////	   System.out.println("Calculating good match list...");
////       LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();
////
////       float nndrRatio = 1.5f;
////
////       for (int i = 0; i < matches.size(); i++) {
////           MatOfDMatch matofDMatch = matches.get(i);
////           DMatch[] dmatcharray = matofDMatch.toArray();
////           DMatch m1 = dmatcharray[0];
////           DMatch m2 = dmatcharray[1];
////
////          // System.out.println("Distance m1:"+m1.toString());
////          //System.out.println("Distance m2:"+m2.toString());
////           if (m2.distance <= m1.distance * nndrRatio) {
////               goodMatchesList.addLast(m2);
////
////           }
////	   }
////       if (goodMatchesList.size() > 30) {
////           System.out.println("Object Found!!! for:"+ob1+"and"+ob);}
////       System.out.println(goodMatchesList.size());
////       System.out.println(ob+","+ob1);
//
//       }
//
//		 } 
//	//	 sift_match();
//		 System.out.println("Garbage Collector Called.");
//         System.gc();
//         System.runFinalization();
//	}
	
	


}
