# Batalha Naval Master

**MATÉRIA:** `PDSI I - UNIVERSIDADE FEDERAL DE UBERLÂNDIA`


## Sobre o Projeto
Este é um sistema completo de Batalha Naval, desenvolvido em Java (back-end) com Spark Java, autenticação via Firebase, e front-end em JavaScript. O projeto suporta partidas contra o computador, histórico de partidas, autenticação de usuários e interface web moderna.

## Pré-requisitos
- Java 17 ou superior
- Maven 3.8+ instalado
- (Opcional) Node.js e npm para testes front-end
- Conta no Firebase (para autenticação)

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

### Testes front-end (JavaScript)
Se desejar rodar os testes do front-end (Jest):
1. Instale o Jest globalmente ou no projeto:
   ```
   npm install --global jest
   # ou
   npm install --save-dev jest
   ```
2. Execute:
   ```
   npx jest
   ```

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

### Para simular falha em teste front-end (Jest)
Altere um valor esperado em `api.test.js`:
```js
test('deve falhar propositalmente', () => {
    expect(1 + 1).toBe(3);
});
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

### Testes front-end (Jest)
- **api.test.js**: Testa a função `addHistory` para garantir que lida corretamente com respostas de sucesso e erro do back-end.

## Estrutura do Projeto
- `src/main/java/battleship/`: Código-fonte Java (domínio, serviços, controllers, filtros, exceções)
- `src/main/resources/public/`: Front-end (HTML, CSS, JS)
- `src/test/java/`: Testes unitários Java
- `src/test/js/`: Testes unitários front-end (Jest)
- `target/`: Saída de build e relatórios de testes

## Observações
- Os testes unitários estão localizados em `src/test/java/` (Java) e `src/test/js/` (JavaScript).
- Para dúvidas ou problemas, consulte os arquivos de teste e os relatórios em `target/surefire-reports/`.
- O arquivo `serviceAccountKey.json` do Firebase deve ser colocado em `src/main/resources/` (não versionado).
- O projeto já está configurado para rodar em ambientes Windows, Mac e Linux.
- Para autenticação, configure seu projeto Firebase e atualize as credenciais conforme necessário.

## Licença
Este projeto está sob a licença MIT.

---

