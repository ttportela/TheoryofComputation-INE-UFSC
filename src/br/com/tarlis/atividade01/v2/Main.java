/**
 * Words 
 */
package br.com.tarlis.atividade01.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
		
//		System.out.println("01011010".matches("\b(010|11)\b"));

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
			match();
			
			System.out.println(found? "S" : "N");
		}
		
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)/1000));
		
		return;
	}
	
	private static void match() {
		// Build Regex for L1:
		String R = "^(";
		int i = 0;
		for (; i < (L1.size() - 1); i++) {
			R += L1.get(i) + "|";
		}
		R += L1.get(i) + ")+$";
		
		// Operates . AND * in L2:
		concat2("",R,0);
		
	}
	
	private static void concat2(String w, String R, int size) {
		if (found || size >= MAX_IT) return;
		
		for (String u : L2) {
			if ((w + u).matches(R)) {
				found = true; 
				return; 
			} else 
				concat2((w + u),R,size+1);	
		}
	}

	static boolean found = false;
	static int MAX_IT = 7;
	static List<String> L1; 
	static List<String> L2;

}
