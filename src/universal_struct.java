import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author Ashutosh
 *
 */

//Universal struct is an object shared by all the class files.
public class universal_struct {

	
 public	static int group_count = 0;
 public static int comparison_count = 1;
 public	static String KeyType  = "double";
 public	static String ValueType = "double";
 public	 static String object_path = "";
 public	static String bounding_box = "";
 public static int group_id = 1;
 public static int common_group = 1;
	
 public static Map<String,String> Object_Frame_Map;
public static Map<String,Map<String,String>> Group_Object_Map = new LinkedHashMap<String,Map<String,String>>(); 
	
	
}
