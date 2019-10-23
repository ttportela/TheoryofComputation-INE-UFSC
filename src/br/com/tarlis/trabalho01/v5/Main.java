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
package br.com.tarlis.trabalho01.v5;

import java.io.*;
import java.util.*;

class Main
{
 static String ReadLn (int maxLg)  // utility function to read from stdin
 {
     byte lin[] = new byte [maxLg];
     int lg = 0, car = -1;
     String line = "";

     try
     {
         while (lg < maxLg)
         {
             car = System.in.read();
             if ((car < 0) || (car == '\n')) break;
             lin [lg++] += car;
         }
     }
     catch (IOException e)
     {
         return (null);
     }

     if ((car < 0) && (lg == 0)) return (null);  // eof
     return (new String (lin, 0, lg));
 }

 public static void main (String args[])  // entry point from OS
 {
     Main myWork = new Main();  // create a dinamic instance
     myWork.Begin();            // the true entry point
 }

 void Begin()
 {
     String input;
     StringTokenizer idata;

     while ((input = Main.ReadLn (255)) != null)
     {
       idata = new StringTokenizer (input);
       int n, m, d, k; 
       n = Integer.parseInt (idata.nextToken());
       m = Integer.parseInt (idata.nextToken());
       d = Integer.parseInt (idata.nextToken());
       k = Integer.parseInt (idata.nextToken());
       
       input = Main.ReadLn (10000);
       idata = new StringTokenizer (input);
       int[] cells = new int[n];
       
       for (int i = 0; i < n; i++) {
    	   cells[i] = Integer.parseInt (idata.nextToken());
       }
		
		// Impressão do resultado:
		cells = automata(n, m, d, k, cells);
		print(cells);
		
     }
 }
 


	/**
	 * Solução C.
	 * 
	 */
	int[] automata(int n, int m, int d, int k, int[] cells) {
		// Calcula o primeiro estado
		int[] gen0 = cells = newState(n, m, d, cells);
		// Para cada passo k (menos 1):
		for (int i = 1; i < k; i++) {
			// Calcula o novo estado:
			int[] aux = newState(n, m, d, cells);
			
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

	int[] newState(int n, int m, int d, int[] cells) {
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
	int index(int j, int n) {
		// Encontra o índice respectivo de j no vetor "circular"
		return j < 0? n + j : (j >= n? j-n : j);
	}
	
	int sum(int i, int d, int n, int[] cells) {
		int sum = 0;
		// Calcula a soma dos valores
		// das células vizinhas de distância
		// -d até +d:
		for (int j = i-d; j <= i+d; j++) {
			sum += cells[index(j, n)];
		}
		return sum;
	}
	
	void print(int[] cells) {
		String s = "";
		for (int i : cells) {
			s += i + " ";
		}
		System.out.println(s.trim());
	}

	int[] split(String input, int n) {
		String[] s = input.trim().split(" ");
		int[] cells = new int[n];
		for (int i = 0; i < Math.min(s.length, n); i++) {
			cells[i] = Integer.parseInt(s[i]);
		}
		return cells;
	}
}