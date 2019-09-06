/**
 * Solução B - Problema - Palavras | URI Online Judge | 1231
 * Copyright (C) 2019  Tarlis Portela <tarlis@tarlis.com.br>
 * 
 * Disciplina: Teoria da Computação - Atividade 01
 * Professor: Maicon Rafael Zatelli
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package br.com.tarlis.atividade01.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Solução B - Problema - Palavras | URI Online Judge | 1231
 * Intersecção de Linguagens (Com Expressão Regular) 
 * 
 * Disciplina: Teoria da Computação - Atividade 01
 * Professor: Maicon Rafael Zatelli
 * 
 * @author Tarlis Tortelli Portela
 *
 */
public class Main {
	
	/**
	 * Método principal, faz a leitura da entrada e chama o método da solução.
	 * 
	 * @param args Nenhum requerido.
	 * @throws IOException Erro de entrada.
	 * @throws NumberFormatException Erro na entrada de N1 ou N2.
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {

		// Ferramentas de leitura da entrada:
		InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(ir);
		
        // Variáveis úteis:
		int n1, n2;
		String input;
		
		// Contador de Tempo (Comentado)
//		long startTime = System.nanoTime();
		
		// Enquanto houver entrada:
		while (in.ready() && (input = in.readLine()) != null) {
			
			// Entrada de N1 e N2:
			n1 = Integer.parseInt(input.split(" ")[0]);
			n2 = Integer.parseInt(input.split(" ")[1]);

			// Entrada dos grupos de palavras:
			// L1 - Linguagem 1
			L1 = new ArrayList<String>();
			for (int i = 0; i < n1; i++) {
				L1.add(in.readLine().trim());
			}

			// L1 - Linguagem 2
			L2 = new ArrayList<String>();
			for (int i = 0; i < n2; i++) {
				L2.add(in.readLine().trim());
			}

			// Flag que demarca a solução
			found = false;
			match();
			
			// Impressão do resultado:
			System.out.println(found? "S" : "N");
		}
		
		// Contador de Tempo (Comentado)
//		long endTime   = System.nanoTime();
//		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}
	
	/**
	 *  Procura por uma concatenação de L2* que combine com a expressão regular de L1.
	 */
	private static void match() {
		// Cria uma expressão regular (Regex) para L1:
		String R = "^(";
		int i = 0;
		for (; i < (L1.size() - 1); i++) {
			R += L1.get(i) + "|";
		}
		R += L1.get(i) + ")+$";
		// Para o exemplo de entrada isso resulta em: R = ^(010|11)+$
//		System.out.println(R);
		
		// Faz concatenações sucessivas em L2 e compara com R
		concat2("",R,0);
		
	}
	
	/**
	 * Faz L2* e compara com a expressão regular R.
	 * 
	 * @param w Palavra de L1*.
	 * @param R Regex de L2.
	 * @param it Quantidade Máxima de iterações
	 */
	private static void concat2(String w, String R, int it) {
		if (found || it >= MAX_IT || w.length() > MAX_SIZE) return;
		
		for (String u : L2) {
			if ((w + u).matches(R)) {
				found = true; 
				return; 
			} else 
				concat2((w + u),R,it+1);	
		}
	}

	// Variáveis globais auxiliares:
	static boolean found = false;
	static int MAX_IT = 7;
	static int MAX_SIZE = 40; // Tamanho limite da palavra
	static List<String> L1; 
	static List<String> L2;

}
