# GerenciadorTarefa
Um simples e pratico gerenciador de tarefas 


Gerenciador de Tarefas - Projeto em Java
Este é um gerenciador de tarefas simples desenvolvido em Java, utilizando a biblioteca Swing para a interface gráfica. O objetivo deste projeto é ajudar o usuário a organizar suas tarefas com funcionalidades adicionais como notificações, agrupamento por data, tags, exportação de tarefas e muito mais.

Funcionalidades
1. Cadastro e Visualização de Tarefas
O aplicativo permite ao usuário adicionar novas tarefas com informações como título, descrição, data de vencimento e tags. As tarefas podem ser visualizadas em uma lista, com a opção de marcar como concluídas.

2. Busca por Tarefa
O sistema permite que o usuário pesquise tarefas de acordo com o título, descrição ou data de vencimento. A busca é realizada em tempo real conforme o texto é digitado na barra de pesquisa.

3. Agrupar Tarefas por Data
As tarefas são agrupadas e exibidas separadas por data de vencimento. Isso facilita a visualização e organização das tarefas para o dia específico.

4. Lembrete por Notificação
O aplicativo verifica automaticamente se o prazo de alguma tarefa está se aproximando. Caso o prazo esteja a menos de 30 minutos do vencimento, uma notificação será exibida alertando o usuário sobre a proximidade do vencimento.

5. Edição de Tarefas
Embora o código atual seja mais focado em adicionar tarefas, a funcionalidade de edição pode ser facilmente implementada com um JOptionPane para que o usuário altere o título, a descrição, ou a data de vencimento de uma tarefa.

6. Exportar Tarefas para Arquivo CSV
O usuário pode exportar todas as tarefas para um arquivo CSV. Isso é útil para manter um backup ou imprimir as tarefas. As tarefas exportadas incluem título, descrição, data de vencimento, tags e status (concluída ou pendente).

7. Tags para Tarefas
Cada tarefa pode ser associada a tags, que ajudam na filtragem e organização das tarefas. Por exemplo, uma tarefa pode ter tags como "Trabalho", "Pessoal", "Urgente", facilitando a localização e organização das tarefas.

8. Histórico de Conclusões
O sistema mantém um histórico de tarefas concluídas, com a data e hora de conclusão. Isso permite que o usuário acompanhe suas tarefas realizadas.

9. Modo Noturno
O aplicativo oferece a opção de alternar para o modo noturno, o que altera as cores da interface para tornar a leitura mais confortável em ambientes com pouca luz.

10. Prioridade Automática
O sistema atribui prioridade automaticamente às tarefas com base no tempo restante até o vencimento. Tarefas com prazo mais próximo terão maior prioridade.

Tecnologias Utilizadas
Java: A principal linguagem de programação utilizada.
Swing: Biblioteca gráfica utilizada para a criação da interface do usuário.
JOptionPane: Para interações simples com o usuário, como inserção de dados e exibição de alertas.
Requisitos
JDK 8 ou superior: Necessário para compilar e executar o código Java.
IDE recomendada: IntelliJ IDEA, Eclipse ou Visual Studio Code (com suporte Java).
Como Executar
Clone o repositório:

bash
Copiar código
git clone https://github.com/usuario/gerenciador-de-tarefas.git
Compile o código (caso esteja utilizando linha de comando):

bash
Copiar código
javac TaskManager.java
Execute o programa:

bash
Copiar código
java TaskManager
Interaja com a interface:

Adicione novas tarefas clicando no botão "Adicionar Tarefa".
Pesquise tarefas usando a barra de busca.
Marque as tarefas como concluídas.
Mude para o modo noturno usando o botão correspondente.
Funcionalidade de Exportação para CSV
Para exportar suas tarefas para um arquivo CSV:

Clique no botão de exportação (implementado no código).
O arquivo será gerado no diretório onde o programa está sendo executado como tarefas.csv.
O arquivo CSV gerado terá o seguinte formato:

r
Copiar código
Título,Descrição,Data de Vencimento,Tags,Status
Tarefa 1,Descrição 1,12/12/2024,Trabalho,Concluída
Tarefa 2,Descrição 2,15/12/2024,Pessoal,Pendente
Exemplo de Interface
A interface gráfica foi projetada para ser intuitiva, com:

Uma lista de tarefas exibindo o título, status (concluída ou pendente) e data de vencimento.
Barra de pesquisa para filtrar tarefas.
Botões para adicionar, exportar e alternar para o modo noturno.
Notificações automáticas quando o prazo de uma tarefa se aproxima.
Contribuição
Sinta-se à vontade para contribuir com melhorias ou corrigir problemas. Para isso, basta seguir os passos abaixo:

Faça um fork deste repositório.
Crie uma branch para suas modificações.
Envie um pull request com uma descrição clara das mudanças feitas.
Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.
