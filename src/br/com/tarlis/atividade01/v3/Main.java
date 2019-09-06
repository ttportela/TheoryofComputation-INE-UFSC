/**
 * Solução C - Problema - Palavras | URI Online Judge | 1231
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
package br.com.tarlis.atividade01.v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Solução C - Problema - Palavras | URI Online Judge | 1231
 * Intersecção de Linguagens (Equivalência de Autômato Finito) 
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
			boolean match = false;
			// Teste apenas para a hipótese de L1 ou L2 ser vazia:
			if (!L1.isEmpty() && !L2.isEmpty())
				match = AFN("", "", 0);
			
			// Impressão do resultado:
			System.out.println(match? "S" : "N");
		}
		
		// Contador de Tempo (Comentado)
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}

	/**
	 * 
	 * 
	 * @param w Palavra de entrada - L1. Máquina 1 (M1)
	 * @param u Palavra de entrada - L2. Máquina 2 (M2)
	 * @param pos Posição da entrada (caractere que representa o estado atual).
	 * @return true Se as palavras terminam idênticas.
	 */
	private static boolean AFN(String w, String u, int pos) {
		// Limita o a quantidade de iterações 
		// quando o tamanho da entrada ultrapassa MAX_POS (estado de rejeição, LEAF caso 1):
		if (pos > MAX_POS) return false;
		
		// Flag que demarca se encontrou a solução:
		boolean solved = false;
		// Nesses dois casos, tenta cada possível concatenação:
		if (w.length()-1 < pos) {
			for (String w2 : L1) {
				solved = solved || AFN((w + w2), u, pos); // Replica as Máquinas: Concatenação da entrada de M1, pos não muda
			}
		} else if (u.length()-1 < pos) {
			for (String u2 : L2) {
				solved = solved || AFN(w, (u + u2), pos); // Replica as Máquinas: Concatenação da entrada de M2, pos não muda
			}
		} 
		// Nenhuma concatenação é necessária, por enquanto.
		else {
			// Se a entrada e diferente (estado de rejeição, LEAF caso 2)
			if (w.charAt(pos) != u.charAt(pos)) return false; // este ramo do AFN morre
			// Concatenações até "pos" tem a mesma sequência de entrada
			else {
				// Concatenações terminam com o mesmo tamanho e mesma sequência de caracateres 
				// (estado de aceitação, LEAF caso 3)
				if (w.length()-1 == pos && u.length()-1 == pos) {
					return true; 
				} 
				// Caso contrário, continua:
				else {
					solved = solved || AFN(w, u, pos+1); // pos+1 representa o próximo caractere de entrada
				}
			}
			
		}
		
		// Se não for nenum doa casos LEAF:
		return solved;
	}

	// Variáveis globais auxiliares:
	static int MAX_POS = 40; // Tamanho limite da palavra
	static List<String> L1; 
	static List<String> L2;

}
