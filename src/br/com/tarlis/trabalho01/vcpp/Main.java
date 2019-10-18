package br.com.tarlis.trabalho01.vcpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
//		// Ferramentas de leitura da entrada:
//		byte[] buffer = new byte[System.in.available()+1];
//		System.in.read(buffer);
//		buffer[buffer.length-1] = '\n';
//		
//		// Ferramentas de leitura da entrada:
//		InputStreamReader ir = new InputStreamReader(new ByteArrayInputStream(buffer));
//        BufferedReader in = new BufferedReader(ir);
        

		InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(ir);
		
        // Variáveis úteis:
		String input, input2;

		// Enquanto houver entrada:
		while (in.ready() && (input = in.readLine()) != null) {
			int[] VAR = split(input, 4); 
			int[] cells = split(input2 = in.readLine(), VAR[0]);
			
			// Impressão do resultado:
			cells = automata8(VAR[0], VAR[1], VAR[2], VAR[3], cells);
			print(cells);
		}
		
		return;
	}
	
	private static int[] automata8(int n, int m, int d, int k, int[] prev) {
		int OLD = 0, SUM = 1, NEW = 2, NEX = 3;
		int[][] tape = new int[4][n];
		tape[OLD] = prev;
		tape[SUM][0] = sum1(0, d, n, tape[OLD]);
		tape[NEX][0] = tape[SUM][0] % m;

		// Apenas para os primeiros: //TODO ?
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
	
	private static int[] automatax(int n, int m, int d, int k, int[] prev) {
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
		System.out.println("0:");
		System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
		
		int j = 1;
		for (; j <= d+d; j++) {
//			System.out.println(j + "::: [=" + index(j-1-d, n) + " - x=" + index(j-1, n) + " - ]=" + index(j+d, n));
			tape[SUM][j] = tape[SUM][index(j-1, n)] - tape[OLD][index(j-1-d, n)] + tape[OLD][index(j+d, n)];
			tape[NEX][j] = tape[SUM][j] % m;
//			next[NEW][j] %= m;
//			tape[NEW][j] = 0;
//			tape[NEW][index(j-1-d, n)] = 0;
			for (int i = j-d; i <= j+d; i++) {
				tape[NEW][index(i, n)] += tape[SUM][j] % m;
			}
			tape[NEW][index(j+d+1, n)] = 0;
//			tape[NEW][index(j+d+1, n)] = 0;
//			for (int i = j; i > j-1-d; i--) {
//				tape[NEW][index(i, n)] += tape[SUM][j] % m;
//			}
			System.out.println(j + ":: ");
			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
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
			tape[NEW][index(x+d+1, n)] = 0;
//			tape[NEX][x] = tape[NEW][x] % m;
			
			System.out.println(j + "::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n));
			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
						
			if (x == n-1) {
				System.out.print("e -- ");
				print(tape[NEX]);
				tape[OLD] = tape[NEX];
				tape[SUM] = tape[NEW];
				tape[NEW] = new int[n];
				System.out.println(j + "::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n) + "?");
				System.out.print("| -- ");print(tape[OLD]);System.out.print("| -- "); print(tape[NEX]); System.out.print("| -- ");print(tape[SUM]); System.out.print("| -- ");print(tape[NEW]);
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
			tape[NEW][index(x+d+1, n)] = 0;
//			tape[NEX][x] = tape[NEW][x] % m;
			
			System.out.println(j + ":::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n));
			System.out.print("| OLD -- ");print(tape[OLD]);System.out.print("| NEX -- "); print(tape[NEX]); System.out.print("| SUO -- ");print(tape[SUM]); System.out.print("| SUN -- ");print(tape[NEW]);
						
			if (x == n-1) {
				System.out.print("e -- ");
				print(tape[NEX]);
				tape[OLD] = tape[NEX];
				tape[SUM] = tape[NEW];
				tape[NEW] = new int[n];
//				sum = tape[OLD][0];
				System.out.println(j + "::: x=" + x + " - y=" + y + " - ]=" + index(x+d, n) + "?");
				System.out.print("| -- ");print(tape[OLD]);System.out.print("| -- "); print(tape[NEX]); System.out.print("| -- ");print(tape[SUM]); System.out.print("| -- ");print(tape[NEW]);
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
	
	private static int sum1(int i, int d, int n, int[] cells) {
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