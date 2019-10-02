/**
 * Problema - 3704 - Cellular Automaton | ICPC Live Archive
 * Copyright (C) 2019  Tarlis Portela <tarlis@tarlis.com.br>
 * 
 * Disciplina: Teoria da Computação - Atividade 02
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
package br.com.tarlis.trabalho01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Problema - 3704 - Cellular Automaton | ICPC Live Archive
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
		String input;
		
		// Contador de Tempo (Comentado)
		long startTime = System.nanoTime();

		// Enquanto houver entrada:
		while (in.ready() && (input = in.readLine()) != null) {
			int[] VAR = split(input); // n, m, d, k (1 ≤ n ≤ 500, 1 ≤ m ≤ 1.000.000, 0 ≤ d < n/2, 1 ≤ k ≤ 10.000.000)
			int[] cells = split(in.readLine());
			
			// Impressão do resultado:
			cells = automata(VAR[0], VAR[1], VAR[2], VAR[3], cells);
			print(cells);
		}
		
		// Contador de Tempo (Comentado)
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}

	private static int[] automata(int n, int m, int d, int k, int[] cells) {
		for (int i = 0; i < k; i++) {
			int[] aux = new int[n];
			for (int j = 0; j < n; j++) {
				aux[j] = sum1(j, d, n, cells) % m;
			}
			cells = aux;
		}
		return cells;
	}
	
	private static int sum1(int i, int d, int n, int[] cells) {
		int sum = 0;
		for (int j = i-d; j <= i+d; j++) {
			int k = j < 0? n + j : (j >= n? j-n : j);
			if (distance(i,k,n) <= d) {
				sum += cells[k];
			}
		}
		return sum;
	}

//	private static int sum(int i, int j, int d, int n, int[] cells) {
//		if (j == -1) j = n-1;
//		if (j == n)  j = 0;
//		if (distance(i,j,n) <= d) {
//			int sum = cells[j];
//			sum += sum(i, j-1,d, n, cells);
//			sum += sum(i, j+1,d, n, cells);
//			return sum;
//		} else 
//			return 0;
//	}

	private static int distance(int i, int j, int n) {
		//  min(|i − j|, n − |i − j|) >> distance
		return Math.min(Math.abs(i-j), n - Math.abs(i-j));
	}

	private static void print(int[] cells) {
		for (int i : cells) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	private static int[] split(String input) {
		String[] s = input.trim().split(" ");
		return Arrays.asList(s).stream().mapToInt(Integer::parseInt).toArray();
	}
	
		
}
