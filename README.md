# 🌱 AgroSat — API REST

API REST principal do sistema **AgroSat**, desenvolvida em Java com Spring Boot.

O AgroSat é um sistema de monitoramento agrícola via satélite que transforma dados climáticos e satelitais em inteligência para o produtor rural, calculando o índice de saúde da lavoura (NDVI) e gerando alertas e recomendações automáticas.

---

## 👥 Integrantes

| Nome                      | RM     |
| ------------------------- | ------ |
| Gustavo Barrios de Araújo | 563358 |
| Matheus Almeida Ribeiro   | 562980 |
| Phietro Solon Oliveira    | 563842 |

---

## 🛠️ Tecnologias utilizadas

* Java 21
* Spring Boot 3.3.0
* Spring Data JPA — persistência e mapeamento objeto-relacional
* Spring Validation — Bean Validation nos DTOs
* Spring HATEOAS — links de hipermídia nas respostas
* Spring Cache — cache de requisições com `@Cacheable`
* Spring WebFlux / WebClient — consumo das APIs externas (NASA POWER e Open-Meteo)
* Oracle Database — banco de dados relacional
* SpringDoc OpenAPI (Swagger UI) — documentação interativa da API
* Gradle — gerenciador de build

---

## 🗄️ Banco de dados

O projeto utiliza o banco Oracle da FIAP. As tabelas já estão criadas via scripts DDL da disciplina de Banco de Dados. O JPA apenas mapeia as entidades existentes (`ddl-auto=none`).

### Tabelas utilizadas

* `TB_USUARIO`
* `TB_PRODUTOR`
* `TB_COOPERATIVA`
* `TB_PROPRIEDADE`
* `TB_LEITURA_SATELITAL`
* `TB_ALERTA`
* `TB_RECOMENDACAO`

---

## ☁️ Deploy

A aplicação encontra-se publicada na Microsoft Azure e pode ser acessada através dos links abaixo:

### Swagger UI

http://40.123.251.32:8080/swagger-ui/index.html

### OpenAPI JSON

http://40.123.251.32:8080/v3/api-docs

O ambiente online permite testar os endpoints da API sem necessidade de executar o projeto localmente.

---

## ⚙️ Como executar

### Pré-requisitos

* Java 21+
* Gradle 8+
* Acesso ao banco Oracle da FIAP

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/agrosat.git
cd agrosat
```

### 2. Configure o banco de dados

Edite o arquivo `src/main/resources/application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
spring.datasource.username=SEU_RM
spring.datasource.password=SUA_SENHA
```

### 3. Execute o projeto

```bash
./gradlew bootRun
```

A aplicação sobe na porta **8080**.

---

## 📖 Documentação da API (Swagger)

### Ambiente Local

```text
http://localhost:8080/swagger-ui/index.html
```

### Ambiente Publicado (Azure)

```text
http://40.123.251.32:8080/swagger-ui/index.html
```

Todos os endpoints estão documentados com descrições, parâmetros, exemplos de requisição e respostas.

---

## 🔀 Rotas da API

### 👨‍🌾 Produtores — `/produtores`

| Método | Rota                       | Descrição                  | Status    |
| ------ | -------------------------- | -------------------------- | --------- |
| POST   | `/produtores`              | Cadastra novo produtor     | 201       |
| GET    | `/produtores/{id}`         | Busca produtor por id      | 200 / 404 |
| GET    | `/produtores?pageNumber=0` | Lista produtores paginados | 200 / 404 |
| PUT    | `/produtores/{id}`         | Atualiza dados do produtor | 200 / 404 |
| DELETE | `/produtores/{id}`         | Remove produtor            | 204 / 404 |

### 🌾 Propriedades — `/propriedades`

| Método | Rota                         | Descrição                    | Status    |
| ------ | ---------------------------- | ---------------------------- | --------- |
| POST   | `/propriedades`              | Cadastra nova propriedade    | 201       |
| GET    | `/propriedades/{id}`         | Busca propriedade por id     | 200 / 404 |
| GET    | `/propriedades?pageNumber=0` | Lista propriedades paginadas | 200 / 404 |
| PUT    | `/propriedades/{id}`         | Atualiza propriedade         | 200 / 404 |
| DELETE | `/propriedades/{id}`         | Remove propriedade           | 204 / 404 |

### 🚨 Alertas — `/alertas`

| Método | Rota                                     | Descrição                        | Status    |
| ------ | ---------------------------------------- | -------------------------------- | --------- |
| POST   | `/alertas`                               | Registra novo alerta             | 201       |
| GET    | `/alertas/{id}`                          | Busca alerta por id              | 200 / 404 |
| GET    | `/alertas/propriedade/{id}?pageNumber=0` | Lista alertas de uma propriedade | 200 / 404 |
| PATCH  | `/alertas/{id}/resolver`                 | Marca alerta como resolvido      | 200 / 404 |
| DELETE | `/alertas/{id}`                          | Remove alerta                    | 204 / 404 |

### 🛰️ Satélite — `/satelite`

| Método | Rota                                             | Descrição                      | Status    |
| ------ | ------------------------------------------------ | ------------------------------ | --------- |
| GET    | `/satelite/propriedade/{id}/clima`               | Consulta dados climáticos      | 200 / 404 |
| POST   | `/satelite/propriedade/{id}/saude`               | Calcula NDVI e salva leitura   | 200 / 404 |
| GET    | `/satelite/clima?latitude={lat}&longitude={lon}` | Consulta clima por coordenadas | 200       |

---

## 🧪 Como testar com Postman

### Importar a coleção

1. Abra o Postman
2. Clique em Import
3. Importe o arquivo:

```text
documentos/agrosat-postman-collection.json
```

### Fluxo sugerido para testes

1. Cadastrar produtor
2. Cadastrar propriedade
3. Consultar clima
4. Calcular saúde da lavoura
5. Criar alerta
6. Listar alertas
7. Resolver alerta

---

## 📐 Arquitetura

```text
agrosat/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
├── mapper/
└── exception/
```

### Estrutura das camadas

* Controller → entrada HTTP e endpoints REST
* Service → regras de negócio
* Repository → persistência de dados
* Model → entidades JPA
* DTO → objetos de transferência
* Mapper → conversão Entity ↔ DTO
* Exception → tratamento global de erros

### Integrações externas

* NASA POWER API
* Open-Meteo API

O Open-Meteo atua como fallback automático caso a NASA POWER esteja indisponível.

---

## 🚀 Diferenciais do Projeto

* Monitoramento agrícola via satélite
* Cálculo automático de NDVI
* Geração de alertas inteligentes
* Recomendações automáticas ao produtor
* Integração com APIs climáticas externas
* Cache para otimização de desempenho
* API REST documentada com Swagger
* Deploy em ambiente cloud (Azure)
* Arquitetura em camadas seguindo boas práticas Spring Boot

---

## ✅ Requisitos técnicos atendidos

* [x] CRUD completo (GET, POST, PUT e DELETE)
* [x] Bean Validation nos DTOs
* [x] Paginação e ordenação
* [x] Busca por parâmetros
* [x] Cache com Spring Cache
* [x] Tratamento global de exceções
* [x] DTOs utilizando Records Java
* [x] HATEOAS
* [x] Swagger/OpenAPI documentado
* [x] Relacionamentos JPA
* [x] Integração com APIs externas via WebClient
* [x] Spring Data JPA Query Methods
* [x] Deploy em nuvem (Microsoft Azure)

---

## 📌 Objetivo do Projeto

O AgroSat busca apoiar produtores rurais na tomada de decisão através da análise automática de indicadores climáticos e satelitais, permitindo identificar riscos agrícolas antecipadamente e melhorar a produtividade das lavouras.
