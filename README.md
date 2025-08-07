# Batalha Naval Master

**MATÉRIA:** `PDSI I - UNIVERSIDADE FEDERAL DE UBERLÂNDIA`


## Sobre o Projeto
Este é um sistema completo de Batalha Naval, desenvolvido em Java (back-end) com Spark Java, autenticação via Firebase, e front-end em JavaScript. O projeto suporta partidas contra o computador, histórico de partidas, autenticação de usuários e interface web moderna.

## Pré-requisitos
Para executar e desenvolver este projeto, você precisará dos seguintes pré-requisitos:

- **Java 24** (compilação e execução)
- **Maven 3.8+** (gerenciamento de dependências e build)
- **Conta no Firebase** (para autenticação e banco de dados)
- **Node.js** e **npm** (opcional, para testes front-end com Jest)

> As versões de Java e Maven são baseadas no arquivo `pom.xml` e no workflow de CI. O projeto utiliza Java 24 (Temurin) e Maven para build e testes.

## Ferramentas, Bibliotecas e Tecnologias Utilizadas

- **Linguagem principal:** Java 24
- **Gerenciador de dependências:** Maven
- **Framework Web:** Spark Java 2.9.4
- **Autenticação e Banco de Dados:** Firebase Admin SDK 9.1.1 (Firestore)
- **Serialização JSON:** Gson 2.10.1
- **Logging:** SLF4J Simple 1.7.36
- **Testes Java:** JUnit Jupiter 5.9.2, Mockito 5.2.0
- **Testes Front-end:** Jest (JavaScript, opcional)
- **Front-end:** HTML, CSS, JavaScript (puro)

> Todas as versões das bibliotecas Java estão especificadas no `pom.xml`. O front-end utiliza apenas recursos nativos do navegador e testes opcionais com Jest.

## Como executar o projeto

### Windows
1. Abra o Prompt de Comando ou PowerShell na raiz do projeto.
2. Execute:
   ```
   mvn clean package
   java -cp target/BatalhaNaval-1.0-SNAPSHOT.jar battleship.application.MainApplication
   ```

### macOS
1. Abra o Terminal na raiz do projeto.
2. Execute:
   ```
   mvn clean package
   java -cp target/BatalhaNaval-1.0-SNAPSHOT.jar battleship.application.MainApplication
   ```

O servidor estará disponível em `http://localhost:8080`.

## Como executar os testes

### Testes back-end (Java)
No terminal (Windows ou Mac), execute:
```
mvn test
```
Os resultados aparecerão no console e em `target/surefire-reports/`.

## Como simular testes falhos ou ignorados

### Para forçar uma falha em um teste Java
Altere um valor esperado em um teste, por exemplo em `GameServiceImplTest.java`:
```java
@Test
public void testInitGame() {
    InitResponse resp = service.initGame(new InitRequest(7));
    // Espera 7, mas força erro esperando 8
    assertEquals(8, resp.getSize());
}
```

### Para pular (ignorar) um teste Java
Adicione a anotação `@Disabled`:
```java
import org.junit.jupiter.api.Disabled;

@Disabled("Ignorado para demonstração")
@Test
public void testInitGame() {
    // ...
}
```

### Para forçar erro em um teste Java
Lance uma exceção manualmente:
```java
@Test
public void testForcaErro() {
    throw new RuntimeException("Erro proposital");
}
```

## Integração Contínua (CI)

Este projeto utiliza GitHub Actions para garantir a qualidade do código a cada commit e pull request. O workflow de CI:
- Faz checkout do repositório
- Configura o Java 24
- Realiza build e executa todos os testes com Maven
- Só permite o build se todos os testes passarem

O arquivo de configuração está em `.github/workflows/ci.yml`.

## O que cada teste faz

### Testes de domínio
- **BoardTest**: Testa posicionamento de navios, sobreposição, limites do tabuleiro, ataques (acerto/erro) e verificação de fim de jogo.
- **ShipTest**: Testa geração de coordenadas do navio (horizontal/vertical), ocupação, registro de acertos e afundamento.
- **CoordinateTest**: Testa igualdade, hashCode e métodos getters da coordenada.
- **ShipFactoryTest**: Testa se a fábrica cria navios corretamente com o tamanho esperado.

### Testes de exceção
- **BadRequestExceptionTest**: Testa se a exceção retorna a mensagem correta.

### Testes de serviço
- **GameServiceImplTest**: Testa inicialização do jogo, posicionamento de navio do jogador (válido e inválido), ataques do jogador e do computador.

## Banco de Dados

O modelo de banco de dados noSQL do Batalha Naval Master foi desenhado para ser flexível, escalável e aderente aos requisitos do projeto, sendo totalmente compatível com o Firestore do Firebase. Cada coleção representa um conjunto de documentos, e os campos podem ser tipos primitivos, arrays ou referências por ID, seguindo o padrão do Firestore.

### Estrutura e Implementação Atual

- **Coleções já implementadas:**
  - **users:** cada usuário é um documento nesta coleção.
    - **games:** subcoleção dentro de cada usuário, onde são armazenados os históricos de partidas daquele usuário (cada partida é um documento com os dados do jogo, resultado, timestamp, etc).

- **Coleções previstas para próximas sprints (ainda não implementadas):**
  - **Games:** coleção global de partidas.
  - **GameHistory:** histórico detalhado de partidas global.
  - **Invitations:** convites e agendamento de partidas.
  - **GameSettings:** preferências do usuário.
  - **ChatMessages:** mensagens trocadas durante as partidas (chat in-game).
  - **Friends:** gerenciamento de amizades e solicitações.
  - **AISettings:** níveis e comportamentos da IA.
  - **Reports:** relatórios e feedbacks de partidas.

### Esquema Gráfico Completo (dbdiagram.io)

O esquema abaixo representa **todas as coleções e relacionamentos previstos nos requisitos do projeto**, incluindo as já implementadas e as planejadas para o futuro. Use este modelo para documentação, discussão com o time e planejamento de evolução do banco. Ele pode ser importado no [dbdiagram.io](https://dbdiagram.io):

```dbml
Table Users {
  userId string [pk]
  username string
  photoUrl string
  winCount int
  loseCount int
  gamesPlayed int
  friends string[]
  lastLogin datetime
}

Table Games {
  gameId string [pk]
  player1Id string [ref: > Users.userId]
  player2Id string [ref: > Users.userId]
  player1MoveHistory string[]
  player2MoveHistory string[]
  status string
  winnerId string [ref: > Users.userId]
  boardSize int
  turnCount int
  gameStart datetime
  gameEnd datetime
  isOnline boolean
  difficultyLevel string
  player1ShipPositions string[]
  player2ShipPositions string[]
}

Table GameHistory {
  gameHistoryId string [pk]
  gameId string [ref: > Games.gameId]
  player1Id string [ref: > Users.userId]
  player2Id string [ref: > Users.userId]
  result string
  moves string[]
  finalBoard string
  gameDate datetime
}

Table Invitations {
  invitationId string [pk]
  senderId string [ref: > Users.userId]
  receiverId string [ref: > Users.userId]
  status string
  gameId string [ref: > Games.gameId]
  inviteTime datetime
  responseTime datetime
}

Table GameSettings {
  userId string [pk, ref: > Users.userId]
  defaultBoardSize int
  preferredDifficulty string
  autoShipPlacement boolean
  notificationsEnabled boolean
}

Table ChatMessages {
  messageId string [pk]
  gameId string [ref: > Games.gameId]
  senderId string [ref: > Users.userId]
  receiverId string [ref: > Users.userId]
  message string
  timestamp datetime
}

Table Friends {
  userId string [pk, ref: > Users.userId]
  friendsList string[]
  requestSent string[]
  requestReceived string[]
}

Table AISettings {
  aiLevel string [pk]
  aiBehavior string
}

Table Reports {
  reportId string [pk]
  gameId string [ref: > Games.gameId]
  report string
  timestamp datetime
}
```

> **Nota:** Apenas a coleção `users` (com subcoleção `games`) está implementada atualmente. As demais fazem parte do roadmap aprovado e serão implementadas conforme a evolução do projeto. Algumas tabelas/coleções decididas aqui ainda poderão ser alteradas ou até não estar presentes na versão final da entrega, dependendo das decisões de implementação e priorização ao longo do desenvolvimento. Além disso, a subcoleção `games` não está totalmente implementada até o momento desta apresentação, podendo sofrer mudanças ou expansões.

### Observações Importantes
- O modelo cobre as funcionalidades essenciais atuais (autenticação e histórico de partidas por usuário).
- O uso do padrão noSQL permite adicionar coleções e campos sem grandes refatorações, garantindo flexibilidade e escalabilidade.
- O modelo é totalmente compatível com o Firestore do Firebase, bastando criar as coleções e documentos conforme a estrutura acima.

## Licença
Este projeto está sob a licença MIT.
