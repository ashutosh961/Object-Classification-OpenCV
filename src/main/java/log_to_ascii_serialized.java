import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.json.simple.JSONObject;

import com.sun.jndi.cosnaming.IiopUrl.Address;

/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
public class log_to_ascii_serialized implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Mapping seed tuples to JSON objects and storing it in bin file 
	//private static final String FILENAME = "C:\\Users\\Ashutosh\\eclipse-workspace\\OpenCV2-Test\\output\\Relation_object_serialized.bin";
	private static final String FILENAME = read_gray.seed_object_path;
	public static int  seed_count = 0;
	public static void serializeObject(JSONObject object) {

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		int seed_count = 0 ;
		try {

			fout = new FileOutputStream(FILENAME,true);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(object);
			seed_count ++;
			System.out.println("serial object written to file ");
			oos.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try{
			if(seed_count>=1){
				
				  ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream(FILENAME, true)) {
			            protected void writeStreamHeader() throws IOException {
			                reset();
			            }
			        };	
				
			    	os2.writeObject(object);
			    	os2.close();
					
		}
			}
		catch(Exception e ) 
		{
			
		}
			
		
		
		finally {

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public static Address deserializeAddress() {

		Address address = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(FILENAME);
			ois = new ObjectInputStream(fin);
			JSONObject object =(JSONObject) ois.readObject();
			System.out.println(object.toJSONString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return address;

	}
	
	
}
