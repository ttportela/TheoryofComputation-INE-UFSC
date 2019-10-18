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
 * Disciplina: Teoria da Computação - Atividade 01
 * Professor: Maicon Rafael Zatelli
 * 
 * @author Tarlis Tortelli Portela
 *
 */
class Main {
	
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
        int x;

        while ((input = Main.ReadLn (255)) != null)
        {
          x = Integer.parseInt (input);
          cantor(x);
        }
    }
	
	void cantor(int x) {
		int term = 1; boolean invert = true;
		for (int n = term; n <= x; n++) {
			for (int i = 1, j = n; i <= n; i++, j--) {
				if (term == x) {
					if (invert)
						System.out.println("TERM " + term + " IS " + j + "/" + i);
					else
						System.out.println("TERM " + term + " IS " + i + "/" + j);
					return;
				}
				term++;
			}
			invert = !invert;
		}
	}
}
