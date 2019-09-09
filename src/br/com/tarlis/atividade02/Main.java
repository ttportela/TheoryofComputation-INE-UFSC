/**
 * Problema - LEXSIM - Avaliador Lexico e Sintático | URI Online Judge | 1083
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
package br.com.tarlis.atividade02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Problema - LEXSIM - Avaliador Lexico e Sintático | URI Online Judge | 1083
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
			
			// Impressão do resultado:
			lexsim(input);
			
		}
		
		// Contador de Tempo (Comentado)
		long endTime   = System.nanoTime();
		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
		return;
	}
	
	/**
	 * Método de partida e tratamento de erros. 
	 * 
	 * @param wl1 Palavra de L1*
	 * @param wl2 Palavra de L2*
	 * @param it Número da iteração;
	 */
	private static void lexsim(String input) {
		try {			
			Parser expr = Parser.parse(input);
			System.out.println(expr.postOrder());
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// Variáveis globais auxiliares:
//	static int MAX_IT = 10; // Limite de iterações recursivas

}

class Parser {
	
	protected static final char[] OPERATORS = new char[] {
			'|', '.', '#', '=', '<', '>', '-', '+', '/', '*', '^' };
	
	protected static final int[] PRIORITY = new int[] {
			1,	  2,   3, 	3, 	 3,   3,   4,   4,   5,   5,   6 };
	
	protected char root;
	protected Parser right = null;
	protected Parser left = null;
	
	private Parser(String expression, List<Marker> markers, int increment) {
		if (expression.length() == 1) {
			root = expression.charAt(0);
			return;
		} else if (!markers.isEmpty()) {
			// Tira do topo (como uma Pilha)
			Marker m = getMarker(markers, increment);
			markers.remove(0);
			
			int pos = m.pos-increment;
			System.out.println(expression + "/" + m.pos +"/"+ pos +"||");
			root = expression.charAt(pos);
			
			String s;
			if (pos <= 0) 
				throw ParseException.SYN_ERROR;
			else { 
				s = trimMarks(expression.substring(0, pos));
				left = new Parser( s, markers, 0 );
			}
			
			if (pos+1 >= expression.length())
				throw ParseException.SYN_ERROR;
			else {
				s = trimMarks(expression.substring(pos+1));
				right = new Parser( s, markers, pos+1 );
			}
		}
		
	}
	
	private Marker getMarker(List<Marker> markers, int pos) {
		for (Marker m : markers) {
			if (m.pos > pos) {
				return m;
			}
		}
		return null;
	}

	public static Parser parse(String expression) {
		// Testa por erro léxico:
		if (!expression.matches("([A-Za-z0-9()\\^*/+-><=#.|])*")) 
			throw ParseException.LEX_ERROR;

		// Tratamento dos parênteses:
		if (expression.contains(")")) {
			// Remove agrupamentos das bordas
			expression = trimMarks(expression);
		}
		
		// Procura pelos operadores, por ordem de precedência menor-maior, da esqerda para direita. 
		List<Marker> markers = splitPoints(expression.toCharArray());
		
		return new Parser(expression, markers, 0);
		
	}
	
	private static List<Marker> splitPoints(char[] expression) {
		int pos = -1, priority = 10, parLevel = 0;
		List<Marker> markers = new ArrayList<Marker>();
		// Para cada caracter procura o operador de menor precedência:
		FOUND:
		for (int i = 0; i < expression.length; i++) {
			char c = expression[i];
			for (int k = 0; k < OPERATORS.length; k++) {
				char op = OPERATORS[k];
				if (op == c) {
					markers.add(new Marker(i, parLevel, PRIORITY[k]));
					priority = PRIORITY[k];
					pos = i;
//					if (priority == 1) break FOUND;
				} else if (c == '(') {
					parLevel++;
				} else if (c == ')') {
					parLevel--;
				}
			}
		}
		
		// se falta parênteses, erro:
		if (parLevel != 0) 
			throw ParseException.SYN_ERROR;
		
		// Ordena pela menor prioridade, considerando o nível dos parênteses
		Collections.sort(markers, new Comparator<Marker>() {
			@Override
			public int compare(Marker o1, Marker o2) {
				if (o1.parLevel <= o2.parLevel)
					if (o1.priority < o2.priority)
						return -1;
					else 
						return 1;
				else
					return 1;
			}
		});
		
		return markers;
	}
	
	private static String trimMarks(String sb) {
	    while (sb.length() > 2 && (sb.charAt(0) == '(' && sb.charAt(sb.length()-1) == ')')) {
	        sb = sb.substring(1,sb.length()-1);
	    }
	    return sb;
	}
	
	public String postOrder() {
		String s = "";
		s += left != null? left.postOrder() : "";
		s += right != null? right.postOrder() : "";
		return s + getRoot();
	}
	
	public String getRoot() {
		return root == '('? "" : String.valueOf(root);
	}
}

class Marker {
	public Marker(int pos, int parLevel, int priority) {
		this.pos = pos;
		this.parLevel = parLevel;
		this.priority = priority;
	}
	public int pos = 0;
	public int parLevel = 0;
	public int priority = 10;
}

class ParseException extends RuntimeException {
	
	public static final ParseException LEX_ERROR = new ParseException("Lexical Error!");
	public static final ParseException SYN_ERROR = new ParseException("Syntax Error!");
	
	public ParseException(String msg) {
		super(msg);
	}
}