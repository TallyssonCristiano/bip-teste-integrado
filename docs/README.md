# Desafio Fullstack Integrado — bip-teste-integrado

## 1. Visão geral
Projeto fullstack para gerenciamento de **benefícios**. Implementação completa em camadas:
- Frontend: **Angular 16+** (componentes standalone)
- Backend: **Spring Boot (Java 17)** com Spring Data JPA/Hibernate
- Banco: **SQL Server** (base `bip_teste_integrado`, autenticação Windows)

O objetivo do desafio foi entregar uma solução funcional com:
- CRUD completo para benefícios
- Operação de transferência entre benefícios (validações e consistência)
- Integração EJB/back-end conforme enunciado (quando aplicável)
- Frontend consumindo a API e testes manuais do fluxo

Todos os requisitos do desafio foram implementados, incluindo CRUD, transferência de benefícios e integração completa front ↔ back.

---

## 2. Pré-requisitos locais
- Node.js (recomendado: v18+ ou v20)  
- Angular CLI (recomendado: v16+)  
- Java 17 (JDK)  
- Maven  
- SQL Server (Express/Developer) rodando localmente no Windows  
- (opcional) Visual Studio Code / Eclipse para edição

---

## 3. Banco de dados
**Banco:** `bip_teste_integrado`  
**Tipo de autenticação:** Windows Authentication (integrated security)

Exemplo de `application.properties` usando Windows Authentication (Microsoft JDBC Driver):

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=bip_teste_integrado;integratedSecurity=true;
# Se preferir usar SQL auth, substitua por:
# spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=bip_teste_integrado
# spring.datasource.username=sa
# spring.datasource.password=SuaSenha
spring.jpa.hibernate.ddl-auto=update
⚠️ Observação sobre Integrated Security: para integratedSecurity=true é necessário o driver Microsoft JDBC e o sqljdbc_auth.dll compatível com sua arquitetura no PATH do Windows. Se encontrar dificuldade, recomendo usar username/password para desenvolvimento local.

4. Como rodar (passo a passo)
Backend (Spring Boot)
Ajuste application.properties conforme seu ambiente (veja acima).

No diretório do backend (o repositório normalmente já contém a pasta backend ou backend-module), rode:

bash

mvn clean spring-boot:run
O backend ficará disponível em:
http://localhost:8080

Frontend (Angular)
Entre na pasta frontend:

bash

cd frontend
npm install    # só se necessário
ng serve
O frontend ficará em:
http://localhost:4200

Certifique-se de que o backend esteja rodando ANTES do frontend para evitar erros de CORS/connection refused.

5. Endpoints (API pública)
Base URL: http://localhost:8080/api/v1/beneficios

GET /api/v1/beneficios — listar todos

GET /api/v1/beneficios/{id} — obter por id

POST /api/v1/beneficios — criar

PUT /api/v1/beneficios/{id} — atualizar

DELETE /api/v1/beneficios/{id} — remover

POST /api/v1/beneficios/transfer?origemId=X&destinoId=Y&valor=Z — transferir valor

6. Testes implementados
6.1 Backend (Spring Boot)

Testes automatizados com JUnit 5 + Mockito:

BeneficioServiceTest.java:

listar() → valida retorno do repositório

transferir() → valida transferência entre benefícios, atualizando corretamente valores de origem/destino

BeneficioControllerIT.java (Integration Tests com MockMvc):

testCreateAndGetBeneficio() → cria benefício e valida GET

testUpdateBeneficio() → atualiza benefício e valida campos

testDeleteBeneficio() → remove benefício

testTransferBeneficio() → chama endpoint de transferência e verifica interação com BeneficioRemote

Todos os testes de backend foram executados e passam com sucesso.

6.2 Frontend (Angular)

Testes unitários com Jasmine + TestBed:

BeneficioService:

list() → verifica chamada GET e retorno correto

transfer() → verifica chamada POST para endpoint de transferência

HttpClient mockado com HttpTestingController

Observação: validações de entrada e regras de negócio (saldo insuficiente, origem ≠ destino, valor > 0) estão implementadas. O sistema usa @Version para optimistic locking (evita inconsistência em grande parte dos casos). Implementações de locking pessimista estavam disponíveis como alternativa, documentadas no código.

7. Melhorias sugeridas

Testes automatizados de integração adicionais

Autenticação/Autorização (JWT, roles)

Migração de banco com Flyway/Liquibase

Containerização com Docker

Observabilidade e logs estruturados

Otimizações de performance e UX

CI/CD via GitHub Actions

Testes de carga e acessibilidade