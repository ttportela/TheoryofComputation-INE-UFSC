/**
 * Solução A - Problema - Palavras | URI Online Judge | 1231
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
package br.com.tarlis.atividade01.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Solução A - Problema - Palavras | URI Online Judge | 1231
 * Intersecção de Linguagens (Comparação de concatenações sucessivas) 
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
		long startTime = System.nanoTime();
		
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
			solved = false;
			// Entrada com palavras vazias
			concat("","",0);
			
			// Impressão do resultado:
			System.out.println(solved? "S" : "N");
		}
		
		// Contador de Tempo (Comentado)
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}
	
	/**
	 * Método de concatenações sucessivas.
	 * 1 => Faz L1* e L2* -> Concatena as palavras em cada variação possível (todas as palavras com todas)
	 * 2 => Compara todas as concatenações de uma linguagem com a outra para identificar se alguma é igual
	 * - Elimina a recursividade em concatenações em que as palavras não iniciam com os mesmos símbolos.
	 * 
	 * @param wl1 Palavra de L1*
	 * @param wl2 Palavra de L2*
	 * @param it Número da iteração;
	 */
	private static void concat(String wl1, String wl2, int it) {
		// Limita o a quantidade de iterações:
		if (solved || it >= MAX_IT || (wl1.length() > MAX_SIZE || wl2.length() > MAX_SIZE)) return;
		
		// Auxiliar:
		String s;
		
		// Todas as combinações de L1*
		for (String w : L1) {
			s = (wl1 + w);
			if (s.equals(wl2)) { 
				solved = true; return; // Encontrou a solução!
			}
			// Se o início das palavras são diferentes, para de fazer concatenações:
			else if (startsWith(s, wl2)) {
				// Itera recursivamente sobre as outras concatenações possíveis de L1
				concat(s, wl2, it+1);
			}	
		}
		
		// Todas as combinações de L2*
		for (String w : L2) {
			s = (wl2 + w);
			if (s.equals(wl1)) { // Encontrou a solução!
				solved = true; return; 
			}
			// Se o início das palavras são diferentes, para de fazer concatenações:
			else if (startsWith(wl1, s)) {
				// Itera recursivamente sobre as outras concatenações possíveis de L2
				concat(wl1, s, it+1);
			}
		}
	}

	/**
	 * Verifica se as palavras correspondem.
	 * 
	 * @return true Se as palavras iniciam com os mesmos caracteres.
	 */
	private static boolean startsWith(String wl1, String wl2) {
		// Compara o início das palavras, da maior pela menor:
		if (wl1.length() <= wl2.length()) 
			return wl2.startsWith(wl1);
		else 
			return wl1.startsWith(wl2);
	}

	// Variáveis globais auxiliares:
	static boolean solved = false;
	static int MAX_IT = 10; // Limite de iterações recursivas
	static int MAX_SIZE = 40; // Tamanho limite da palavra
	static List<String> L1; 
	static List<String> L2;

}
