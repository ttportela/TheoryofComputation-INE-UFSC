/**
 * Problema - 3704 - Cellular Automaton | ICPC Live Archive
 * 
 * Disciplina: Teoria da Computação - Trabalho 01
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
package br.com.tarlis.trabalho01.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Problema - 3704 - Cellular Automaton | ICPC Live Archive
 * 
 * Disciplina: Teoria da Computação - Atividade 01
 * Professor: Maicon Rafael Zatelli
 * 
 * @author Tarlis Tortelli Portela
 * @author Vanessa Lago Machado
 * @author Rômulo Almeida
 *
 */
public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {

		// Ferramentas de leitura da entrada:
		InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(ir);
		
        // Variáveis úteis:
		String input;
		
		// Contador de Tempo (Comentado)
//		long startTime = System.nanoTime();

		// Enquanto houver entrada:
		while (in.ready() && (input = in.readLine()) != null) {
			// n, m, d, k (1 ≤ n ≤ 500, 1 ≤ m ≤ 1.000.000, 0 ≤ d < n/2, 1 ≤ k ≤ 10.000.000)
			int[] VAR = split(input, 4); 
			int[] cells = split(in.readLine(), VAR[0]);
			
			// Impressão do resultado:
			cells = automata(VAR[0], VAR[1], VAR[2], VAR[3], cells);
			print(cells);
		}
		
		// Contador de Tempo (Comentado)
//		long endTime   = System.nanoTime();
//		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}
	
	/**
	 * Solução B.
	 * 
	 */
	private static int[] automata(int n, int m, int d, int k, int[] cells) {
		// Para cada passo k:
		for (int i = 0; i < k; i++) {
			// Calcula o novo estado do autômato:
			cells = newState(n, m, d, cells);
		}
		return cells;
	}

	private static int[] newState(int n, int m, int d, int[] cells) {
		int[] aux = new int[n];
		// Faz apenas a soma da vizinhança do primeiro elemento,
		int sum = sum(0, d, n, cells);
		aux[0] = sum % m;
		// Calcula o novo valor de cada célula
		// do vetor, porém a soma dos demais elementos é
		// Calculada sobre a soma do primeiro, evitando retrabalho.
		for (int j = 1; j < n; j++) {
			sum -= cells[index(j-1-d, n)];
			sum += cells[index(j+d, n)];
			aux[j] = sum % m;
		}
		return aux;
	}

	// Com a função index, evita-se a cópia repetitiva do vetor
	// em emória, basta sabermos o índice do elemento procurado:
	private static int index(int j, int n) {
		// Encontra o índice respectivo de j no vetor "circular"
		return j < 0? n + j : (j >= n? j-n : j);
	}
	
	private static int sum(int i, int d, int n, int[] cells) {
		int sum = 0;
		// Calcula a soma dos valores
		// das células vizinhas de distância
		// -d até +d:
		for (int j = i-d; j <= i+d; j++) {
			sum += cells[index(j, n)];
		}
		return sum;
	}

	private static void print(int[] cells) {
		String s = "";
		for (int i : cells) {
			s += i + " ";
		}
		System.out.println(s.trim());
	}

	private static int[] split(String input, int n) {
		String[] s = input.trim().split(" ");
		int[] cells = new int[n];
		for (int i = 0; i < Math.min(s.length, n); i++) {
			cells[i] = Integer.parseInt(s[i]);
		}
		return cells;
	}
		
}
