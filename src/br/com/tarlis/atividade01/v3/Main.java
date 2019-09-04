/**
 * Words 
 */
package br.com.tarlis.atividade01.v3;

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

			boolean match = false;
			// Only in case L1 or L2 is empty:
			if (!L1.isEmpty() && !L2.isEmpty())
				match = AFN("", "", 0);
			
			System.out.println(match? "S" : "N");
		}
		
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)/1000));
		
		return;
	}

	private static boolean AFN(String w, String u, int pos) {
		// Stop if combinations reach maximum input size (reject state)
		if (pos > MAX_POS) return false;
		
		boolean solved = false;
		// In these two cases, tries every possible subsequent concatenation:
		if (w.length()-1 < pos) {
			for (String w2 : L1) {
				solved = solved || AFN((w + w2), u, pos); // Concatenation in input for M1
			}
		} else if (u.length()-1 < pos) {
			for (String u2 : L2) {
				solved = solved || AFN(w, (u + u2), pos); // Concatenation in input for M2
			}
		} 
		// No concatenation needed yet:
		else {
			// If input differs (reject state, LEAF case 2)
			if (w.charAt(pos) != u.charAt(pos)) return false; // this branch dies
			// Concatenations at "pos" have same sequence of input (accept state, LEAF case 3)
			else {
				// Concatenations end in same size with same sequence of input
				if (w.length()-1 == pos && u.length()-1 == pos) {
					return true; 
				} 
				// Otherwise, continues:
				else {
					solved = solved || AFN(w, u, pos+1); // pos+1 represents the next symbol input
				}
			}
			
		}
		
		// If none of the leaf cases:
		return solved;
	}

	static int MAX_POS = 40;
	static List<String> L1; 
	static List<String> L2;

}
