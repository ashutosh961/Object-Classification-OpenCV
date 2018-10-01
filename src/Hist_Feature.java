import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * 
 */

/**
 * @author Ashutosh,Aditya
 *
 */
public class Hist_Feature {

static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
  static String RGB_matrix  = "";	

	public static String hist_features(String path1) {
		
		  Mat Image1 = Highgui.imread(path1,Highgui.CV_LOAD_IMAGE_COLOR);		


		  Mat hsvRef = new Mat(Image1.height(), Image1.width(), CvType.CV_8UC3, new Scalar(4));
		  

		      /// Convert to HSV/	
		  if(!Image1.empty()) {
		  Imgproc.cvtColor(Image1, hsvRef, Imgproc.COLOR_BGR2RGB);
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
			   
			   
			 String[] red_matrix =  process_hist_matrix.process_matrix(RHist);
			 String[] green_matrix =  process_hist_matrix.process_matrix(GHist);
			 String[] blue_matrix =  process_hist_matrix.process_matrix(BHist);
			 
			 List<String> Red_List = Arrays.asList(red_matrix); 
			 List<String> Green_List = Arrays.asList(green_matrix); 
			 List<String> Blue_List = Arrays.asList(blue_matrix); 
			 
			 RGB_matrix = Red_List.toString()+ Green_List.toString() + Blue_List.toString();
			 int length = Red_List.size()+ Green_List.size()+Blue_List.size();
			 
			 System.out.println("Hist_Size:"+length);
			 
//			 for(int i=0;i<Red_List.size();i++) {
//				 
//				 RGB_matrix+= Red_List.get(i).toString()+","+ Green_List.get(i).toString()+","+ Blue_List.get(i).toString()+";";	
//				 
//				 if(i==Red_List.size()-1) { RGB_matrix+= Red_List.get(i)+","+ Green_List.get(i)+","+ Blue_List.get(i);}
//			 }
//			 
		    
			 System.out.println("RGB MAT:"+RGB_matrix.toString());
			}
		return RGB_matrix;
		}
	
	
	
}
