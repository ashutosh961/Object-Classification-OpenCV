import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 */

/**
 * @author Ashutosh
 *
 */
public class wipe_group_map_exhaustive {
	
	public static Multimap<String,String> Group_Frame_Map_edited =  LinkedHashMultimap.create();
	static ReadWriteLock lock = new ReentrantReadWriteLock();
	static Lock writeLock = lock.writeLock();
	static int lock1 =0;
	public static void wipe_group_map() {
		 

		
		System.out.println("Wiping Group Frame Map:");
	

		Iterator<?> it2 = read_gray.groupset.iterator();
		
		System.out.println("After wiping new map is: ");
		
		while(it2.hasNext()) {
			
			 String compare = it2.next().toString();
        	 System.out.println("compare:"+compare);
        	 int occurrences = read_gray.Group_Frame_Map.get(compare).size();
        	 
        	   try { 	
   	        	 
        		  read_gray.Group_Frame_Map.asMap().get(compare).removeAll((Arrays.asList(read_gray.Group_Frame_Map.asMap().get(compare).toArray()).subList(0, (occurrences/2))));

               }
        	   catch(Exception e) {System.out.println("Group map wipe problem");}
        	 occurrences = read_gray.Group_Frame_Map.get(compare).size();
        	 System.out.println("occurrences:"+occurrences);
			
		}

	}

}
