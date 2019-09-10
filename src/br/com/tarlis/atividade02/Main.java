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
//		long startTime = System.nanoTime();

		// Enquanto houver entrada:
		while (in.ready() && (input = in.readLine()) != null) {
			
			// Impressão do resultado:
			lexsim(input);
			
		}
		
		// Contador de Tempo (Comentado)
//		long endTime   = System.nanoTime();
//		System.out.println("Total Time: " + ((endTime - startTime)*Math.pow(10, -9)));
		
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
		} 
		// Tratamento de erros do Parser
		catch (ParseException e) {
			System.out.println(e.getMessage());
		}
	}

}

/**
 * Classe Parser - Transforma a expressão em uma árvore binária de operações.
 * 
 * */
class Parser {
	
	// Operadores
	protected static final char[] OPERATORS = new char[] {
			'|', '.', '#', '=', '<', '>', '-', '+', '/', '*', '^' };
	
	// Ordem de precedência (respectivamente em OPERATORS)
	protected static final int[] PRIORITY = new int[] {
			1,	  2,   3, 	3, 	 3,   3,   4,   4,   5,   5,   6  };
	
	// Estruturas da árvore binária
	protected char root;
	protected Parser right = null;
	protected Parser left = null;
	
	/**
	 *  Método construtor funciona recursivamente criando novas instâncias de classe
	 *  dividindo a expressão.
	 *  
	 */
	private Parser(String expression, int increment, List<Marker> markers) {
		// Testa para saber se a expressão está mal formatada (com parênteses)
		String s = expression.replaceAll("[()]", "");
		if (expression.length() <= 0) 
			throw ParseException.SYN_ERROR();
		
		if (s.length() == 1) { 
			// Caso 1: se removendo os parênteses 
			// resta apenas um caractere, ele se torna raiz.
			root = s.charAt(0);
			return;
		} else if (!markers.isEmpty()) {
			// Caso 2: a expressão contém vários caracteres e, 
			// portanto, outros operadores. Então,
			// Tira um operador do topo (como uma Pilha)
			Marker m = markers.get(0);
			markers.remove(0);
			
			// O caractere da posição marcada é um operador
			// Logo, ele se torna raíz
			root = expression.charAt(m.pos);
			// Então dividimos a expressão em duas partes:
			// Esquerda - caracteres antes da posição
			// Direita  - caracteres depois da posição

			// Monta a sub árvore esquerda:
			if (m.pos <= 0) // Garantir que a expressão à esquerda exite
				throw ParseException.SYN_ERROR();
			else {
				// Chama recursivamente uma nova sub árvore com a parte
				// Esquerda, e a pilha apenas dos marcadores da parte esquerda 
				left = new Parser( expression.substring(0, m.pos), 0,
					updateMarkers(markers, m.pos, true));
			}
			
			// Monta a sub árvore direita:
			if (m.pos+1 >= expression.length()) // Garantir que a expressão à direita exite
				throw ParseException.SYN_ERROR();
			else {
				// Chama recursivamente uma nova sub árvore com a parte
				// Direita, e a pilha apenas dos marcadores da parte direita
				// (como a String é "cortada", atualizamos os índices dos marcadores)
				right = new Parser( expression.substring(m.pos+1), m.pos,
					updateMarkers(markers, m.pos+1, false));
			}
		} else // Qualquer outro caso é erro Sintático.
			throw ParseException.SYN_ERROR();
		
	}
	
	/**
	 * Este método separa divide a pilha de marcadores em duas, na primeira chamada
	 * left == true e este monta a pilha com os marcadores à esquerda da expressão e, quando
	 * left == false e este monta a pilha com os marcadores à direita da expressão.
	 */
	private List<Marker> updateMarkers(List<Marker> markers, int pos, boolean left) {
		List<Marker> ls = new ArrayList<Marker>();
		// Atualiza markers
		if (markers != null) {
			for (Marker m : markers) {
				if ((left && m.pos < pos) || (!left && m.pos > pos)) {
					// Atualiza as posições quando monta a pilha da esquerda
					m.pos = left? m.pos : m.pos - pos; 
					ls.add(m);
				}
			}
		}
		return ls;
	}
	
	/** 
	 * Impressão é Pós-Ordem
	 */
	public String postOrder() {
		String s = "";
		s += left != null? left.postOrder() : "";
		s += right != null? right.postOrder() : "";
		return s + root;
	}

	/** 
	 * Método auxiliar, para a conversão da String em árvore
	 * (não faz parte das instâncias)
	 */ 
	public static Parser parse(String expression) {
		// Testa por erro léxico:
		if (!expression.matches("([A-Za-z0-9()\\^*/+-><=#.|])*")) 
			throw ParseException.LEX_ERROR();

		// Procura pelos operadores, por ordem de precedência menor-maior, da esqerda para direita. 
		List<Marker> markers = splitPoints(expression.toCharArray());
		
		try {
			return new Parser(expression, 0, markers);
		} catch (Exception e) {
			// Qualquer outro erro gerado é possivelmente causado por má formção
			throw ParseException.SYN_ERROR();
		}
		
	}
	
	
	/**
	 * Este método cria a pilha de prioridades dos operadores.
	 * A implementação é de uma lista, pois fazemos ordenação.
	 * 
	 * @param expression Expressão matemática
	 * @return Lista das posições dos marcadores na String.
	 */
	private static List<Marker> splitPoints(char[] expression) {
		// Variável que indica se entrou em algum parênteses
		int parLevel = 0;
		// Lista a ser preenchida com os marcadores das posições dos operadores
		List<Marker> markers = new ArrayList<Marker>();
		// Percorremos a String da direita para a esquerda
		// Para cada caractere procura o operador de menor precedência:
		for (int i = expression.length-1; i >= 0; i--) {
			char c = expression[i];
			if (c == ')') { // Quando entra em um parênteses, sobre um nível
				parLevel++;
			} else if (c == '(') {// Quando sai de um parênteses, desce um nível
				parLevel--;
			} else // Vê se o caractere é um operador e adiciona um marcador à lista
				for (int k = 0; k < OPERATORS.length; k++) {
					char op = OPERATORS[k];
					if (op == c) {
						markers.add(new Marker(i, parLevel, PRIORITY[k]));
						break;
					}
				}
		}
		
		// Se faltou algum parênteses, erro:
		if (parLevel != 0) 
			throw ParseException.SYN_ERROR();
		
		// Ordena pela menor prioridade, considerando o nível dos parênteses
		Collections.sort(markers, new Comparator<Marker>() {
			@Override
			public int compare(Marker o1, Marker o2) {
				if (o1.parLevel == o2.parLevel)
					return o1.priority - o2.priority;
				else
					return o1.parLevel - o2.parLevel;
			}
		});
		
		return markers;
	}
	
}

/**
 * Classe que representa o marcador
 * 
 */
class Marker {
	public Marker(int pos, int parLevel, int priority) {
		this.pos = pos; 
		this.parLevel = parLevel; 
		this.priority = priority; 
	}
	public int pos 		= 0;  // Posição do caratere na String
	public int parLevel = 0;  // Nível do parênteses que o operador está
	public int priority = 10; // Prioridade do operador
}

/**
 * Classe de Erros do Parser
 */
class ParseException extends RuntimeException {
	
	public ParseException(String msg) {
		super(msg);
	}
	
	public static ParseException LEX_ERROR() {
		return new ParseException("Lexical Error!");
	}
	
	public static ParseException SYN_ERROR() {
		return new ParseException("Syntax Error!");
	}
}
