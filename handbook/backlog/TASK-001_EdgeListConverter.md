# TASK-001: Conversor de Lista de Arestas para Formato algs4

## Descrição
A classe `Graph` da biblioteca `algs4` exige um formato de arquivo específico para inicialização via construtor `Graph(In in)`. Os datasets do SNAP (como o Facebook Union) fornecem apenas as arestas, sem os metadados iniciais (V e E).

Esta tarefa consiste em criar um script utilitário que lê uma lista de arestas bruta da `stdin` e gera a saída formatada na `stdout`.

## Formato de Entrada (SNAP)
```text
0 1
1 2
2 5
```

## Formato de Saída (algs4)
```text
6      <-- V (Maior ID + 1)
3      <-- E (Total de arestas)
0 1
1 2
2 5
```

## Requisitos Técnicos
1. **Nome da Classe:** `EdgeListConverter`.
2. **Entrada:** Deve utilizar `Scanner(System.in)` ou similar para ler a lista de arestas até o fim do arquivo (EOF).
3. **Lógica de Vértices:** Percorra todas as arestas e identifique o maior valor numérico (`maxID`). O número de vértices `V` será `maxID + 1`.
4. **Lógica de Arestas:** Conte o número total de linhas/pares lidos para determinar `E`.
5. **Armazenamento:** Como precisamos imprimir `V` e `E` *antes* das arestas, você deve armazenar as arestas temporariamente (ex: `ArrayList<Integer>` ou um `StringBuilder` gigante) enquanto processa o arquivo.
6. **Saída:** Utilize `System.out.println`.
7. **Feedback Visual:** Implemente um indicador de progresso no `System.err` para arquivos grandes (ex: a cada 10.000 linhas), usando `\r` para manter a barra na mesma linha.

## Critérios de Aceite
- O comando `java EdgeListConverter < data/facebook/0.edges` deve produzir uma saída válida para a `algs4`.
- O teste unitário `EdgeListConverterTest.java` deve passar com sucesso.
- Deve lidar com arquivos grandes (ex: `facebook_union.txt` com > 170k arestas) sem estourar a memória.
