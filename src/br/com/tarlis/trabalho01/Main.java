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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
		String input, input2;
		
		// Contador de Tempo (Comentado)
		long startTime = System.nanoTime();

		// Enquanto houver entrada:
		while (in.ready() && (input = in.readLine()) != null) {
			// n, m, d, k (1 ≤ n ≤ 500, 1 ≤ m ≤ 1.000.000, 0 ≤ d < n/2, 1 ≤ k ≤ 10.000.000)
			int[] VAR = split(input, 4); 
			int[] cells = split(input2 = in.readLine(), VAR[0]);
			
			// Impressão do resultado:
//			System.out.print("s -- ");
//			print(cells);
//			cells = automata6(VAR[0], VAR[1], VAR[2], VAR[3], input2);
			cells = automata9(VAR[0], VAR[1], VAR[2], VAR[3], cells);
//			System.out.print("e -- ");
			print(cells);
//			System.out.println("-- n="+VAR[0]+", m="+VAR[1]+", d="+VAR[2]+", k="+VAR[3]+" -- ");
//			System.out.println("--------------------------------- ");
		}
		
		// Contador de Tempo (Comentado)
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}

	private static int[] automata7(int n, int m, int d, int k, int[] prev) {
		int OLD = 0, SUM = 1, NEW = 2, NEX = 3;
		int[][] tape = new int[4][n];
		tape[OLD] = prev;
		tape[SUM][0] = sum1(0, d, n, tape[OLD]);
		tape[NEX][0] = tape[SUM][0] % m;
//		next[NEW][0] %= m;
//		tape[NEW][0] = 0;
//		for (int i = 0; i > 0-1-d; i--) {
//			tape[NEW][index(i, n)] += tape[SUM][0] % m;
//		}
		
		for (int i = -d; i <= d; i++) {
			// Apenas para os primeiros:
//			for (int j = 0; i < d; i++) {
				// Apenas para o primeiro:
//				tape[NEW][0] += (sum1(i, d, n, tape[OLD]) % m);
//			}
//			tape[NEW][0] += (sum1(i, d, n, tape[OLD]) % m);
			tape[NEW][index(i, n)] += tape[SUM][0] % m;
		}
//		System.out.println("0:");
//		System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
		
		int j = 1;
		for (; j <= d+d+1; j++) {
//			System.out.println(j + "::: [=" + index(j-1-d, n) + " - x=" + index(j-1, n) + " - ]=" + index(j+d, n));
			tape[SUM][j] = tape[SUM][index(j-1, n)] - tape[OLD][index(j-1-d, n)] + tape[OLD][index(j+d, n)];
			tape[NEX][j] = tape[SUM][j] % m;
//			next[NEW][j] %= m;
//			tape[NEW][j] = 0;
//			tape[NEW][index(j-1-d, n)] = 0;
			for (int i = j-d; i <= j+d; i++) {
				tape[NEW][index(i, n)] += tape[SUM][j] % m;
			}
//			tape[NEW][index(j+d+1, n)] = 0;
//			for (int i = j; i > j-1-d; i--) {
//				tape[NEW][index(i, n)] += tape[SUM][j] % m;
//			}
//			System.out.println(j + ":: ");
//			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
		}
		
//		for (int j = d*3+1; j <= d*2+d; j++) {
		int sum = tape[SUM][index(d*2, n)];
//		int j = d*2+1;
		for (; j <= n-d; j++) {
			int x = j % n, y = index(x-1-d, n);//, z = index(y-1, n);
			
			tape[SUM][x] = sum = sum - tape[OLD][index(x-1-d, n)] + tape[OLD][index(x+d, n)];
			tape[NEX][x] = tape[SUM][x] % m;
			
//			tape[SUM][y] = tape[NEW][y];
//			tape[OLD][y] = tape[NEX][y];//tape[SUM][y] % m;
//			tape[NEW][y] = 0;
			
//			tape[NEW][index(x+d+1, n)] = 0;
			for (int i = x-d; i <= x+d; i++) {
				tape[NEW][index(i, n)] += tape[SUM][x] % m;
			}
//			tape[NEX][x] = tape[NEW][x] % m;
			
//			System.out.println(j + "::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n));
//			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
						
			if (x == n-1) {
//				System.out.print("e -- ");
//				print(tape[NEX]);
				tape[OLD] = tape[NEX];
				tape[SUM] = tape[NEW];
				tape[NEW] = new int[n];
//				System.out.println(j + "::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n) + "?");
//				System.out.print("| -- ");print(tape[OLD]);System.out.print("| -- "); print(tape[NEX]); System.out.print("| -- ");print(tape[SUM]); System.out.print("| -- ");print(tape[NEW]);
			}
		}
		
		for (; j < k*n; j++) {
			int x = j % n, y = index(x-1-d, n);//, z = index(y-1, n);
			
//			if (x == 0) tape[SUM][x] = tape[SUM][0];
//			tape[SUM][x] = sum = sum - tape[OLD][index(x-1-d, n)] + tape[OLD][index(x+d, n)];
			tape[NEX][x] = tape[SUM][x] % m;
			
//			tape[SUM][y] = tape[NEW][y];
//			tape[OLD][y] = tape[NEX][y];//tape[SUM][y] % m;
//			tape[NEW][y] = 0;
			
//			tape[NEW][index(x+d+1, n)] = 0;
			for (int i = x-d; i <= x+d; i++) {
				tape[NEW][index(i, n)] += tape[SUM][x] % m;
			}
//			tape[NEX][x] = tape[NEW][x] % m;
			
//			System.out.println(j + ":::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n));
//			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
						
			if (x == n-1) {
//				System.out.print("e -- ");
//				print(tape[NEX]);
				tape[OLD] = tape[NEX];
				tape[SUM] = tape[NEW];
				tape[NEW] = new int[n];
//				sum = tape[OLD][0];
//				System.out.println(j + "::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n) + "?");
//				System.out.print("| -- ");print(tape[OLD]);System.out.print("| -- "); print(tape[NEX]); System.out.print("| -- ");print(tape[SUM]); System.out.print("| -- ");print(tape[NEW]);
			}
		}
		
//		for (int j = d*3-1; j < k*n; j++) {
//		for (int j = d*2+1; j < k*n; j++) {
//			int x = j % n, y = index(x-1-d, n);//, z = index(y-1, n);
//			
////			tape[SUM][x] = tape[NEW][x];
////			System.out.println(j + " ---- [" + index(x-1-d, n) + "," + index(x-1, n) + "," + index(x-1+d, n) + "]");
////			tape[SUM][x] = tape[SUM][index(x-1, n)] - tape[OLD][index(x-1-d, n)] + tape[OLD][index(x-1+d, n)];
////			tape[NEX][x] = tape[SUM][x] % m;
////			tape[NEX][x] = tape[NEW][x] % m;
//			
//			tape[OLD][y] = tape[NEX][y];//tape[SUM][y] % m;
//			tape[SUM][y] = tape[NEW][y];
//			tape[NEW][y] = 0;
//			
////			next[NEW][x] %= m;
//			// p/ Além de d:
////			tape[SUM][z] = tape[NEW][z];
//			// Restart SUM:
////			tape[NEW][x] = 0;
//			for (int i = x-d; i <= x+d; i++) {
////			for (int i = x-d; i <= x; i++) {
//				tape[NEW][index(i, n)] += tape[SUM][x] % m;
//			}
//			tape[NEX][x] = tape[NEW][x] % m;
////			tape[NEW][index(x+d+1, n)] = 0;
////			for (int i = x; i > x-1-d; i--) {
////				tape[NEW][index(i, n)] += tape[SUM][x] % m;
////			}
//			System.out.println(j + ":::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n) + " - z=?");
//			System.out.print("| -- ");print(tape[OLD]);System.out.print("| -- "); print(tape[NEX]); System.out.print("| -- ");print(tape[SUM]); System.out.print("| -- ");print(tape[NEW]);
//			if (x == n-1) {
//				System.out.print("e -- ");
//				print(tape[NEX]);
////				print(answer(n, d, m, tape));
//			}
//		}

		return tape[NEX];//answer(n, d, m, tape);
		
//		for (int i = 1; i < k; i++) {
//			for (int j = 1; j < n; j++) {
//				next[0][j] %= m;
//				next[1][j] = next[0][j] - cell(j-1-d, n, prev) + cell(j+d, n, prev);
//			}
//			
//		}
//			
//		
//		int[] gen0 = prev = newState(n, m, d, prev);
//		for (int i = 1; i < k; i++) {
//			int[] aux = newState(n, m, d, prev);
//			
//			if (Arrays.equals(prev, aux)) {
//				return aux;
//			}
//			
//			if (Arrays.equals(gen0, aux)) {
//				if ((k % i) == 0) return prev;
//				k = i + (k % i); 
//			}
//			
//			prev = aux;
//		}
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
	
	private static HashMap<String, Integer> M = new HashMap<String, Integer>();
	private static int[] automata6(int n, int m, int d, int k, String input) {
		String key = n + " " + m + " " + d + "-" + input;
		int i = 0;
		int[] gen0 = null, cells;
		// Procura resultados calculados anteriormente:
		if (M.containsKey(key)) {
			i = (int) M.get(key);
			
			// Um resultado previamente calculado tem um padrão:
			// Procura a próxima geração k multipla de i;
			k = i + (k % i);
		}
		
		// Geração 0 - para procurar padrões:
		cells = newState(n, m, d, split(input, n));
		System.out.print(0 + ": ");
		print(cells);
		
		// Próximas gerações:
		for (i = 1; i < k; i++) {
			// Modifica o estado (próxima geração):
			int[] aux = newState(n, m, d, cells);
			
			System.out.print(i + ": ");
			print(aux);

			// Os valores das células se repetem a cada geração:
			if (Arrays.equals(cells, aux)) {
				System.out.println(">>> EQUALS ");
				return aux;
			}

			// Encontrou por um padrão nas gerações:
			if (Arrays.equals(gen0, aux)) {
				k = i + (k % i);
				M.put(key, i);
				System.out.println(">>> LOOP: " + ((k % i)-1));
			}
			
			cells = aux;
		}
		return cells;
	}

//	private static HashMap<String, ArrayList> M = new HashMap<String, ArrayList>();
//	private static int[] automata5(int n, int m, int d, int k, String input) {
//		String key = n + " " + m + " " + d + "-" + input;
//		ArrayList x = null;
//		int i = 0;
//		int[] gen0 = null, cells;
//		// Procura resultados calculados anteriormente:
//		if ((x = M.get(key)) != null) {
//			i = (int) x.get(0);
//			
//			// Um resultado previamente calculado tem um padrão:
//			// Procura a próxima geração k multipla de i;
//			if ((boolean) x.get(1)) {
//				k = i + (k % i);
//				i = x.size()-2;
//			}
//			
//			// O resultado já foi previamente calculado:
//			if (x.size()-2 >= k) {
//				return (int[]) x.get(k+1);
//			}	
//			
//			// OU - Não encontrou um padrão mas já fez parte dos cálculos
//			// Valor de i é a útlima geração calculada
//			gen0 = (int[]) x.get(2);
//		}
//		
//		// Geração 0 - para procurar padrões:
//		if (i == 0) {
//			cells = newState(n, m, d, split(input, n)); 
////			System.out.print(i + ": ");
////			print(cells);
//			i++;
//			gen0 = cells;
//			x = new ArrayList();
//			x.add(0); // 1ª generação
//			x.add(false); // Sem padrão
//			x.add(gen0); // Células - iteração 0
//			M.put(key, x); // Salva
//		} else {
//			cells = (int[]) x.get(x.size()-1);
//		}
//		
//		// Próximas gerações:
//		for (; i < k; i++) {
//			// Modifica o estado (próxima geração):
//			int[] aux = newState(n, m, d, cells);
//			x.add(aux);
////			System.out.print(i + ": ");
////			print(aux);
//			
//			// Os valores das células se repetem a cada geração:
//			if (Arrays.equals(cells, aux)) {
//				return aux;
//			}
//			
//			// Encontrou por um padrão nas gerações:
//			if (Arrays.equals(gen0, aux)) {
//				k = i + (k % i);
////				M.remove(key);
//				x.set(0, i);
//				x.set(1, true);
//			} else if (k > 1000) {
//				M.remove(key);
//			}
//			
//			if (!(boolean) x.get(1)) x.set(0, i);
//			
//			cells = aux;
//		}
//		return cells;
//	}

//	private static int[] automata4(int n, int m, int d, int k, int[] cells) {
//		int[] gen0 = null;
//		for (int i = 0; i < k; i++) {
//			int[] aux = newState(n, m, d, cells);
//						
//			if (Arrays.equals(cells, aux)) {
//				return aux;
//			}
//			
//			if (gen0 != null && Arrays.equals(gen0, aux)) {
//				k = i + (k % i);
//			}
//			if (i == n-1) {
//				gen0 = aux;
//			}
//			
//			cells = aux;
//		}
//		return cells;
//	}

	private static int[] automata9(int n, int m, int d, int k, int[] cells) {
		int[][] gen0 = new int[n+1][];
		gen0[0] = cells = newState(n, m, d, cells);
		System.out.print(0 + ": ");
		print(cells);
		BB:
		for (int i = 1; i < k; i++) {
			int[] aux = newState(n, m, d, cells);
			
			System.out.print(i + ": ");
			print(aux);
//			if (i <= 505) {
//				if (Arrays.equals(cells, aux)) {
////					System.out.println(i + " >>> EQUALS ");
//					return aux;
//				}
//				
				if (i <= n) {
					gen0[i] = aux;
				} else
				for (int[] gen : gen0) {
					if (Arrays.equals(gen, aux)) {
						System.out.println(i + " >>> LOOP");
						print(gen);
						break BB;
					}
				}
//				if (Arrays.equals(gen0, aux)) {
//					k = i + (i-(m-1));
//					System.out.println(i + " >>> LOOP: NEW-k=" + k);
//					if ((i-m) - (i-(m-1)) == -1) return cells;
//					if ((i-m) - (i-(m-1)) == 0)  return aux;
//				}
//			}
			
			cells = aux;
		}
		
		for (int i = 0; i < m+2; i++) {
			int[] aux = newState(n, m, d, cells);
			
			System.out.print(i + ":: ");
			print(aux);
			
			for (int[] gen : gen0) {
				if (Arrays.equals(gen, aux)) {
					System.out.println(i + " >>> LOOP");
					print(gen);
				}
			}
//			if (Arrays.equals(gen0, aux)) {
//				k = i + (i-(m-1));
//				System.out.println(i + " >>> LOOP: NEW-k=" + k);
//				if ((i-m) - (i-(m-1)) == -1) return cells;
//				if ((i-m) - (i-(m-1)) == 0)  return aux;
//			}
			
			cells = aux;
		}
		
		return cells;
	}

	private static int[] automata3(int n, int m, int d, int k, int[] cells) {
		int[] gen0 = cells = newState(n, m, d, cells);
//		System.out.print(0 + ": ");
//		print(cells);
		for (int i = 1; i < k; i++) {
			int[] aux = newState(n, m, d, cells);
			
			System.out.print(i + ": ");
			print(aux);
//			if (i <= 505) {
				if (Arrays.equals(cells, aux)) {
//					System.out.println(i + " >>> EQUALS ");
					return aux;
				}
//				
				if (Arrays.equals(gen0, aux)) {
					if ((k % i) == 0) return cells;
					k = i + (k % i); //((k % i) < 1? i: (k % i));
//					System.out.println(i + " >>> LOOP: " + ((k % i)));
				}
//			}
			
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

	private static int[] split(String input, int n) {
		String[] s = input.trim().split(" ");
		int[] cells = new int[n];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = Integer.parseInt(s[i]);
		}
		return cells;
	}
	
		
}
