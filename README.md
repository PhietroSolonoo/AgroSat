# 🌱 AgroSat — API REST

API REST principal do sistema **AgroSat**, desenvolvida em Java com Spring Boot 

O AgroSat é um sistema de monitoramento agrícola via satélite que transforma dados climáticos e satelitais em inteligência para o produtor rural, calculando o índice de saúde da lavoura (NDVI) e gerando alertas e recomendações automáticas.

---

## 👥 Integrantes

| Nome | RM |
|------|----|
| *Gustavo Barrios de Araújo* | *563358* |
| *Matheus Almeida Ribeiro* | *562980* |
| *Phietro Solon Olveira* | *563842* |

---

## 🛠️ Tecnologias utilizadas

- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Data JPA** — persistência e mapeamento objeto-relacional
- **Spring Validation** — Bean Validation nos DTOs
- **Spring HATEOAS** — links de hipermídia nas respostas
- **Spring Cache** — cache de requisições com `@Cacheable`
- **Spring WebFlux / WebClient** — consumo das APIs externas (NASA POWER e Open-Meteo)
- **Oracle Database** — banco de dados relacional
- **SpringDoc OpenAPI (Swagger UI)** — documentação interativa da API
- **Gradle** — gerenciador de build

---

## 🗄️ Banco de dados

O projeto utiliza o banco **Oracle** da FIAP. As tabelas já estão criadas via scripts DDL da disciplina de Banco de Dados. O JPA apenas mapeia as entidades existentes (`ddl-auto=none`).

**Tabelas utilizadas:**
- `TB_USUARIO`
- `TB_PRODUTOR`
- `TB_COOPERATIVA`
- `TB_PROPRIEDADE`
- `TB_LEITURA_SATELITAL`
- `TB_ALERTA`
- `TB_RECOMENDACAO`

---

## ⚙️ Como executar

### Pré-requisitos

- Java 21+
- Gradle 8+
- Acesso ao banco Oracle da FIAP

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

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

Todos os endpoints estão documentados com descrição, parâmetros e exemplos de resposta.

---

## 🔀 Rotas da API

### 👨‍🌾 Produtores — `/produtores`

| Método | Rota | Descrição | Status |
|--------|------|-----------|--------|
| POST | `/produtores` | Cadastra novo produtor | 201 |
| GET | `/produtores/{id}` | Busca produtor por id | 200 / 404 |
| GET | `/produtores?pageNumber=0` | Lista produtores paginados | 200 / 404 |
| PUT | `/produtores/{id}` | Atualiza dados do produtor | 200 / 404 |
| DELETE | `/produtores/{id}` | Remove produtor | 204 / 404 |

**Exemplo de body para POST/PUT:**
```json
{
  "nome": "João da Silva",
  "email": "joao@email.com",
  "senha": "senha123",
  "telefone": "11999998888",
  "cpf": "123.456.789-00",
  "dataNascimento": "1985-06-15",
  "logradouro": "Rua das Flores",
  "numero": "123",
  "bairro": "Centro",
  "cidade": "Ribeirão Preto",
  "estado": "SP",
  "cep": "14010-000"
}
```

---

### 🌾 Propriedades — `/propriedades`

| Método | Rota | Descrição | Status |
|--------|------|-----------|--------|
| POST | `/propriedades` | Cadastra nova propriedade | 201 |
| GET | `/propriedades/{id}` | Busca propriedade por id | 200 / 404 |
| GET | `/propriedades?pageNumber=0` | Lista propriedades paginadas | 200 / 404 |
| PUT | `/propriedades/{id}` | Atualiza propriedade | 200 / 404 |
| DELETE | `/propriedades/{id}` | Remove propriedade | 204 / 404 |

**Exemplo de body para POST/PUT:**
```json
{
  "nome": "Fazenda Santa Luzia",
  "areaHa": 250.5,
  "cultura": "SOJA",
  "latitude": -21.1767,
  "longitude": -47.8208,
  "status": "ATIVO",
  "idProdutor": 1,
  "idCooperativa": null
}
```

---

### 🚨 Alertas — `/alertas`

| Método | Rota | Descrição | Status |
|--------|------|-----------|--------|
| POST | `/alertas` | Registra novo alerta | 201 |
| GET | `/alertas/{id}` | Busca alerta por id | 200 / 404 |
| GET | `/alertas/propriedade/{id}?pageNumber=0` | Lista alertas de uma propriedade | 200 / 404 |
| PATCH | `/alertas/{id}/resolver` | Marca alerta como resolvido | 200 / 404 |
| DELETE | `/alertas/{id}` | Remove alerta | 204 / 404 |

**Exemplo de body para POST:**
```json
{
  "idPropriedade": 1,
  "idLeitura": null,
  "tipo": "SECA",
  "nivel": "ALTO",
  "descricao": "Índice de umidade do solo abaixo do limite crítico para soja."
}
```

---

### 🛰️ Satélite — `/satelite`

| Método | Rota | Descrição | Status |
|--------|------|-----------|--------|
| GET | `/satelite/propriedade/{id}/clima` | Dados climáticos da propriedade via NASA POWER / Open-Meteo | 200 / 404 |
| POST | `/satelite/propriedade/{id}/saude` | Calcula saúde da lavoura e salva leitura no banco | 200 / 404 |
| GET | `/satelite/clima?latitude={lat}&longitude={lon}` | Dados climáticos por coordenadas livres | 200 |

**Exemplo de resposta do endpoint de saúde:**
```json
{
  "idLeitura": 1,
  "propriedade": "Fazenda Santa Luzia",
  "ndvi": 0.62,
  "saudeLavoura": "EXCELENTE",
  "temperatura": 28.4,
  "umidade": 72.1,
  "precipitacao": 12.5
}
```

**Classificação do NDVI:**

| NDVI | Saúde da lavoura |
|------|-----------------|
| >= 0.6 | EXCELENTE |
| >= 0.4 | BOA |
| >= 0.2 | MODERADA |
| >= 0.0 | RUIM |
| < 0.0 | CRITICA |

---

## 🧪 Como testar com Postman

### Importar a coleção

1. Abra o Postman
2. Clique em **Import**
3. Importe o arquivo `documentos/agrosat-postman-collection.json` disponível no repositório

### Fluxo sugerido para teste completo

**Passo 1 — Cadastrar um produtor:**
```
POST http://localhost:8080/produtores
```

**Passo 2 — Cadastrar uma propriedade:**
```
POST http://localhost:8080/propriedades
```
Use o `id` retornado no passo anterior como `idProdutor`.

**Passo 3 — Buscar dados climáticos da propriedade:**
```
GET http://localhost:8080/satelite/propriedade/{id}/clima
```

**Passo 4 — Calcular e salvar a saúde da lavoura:**
```
POST http://localhost:8080/satelite/propriedade/{id}/saude
```
Isso consulta a NASA POWER / Open-Meteo e salva a leitura no banco.

**Passo 5 — Gerar um alerta para a propriedade:**
```
POST http://localhost:8080/alertas
```

**Passo 6 — Listar alertas da propriedade:**
```
GET http://localhost:8080/alertas/propriedade/{id}?pageNumber=0
```

**Passo 7 — Resolver o alerta:**
```
PATCH http://localhost:8080/alertas/{id}/resolver
```

---

## 📐 Arquitetura

```
agrosat/
├── controller/       # Camada de entrada HTTP (REST)
├── service/          # Regras de negócio
├── repository/       # Acesso ao banco de dados (Spring Data JPA)
├── model/            # Entidades JPA mapeadas nas tabelas Oracle
├── dto/              # Objetos de transferência de dados (records)
├── mapper/           # Conversão entre entidades e DTOs
└── exception/        # Tratamento global de exceções
```

**Integrações externas:**
- **NASA POWER API** — dados históricos de temperatura, precipitação, umidade e vento
- **Open-Meteo API** — fallback automático caso a NASA POWER não responda

---

## ✅ Requisitos técnicos atendidos

- [x] CRUD completo (GET, POST, PUT, DELETE)
- [x] Bean Validation nos campos dos DTOs
- [x] Paginação e ordenação nos endpoints de listagem
- [x] Busca com parâmetros (`pageNumber`, `latitude`, `longitude`)
- [x] Cache com `@Cacheable` e `@CacheEvict`
- [x] Tratamento global de exceções (`@ControllerAdvice`)
- [x] DTOs com records Java
- [x] HATEOAS com links nas respostas
- [x] Swagger/OpenAPI documentado
- [x] Entidades relacionadas e mapeadas com JPA
- [x] Integração com APIs externas via WebClient
- [x] Repositórios com Spring JPA Query Methods
