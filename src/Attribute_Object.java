import java.io.Serializable;

/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
public class Attribute_Object implements Serializable {
	
	
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  String Object_Name = "";
	public  String ObjectType = "";
	public  String objectCategory ;
	public  String objectDataType = "";
	public  String SimilarityMethods;
	public  String SimilarityThreshold;
	public  String AttributeValue;
	public  String signature_method="";	
	
	
	public void SetObjectType(String objecttype) {
		
		ObjectType = objecttype;
		
	}
	
	public void SetObjectName(String objectname) {
		
		Object_Name = objectname;
		}
	public void SetObjectcategory(String objectcategory) {
	
		objectCategory = objectcategory;
		}
	public void similaritymethods(String similaritymethods) {
	
		SimilarityMethods = similaritymethods;
		}
	
	public void objectdatatype(String objectdatatype) {
		
		objectDataType = objectdatatype;
		}
	

}
