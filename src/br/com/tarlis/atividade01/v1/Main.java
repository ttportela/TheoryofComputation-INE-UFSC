/**
 * Words 
 */
package br.com.tarlis.atividade01.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tarlis Tortelli Portela
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {

		InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(ir);
		
		int n1, n2;
		String input;
		
		long startTime = System.nanoTime();
		
		while (in.ready() && (input = in.readLine()) != null) {
			
			// Input size groups:
			n1 = Integer.parseInt(input.split(" ")[0]);
			n2 = Integer.parseInt(input.split(" ")[1]);

			// Input of words 
			L1 = new ArrayList<String>();
			for (int i = 0; i < n1; i++) {
				L1.add(in.readLine().trim());
			}
			L2 = new ArrayList<String>();
			for (int i = 0; i < n2; i++) {
				L2.add(in.readLine().trim());
			}

			found = false;
			concat("","",0);
			
			System.out.println(found? "S" : "N");
		}
		
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)/1000));
		
		return;
	}
	
	private static void concat(String wl1, String wl2, int size) {
		if (found || size >= MAX_IT) return;
		for (String w : L1) {
			if ((wl1 + w).equals(wl2)) { 
				found = true; return; 
			} else {
				concat((wl1 + w), wl2, size+1);
			}	
		}
			
		for (String w : L2) {
			if ((wl2 + w).equals(wl1)) { 
				found = true; return; 
			} else {
				concat(wl1, (wl2 + w), size+1);
			}
		}
	}

	static boolean found = false;
	static int MAX_IT = 7;
	static List<String> L1; 
	static List<String> L2;

}
