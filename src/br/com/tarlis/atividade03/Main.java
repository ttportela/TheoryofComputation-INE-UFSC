/**
 * Problema - 264 - Count on Cantor | UVA Online Judge
 * Copyright (C) 2019  Tarlis Portela <tarlis@tarlis.com.br>
 * 
 * Disciplina: Teoria da Computação - Atividade 03
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
package br.com.tarlis.atividade03;

import java.io.*;
import java.util.*;

/**
 * Problema - 264 - Count on Cantor | UVA Online Judge
 * 
 * Disciplina: Teoria da Computação - Atividade 03
 * Professor: Maicon Rafael Zatelli
 * 
 * @author Tarlis Tortelli Portela
 *
 */
class Main {
	
	// Função utilitária para leitura do teclado, conforme modelo em UVa
	static String ReadLn (int maxLg) 
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
        int x;

        // Laço de repetição para leitura de cada linha da entrada:
        while ((input = Main.ReadLn (255)) != null)
        {
          // Converte a entrada em um número e chama a função para
          // buscar e imprimir a resposta.
          x = Integer.parseInt (input);
          cantor(x);
        }
    }
	
	void cantor(int x) {
		// Contador do termo equivalente no conjunto N
		int term = 1; 
		
		// Contador de n para subconjuntos de Q
		for (int n = term; n <= x; n++) {
			// Dois contadores: numerador e denominador
			for (int i = 1, j = n; i <= n; i++, j--) {
				// Imprime quando chega no termo pesquisado:
				if (term == x) {
					// Inverte a ordem de impressão para n ímpar
					if (n % 2 != 0)
						System.out.println("TERM " + term + " IS " + j + "/" + i);
					else
						System.out.println("TERM " + term + " IS " + i + "/" + j);
					return;
				}
				term++; // incremento do termo
			}
		}
	}
}
