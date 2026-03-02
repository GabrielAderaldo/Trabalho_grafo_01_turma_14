# 🔄 Guia de Atualização para a Equipe

Para garantir que sua branch esteja alinhada com as novas funcionalidades de Data Science e Automação, copie e cole o prompt abaixo no seu chat de IA (ChatGPT, Claude, Gemini, etc.).

---

## 📋 Prompt de Sincronização (Copie e Cole)

> "Atue como um Engenheiro de Software Sênior. Preciso atualizar minha branch local com as últimas mudanças da `main`, mas o projeto passou por uma reestruturação profunda de infraestrutura.
>
> ### 🛡️ Instruções de Segurança:
> 1. **Preserve o I/O Centralizado:** Todas as exportações agora devem passar pelo `utils.DataExporter`. Não aceite códigos que usem `PrintWriter` ou `FileWriter` diretamente nas classes de lógica.
> 2. **Atenção ao Endianness:** Os arquivos binários agora são Big-Endian. No Python, a leitura deve usar obrigatoriamente `dtype='>i4'`.
> 3. **Modo de Teste:** O `DataExporter` agora tem um `setTestMode(true)`. Garanta que os testes unitários continuem usando essa flag para salvar em `data/generated/test/`.
> 4. **Respeite o Taskfile:** A automação oficial agora prioriza o `Taskfile.yml` e os scripts em `scripts/`.
>
> ### 🛠️ O que fazer agora:
> - Me guie no comando `git fetch origin` e `git merge origin/main`.
> - Se houver conflitos no `FacebookGraph.java` ou `DataExporter.java`, analise linha por linha para garantir que a lógica de cálculo manual (requisito pedagógico) não seja substituída por métodos prontos da biblioteca.
> - Após o merge, me ajude a rodar `./scripts/bootstrap.sh` (ou .ps1) para validar se o ambiente continua íntegro.
>
> Responda em português e valide cada passo antes de prosseguir."
