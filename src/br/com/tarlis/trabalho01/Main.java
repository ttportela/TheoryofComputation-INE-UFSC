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
import java.io.ByteArrayInputStream;
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
//		byte[] buffer = new byte[System.in.available()+1];
//		System.in.read(buffer);
////		if (!System.lineSeparator().equals(buffer[buffer.length-2]))
//		buffer[buffer.length-1] = '\n';
//		
//		// Ferramentas de leitura da entrada:
//		InputStreamReader ir = new InputStreamReader(new ByteArrayInputStream(buffer));
//        BufferedReader in = new BufferedReader(ir);
		
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
			int[] VAR = split(input); 
			int[] cells = split(in.readLine());
			
			// Impressão do resultado:
//			print(cells);
			cells = automata3(VAR[0], VAR[1], VAR[2], VAR[3], cells);
			print(cells);
//			System.out.println("-- n="+VAR[0]+", m="+VAR[1]+", d="+VAR[2]+", k="+VAR[3]+" -- ");
//			System.out.println("------------------------------------------------ ");
		}
		
		// Contador de Tempo (Comentado)
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}

	private static int[] automata3(int n, int m, int d, int k, int[] cells) {
		int[] gen0 = cells = newState(n, m, d, cells);
//		System.out.print(0 + ": ");
//		print(cells);
		for (int i = 1; i < k; i++) {
			int[] aux = newState(n, m, d, cells);
			
//			System.out.print(i + ": ");
//			print(aux);
			
			if (Arrays.equals(cells, aux)) return aux;
			
			if (Arrays.equals(gen0, aux))
				k = i + (k % i);		
			
			cells = aux;
		}
		return cells;
	}
	
	private static int[] automata2(int n, int m, int d, int k, int[] cells) {
//		if (k > m)
//		while ((k % n) == 0) {
//			k = k / n;
//		} 
		
		for (int i = 0; i < k; i++) {
			cells = newState(n, m, d, cells);
		}
		return cells;
	}

	private static int cell(int j, int n, int[] cells) {
		int k = j < 0? n + j : (j >= n? j-n : j);
		return cells[k];
	}

	private static int[] automata(int n, int m, int d, int k, int[] cells) {
//		if (k > m)
//		do {
//			k = k / (m+1);
//		} while (k > 10);
		
		for (int i = 0; i < k; i++) {
			int[] aux = new int[n];
			for (int j = 0; j < n; j++) {
				aux[j] = sum1(j, d, n, cells) % m;
			}
			cells = aux;
		}
		return cells;
	}

	private static int[] newState(int n, int m, int d, int[] cells) {
		int[] aux = new int[n];
		int sum = sum1(0, d, n, cells);
		aux[0] = sum % m;
		for (int j = 1; j < n; j++) {
			sum -= cell(j-1-d, n, cells);
			sum += cell(j+d, n, cells);
			aux[j] = sum % m;
		}
		return aux;
	}
	
	private static int sum1(int i, int d, int n, int[] cells) {
		int sum = 0;
		for (int j = i-d; j <= i+d; j++) {
//			int k = j < 0? n + j : (j >= n? j-n : j);
//			if (distance(i,k,n) <= d) {
//				sum += cells[k];
//			}
			sum += cell(j, n, cells);
		}
		return sum;
	}

	private static int sum(int i, int j, int d, int n, int[] cells) {
		if (j == -1) j = n-1;
		if (j == n)  j = 0;
		if (distance(i,j,n) <= d) {
			int sum = cells[j];
			sum += sum(i, j-1,d, n, cells);
			sum += sum(i, j+1,d, n, cells);
			return sum;
		} else 
			return 0;
	}

	private static int distance(int i, int j, int n) {
		//  min(|i − j|, n − |i − j|) >> distance
		return Math.min(Math.abs(i-j), n - Math.abs(i-j));
	}

	private static void print(int[] cells) {
		String s = "";
		for (int i : cells) {
			s += i + " ";
		}
		System.out.println(s.trim());
	}

	private static int[] split(String input) {
		String[] s = input.trim().split(" ");
		return Arrays.asList(s).stream().mapToInt(Integer::parseInt).toArray();
	}
	
		
}
