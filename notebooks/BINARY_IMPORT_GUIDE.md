# Guia: Importação de Formatos Binários no Python

Este guia explica como ler os arquivos `.bin` gerados pelo Java diretamente no seu Notebook Jupyter usando a biblioteca **NumPy**. O uso de binários é 100x mais rápido que o CSV para grafos grandes.

## 1. Importando a Lista de Arestas (`_edgelist.bin`)

Este arquivo contém pares de inteiros (v, w) de 4 bytes cada.

### Lógica de Parsing (Pseudo-código):
1. Abrir o arquivo.
2. Ler os primeiros dois inteiros (V e E).
3. Ler o restante dos dados como um array de inteiros.
4. Redimensionar o array para uma matriz com 2 colunas.

### Snippet Sugerido (NumPy):
```python
import numpy as np

# Carrega o arquivo todo como inteiros de 32 bits
data = np.fromfile('../data/generated/bin/facebook_edgelist.bin', dtype=np.int32)

V = data[0]
E = data[1]
# As arestas começam do índice 2 em diante
edges = data[2:].reshape((E, 2))

print(f"Grafo carregado: {V} vértices e {E} arestas.")
```

## 2. Importando a Matriz de Adjacência (`_adjmatrix.bin`)

Este arquivo usa o formato **Bitset**. No Python, cada byte lido contém 8 conexões.

### Dica Técnica:
Para reconstruir a matriz no Python, você deve ler o arquivo como `uint8` (bytes) e usar a função `np.unpackbits()` para transformar cada byte em 8 valores (0 ou 1).

## 3. Por que usar NumPy para isso?
- **Velocidade:** O NumPy lê os bytes diretamente para a memória C, sem passar pelo interpretador lento do Python.
- **Memória:** Você economiza GBs de RAM ao lidar com o grafo do Facebook.

---

## 🔗 Referências Úteis
- [NumPy fromfile Documentation](https://numpy.org/doc/stable/reference/generated/numpy.fromfile.html)
- [NetworkX: from_edgelist](https://networkx.org/documentation/stable/reference/generated/networkx.convert_matrix.from_edgelist.html) (Para converter o array `edges` em um objeto de Grafo do Python).
