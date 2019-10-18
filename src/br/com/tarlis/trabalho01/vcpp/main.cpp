#include<bits/stdc++.h>

using namespace std;

#define LL long long

LL n, m;
LL * cells;

LL equals(LL* a, LL* b) {
    for (LL i = 0; i < n; i++) {
        if (a[i] != b[i]) return 0;
    }
    return 1;
}

// Com a função index, evita-se a cópia repetitiva do vetor
// em emória, basta sabermos o índice do elemento procurado:
LL index(LL j) {
	// Encontra o índice respectivo de j no vetor "circular"
	return j < 0? n + j : (j >= n? j-n : j);
}

LL add(LL i, LL d) {
	LL sum = 0;
	// Calcula a soma dos valores
	// das células vizinhas de distância
	// -d até +d:
	for (LL j = i-d; j <= i+d; j++) {
		sum += cells[index(j)];
	}
	return sum;
}

LL addall(LL n) {
	LL sum = 0;
	for (LL j = 0; j < n; j++) {
		sum += cells[j];
	}
	return sum;
}

LL * newState(LL d) {
	LL * aux = new LL[n];
	// Faz apenas a soma da vizinhança do primeiro elemento,
	LL sum = (n > d*2)? add(0, d) : addall(n);
	aux[0] = sum % m;
	// Calcula o novo valor de cada célula
	// do vetor, porém a soma dos demais elementos é
	// Calculada sobre a soma do primeiro, evitando retrabalho.
	for (LL j = 1; j < n; j++) {
		if (n > d*2) {
			sum -= cells[index(j-1-d)];
			sum += cells[index(j+d)];
		}
		aux[j] = sum % m;
	}
	return aux;
}

void automata(LL d, LL k) {
	// Calcula o primeiro estado
	LL * aux, * gen0 = cells = newState(d);
	// Para cada passo k (menos 1):
	for (LL i = 1; i < k; i++) {
		// Calcula o novo estado:
		aux = newState(d);

		// Verifica se os valores das células dos últimos 2
		// estados são iguais, entao retorna (não muda)
		if (equals(cells, aux)) {
		    cells = aux;
			return;
		}

		// Verifica se os valores das células são iguais
		// aos do primeiro passo, então há um ciclo:
		if (equals(gen0, aux)) {
			// O resultado é o estado anterior, retorna:
			if ((k % i) == 0) return;
			// altera o valor de k para calcular apenas até o valor
			// esperado da resposta:
			k = i + (k % i);
		}

		cells = aux;
	}
}

int main()
{
	LL d, k;
	while(~scanf("%d%d%lld%lld", &n, &m, &d, &k))
	{
		cells = new LL[(LL)n];
		for(LL i=0; i<n; ++i)
		    scanf("%d", &cells[i]);

		automata(d, k);

		for(LL i=0;i<n;++i)
		{
			if(i) printf(" ");
			printf("%d", cells[i]);
		}
		printf("\n");
	}
	return 0;
}
