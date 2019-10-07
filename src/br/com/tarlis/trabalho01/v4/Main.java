/**
 * Problema - 3704 - Cellular Automaton | ICPC Live Archive
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
package br.com.tarlis.trabalho01.v4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	/**
	 * Problema - 3704 - Cellular Automaton | ICPC Live Archive
	 * Intersecção de Linguagens (Comparação de concatenações sucessivas) 
	 * 
	 * Disciplina: Teoria da Computação - Atividade 01
	 * Professor: Maicon Rafael Zatelli
	 * 
	 * @author Tarlis Tortelli Portela
	 * @author Vanessa Lago Machado
	 * @author Rômulo Almeida
	 *
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		
//		// Ferramentas de leitura da entrada:
		InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(ir);
		
        // Variáveis úteis:
		String input;

		// Enquanto houver entrada:
		while (in.ready() && (input = in.readLine()) != null) {
			int[] VAR = split(input, 4); 
			int[] cells = split(in.readLine(), VAR[0]);
			
			// Impressão do resultado:
			cells = automata(VAR[0], VAR[1], VAR[2], VAR[3], cells);
			print(cells);
		}
		
		return;
	}
	
	private static int[] automata(int n, int m, int d, int k, int[] prev) {
		int OLD = 0, SUM = 1, NEW = 2, NEX = 3;
		int[][] tape = new int[4][n];
		tape[OLD] = prev;
		tape[SUM][0] = sum(0, d, n, tape[OLD]);
		tape[NEX][0] = tape[SUM][0] % m;

		// Apenas para os primeiros:
		for (int i = -d; i <= d; i++) {
			tape[NEW][index(i, n)] += tape[SUM][0] % m;
		}
		System.out.println("0-");
		System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
		
		// Passo 1: Todas as somas da geração 0:
		int j = 1;
		int sum = tape[SUM][0];
		// Passo 1.1):
		for (; j < d+1; j++) {
			int x = j % n;
			
			tape[SUM][x] = sum = sum - tape[OLD][index(x-1-d, n)] + tape[OLD][index(x+d, n)];
			
			System.out.println(j + ": ");
			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
			System.out.println("| --- -- x=" + x + " - y=" + '?' + " - z=" + '?');
		}
		// Passo 1.2):
		for (; j < n; j++) {
			int x = j % n, y = index(x-1-d, n);
			
			tape[SUM][x] = sum = sum - tape[OLD][index(x-1-d, n)] + tape[OLD][index(x+d, n)];
			// Calcular próximos valores:
			tape[NEX][y] = tape[SUM][y] % m;
			
			System.out.println(j + ": ");
			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
			System.out.println("| --- -- x=" + x + " - y=" + y + " - z=" + '?');
		}
		
		// Passo 2: 
		// A - Todas as somas da geração 1->n:
		// B - Todos os resultados da geração 0->n-1;
		for (; j < k*n+n; j++) {
			int x = j % n, y = index(x-1-d, n), z = index(x-(n/2), n);
			
			// A:
			tape[SUM][x] = sum = sum - tape[OLD][index(x-1-d, n)] + tape[OLD][index(x+d, n)];
			// Calcular próximos valores:
			tape[NEX][y] = tape[SUM][y] % m;
			
			// B:
			tape[OLD][z] = tape[NEX][z];
			
			System.out.println(j + ":: ");
			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
			System.out.println("| --- -- x=" + x + " - y=" + y + " - z=" + z);
			
			if (y == (n-1)) {
				System.out.print("e -- ");
				print(tape[NEX]);
			}
		}
		
		return tape[NEX];//answer(n, d, m, tape);
	}
	
	private static int[] answer(int n, int d, int m, int[][] next) {
		int[] cells = new int[n];
		
		int i = 0;
		for (; i < n-d-2; i++) {
			cells[i] = next[0][i];
		}
		
		for (; i < n; i++) {
//			cells[i] = next[1][i] % m;
			cells[i] = next[3][i];
		}
		
		return cells;
	}

	private static int index(int j, int n) {
		return j < 0? n + j : (j >= n? j-n : j);
	}
	
	private static int sum(int i, int d, int n, int[] cells) {
		int sum = 0;
		for (int j = i-d; j <= i+d; j++) {
			sum += cell(j, n, cells);
		}
		return sum;
	}

	private static int cell(int j, int n, int[] cells) {
//		int k = j < 0? n + j : (j >= n? j-n : j);
		return cells[index(j, n)];
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