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
package br.com.tarlis.trabalho01.v4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
		long startTime = System.nanoTime();

		// Enquanto houver entrada:
		while (in.ready() && (input = in.readLine()) != null) {
			// n, m, d, k (1 ≤ n ≤ 500, 1 ≤ m ≤ 1.000.000, 0 ≤ d < n/2, 1 ≤ k ≤ 10.000.000)
			long[] VAR = split(input, 4); 
			long[] cells = split(in.readLine(), (int) VAR[0]);
			
			// Impressão do resultado:
			cells = automata((int) VAR[0], VAR[1], (int) VAR[2], VAR[3], cells);
			print(cells);
		}
		
		// Contador de Tempo (Comentado)
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}

	/**
	 * Solução C.
	 * 
	 */
	private static long[] automata(int n, long m, int d, long k, long[] cells) {
		// Calcula o primeiro estado
		long[] gen0 = cells = newState(n, m, d, cells);
		// Para cada passo k (menos 1):
		for (long i = 1; i < k; i++) {
			// Calcula o novo estado:
			long[] aux = newState(n, m, d, cells);
			
			// Verifica se os valores das células dos últimos 2 
			// estados são iguais, entao retorna (não muda)
			if (Arrays.equals(cells, aux)) {
				return aux;
			}
			
			// Verifica se os valores das células são iguais
			// aos do primeiro passo, então há um ciclo: 
			if (Arrays.equals(gen0, aux)) {
				// O resultado é o estado anterior, retorna:
				if ((k % i) == 0) return cells;
				// altera o valor de k para calcular apenas até o valor 
				// esperado da resposta: 
				k = i + (k % i);
			}
			
			cells = aux;
		}
		return cells;
	}

	private static long[] newState(int n, long m, int d, long[] cells) {
		long[] aux = new long[n];
		// Faz apenas a soma da vizinhança do primeiro elemento,
		long sum = (n == d*2)? sumall(n, cells) : sum(0, d, n, cells);
		aux[0] = sum % m;
		// Calcula o novo valor de cada célula
		// do vetor, porém a soma dos demais elementos é
		// Calculada sobre a soma do primeiro, evitando retrabalho.
		for (int j = 1; j < n; j++) {
			if (n > d*2) {
				sum -= cells[index(j-1-d, n)];
				sum += cells[index(j+d, n)];
			}
			aux[j] = sum % m;
		}
		return aux;
	}

	private static long sumall(int n, long[] cells) {
		long sum = 0;
		for (int j = 0; j < n; j++) {
			sum += cells[j];
		}
		return sum;
	}

	// Com a função index, evita-se a cópia repetitiva do vetor
	// em emória, basta sabermos o índice do elemento procurado:
	private static int index(int j, int n) {
		// Encontra o índice respectivo de j no vetor "circular"
		return j < 0? n + j : (j >= n? j-n : j);
	}
	
	private static long sum(int i, int d, int n, long[] cells) {
		long sum = 0;
		// Calcula a soma dos valores
		// das células vizinhas de distância
		// -d até +d:
		for (int j = i-d; j <= i+d; j++) {
//		for (int j = 0; j < n; j++) {
//			if (Math.min(Math.abs(i - j), (n-1) - Math.abs(i - j)) <= d)
				sum += cells[index(j, n)];
		}
		return sum;
	}
	
	private static void print(long[] cells) {
		String s = "";
		for (long i : cells) {
			s += i + " ";
		}
		System.out.println(s.trim());
	}

	private static long[] split(String input, int n) {
		String[] s = input.trim().split(" ");
		long[] cells = new long[n];
		for (int i = 0; i < Math.min(s.length, n); i++) {
			cells[i] = Integer.parseInt(s[i]);
		}
		return cells;
	}
	
		
}
