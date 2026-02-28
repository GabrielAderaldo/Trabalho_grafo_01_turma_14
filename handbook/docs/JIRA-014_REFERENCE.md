# Referência de Implementação: JIRA-014 (Exportação Crua)

Substitua o método anterior por esta versão que exporta apenas os dados brutos de cada vértice.

```java
    /**
     * Exporta os graus de cada vértice sem tratamento estatístico.
     * Obriga o tratamento manual via ferramentas externas.
     */
    public void exportRawVertexDegrees(String path) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File(path))) {
            writer.println("Vertice,Grau"); // Header
            
            java.util.stream.IntStream.range(0, G.V()).forEach(v -> {
                // Pega o grau bruto direto da estrutura do grafo
                writer.println(v + "," + G.degree(v));
            });
            
            System.out.println("Dados crus exportados para: " + path);
        } catch (java.io.IOException e) {
            System.err.println("Erro ao exportar dados crus: " + e.getMessage());
        }
    }
```
