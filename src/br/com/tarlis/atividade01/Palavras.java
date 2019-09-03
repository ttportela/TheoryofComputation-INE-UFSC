/**
 * Words

 */
package br.com.tarlis.atividade01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tarlis Tortelli Portela
 *
 */
public class Palavras {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n1, n2;
		Scanner scn = new Scanner(System.in);
		String input;
		
		while ((input = scn.nextLine()) != null) {
			// Input size groups:
			System.out.println(input);
			n1 = Integer.parseInt(input.split(" ")[0]);
			n2 = Integer.parseInt(input.split(" ")[1]);
			
			// Input of words 
			List<String> words1 = new ArrayList<String>();
			for (int i = 0; i < n1; i++) {
				words1.add(scn.nextLine().trim());
			}
			List<String> words2 = new ArrayList<String>();
			for (int i = 0; i < n2; i++) {
				words2.add(scn.nextLine().trim());
			}
			
			System.out.println(words(words1, words2)? "S" : "N");
		}
		
	}

	/**
	 * @param words1
	 * @param words2
	 */
	private static boolean words(List<String> words1, List<String> words2) {
		boolean yes = false;
		
		return yes;
	}

}
