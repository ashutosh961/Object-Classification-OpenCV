import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
//Process Normal hist Matrix
public class process_hist_matrix {
	
	
	static String[] FinalProcessedMatrix; 
	
	public static String[] process_matrix(Mat rHist ){
		
		
	     String[] MatrixConvert = rHist.dump().toString().split(";");

		return MatrixConvert;
	
		
	}

}
