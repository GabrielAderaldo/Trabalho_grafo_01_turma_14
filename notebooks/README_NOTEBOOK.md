# Roteiro de Estudo: Análise Científica no Notebook

Este documento orienta a análise estatística do dataset do Facebook usando Python.

## 📚 Bibliotecas Necessárias
- **Pandas**: Para manipulação do CSV gerado pelo Java (`pd.read_csv`).
- **Matplotlib/Seaborn**: Para visualização de dados.
- **Powerlaw**: Biblioteca específica para testar se uma distribuição segue a Lei de Potência.
- **NetworkX**: Para conferência de métricas topológicas.

## 🔬 Tópicos de Pesquisa Teórica
Para fundamentar o relatório, a equipe deve pesquisar:
1.  **Redes de Escala Livre (Scale-Free Networks)**: Leiam o capítulo 4 do livro "Network Science" de Albert-László Barabási.
2.  **Visualização Log-Log**: Por que usamos escalas logarítmicas para identificar leis de potência?
3.  **Ajuste de Lei de Potência**: Entendam o que é o expoente $\gamma$ (Alpha) e o valor $x_{min}$.

## 📈 Roteiro de Análise (Passo a Passo)
1.  **Carga**: Importar os graus brutos por vértice.
2.  **Agregação**: Calcular a frequência $P(k)$ de cada grau $k$.
3.  **Plot Linear**: Criar um histograma simples e observar a "cauda longa".
4.  **Plot Log-Log**: Aplicar $\log$ em ambos os eixos. Se os dados formarem uma reta descendente, há indícios de escala livre.
5.  **Ajuste**: Usar a função de `Fit` da biblioteca `powerlaw` para calcular o coeficiente.
