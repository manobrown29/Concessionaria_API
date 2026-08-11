# Concessionaria

API para controle de estoque de carros e clientes da Concessionária Marcelo
Gomes, substituindo a planilha atual. Projeto em evolução ao longo de 3
entregas.

## Stack

- Java 21
- Spring Boot 4.0.7 (Web MVC, Data JPA, Validation)
- Lombok
- MySQL
- springdoc-openapi (Swagger UI)
- Maven

## Como rodar

1. Suba um MySQL local (usuário `root`, senha `root`, ou ajuste
   `src/main/resources/application.yaml`). O schema `concessionaria` é
   criado automaticamente (`createDatabaseIfNotExist=true`) e as tabelas são
   criadas/atualizadas pelo Hibernate (`ddl-auto: update`).
2. Rode:

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`. Swagger UI em
`http://localhost:8080/swagger-ui.html`.

## Endpoints

### Carros

| Método | Rota           | Descrição                  |
|--------|----------------|-----------------------------|
| POST   | `/carros`      | Cadastra um carro           |
| GET    | `/carros`      | Lista todos os carros       |
| GET    | `/carros/{id}` | Busca um carro por id       |
| DELETE | `/carros/{id}` | Remove um carro             |

**`POST /carros`** — corpo (`CarroRequestDTO`):

```json
{
  "modelo": "Corolla",
  "marca": "Toyota",
  "cor": "Prata",
  "anoFabricacao": 2023,
  "anoModelo": 2024,
  "placa": "ABC1D23",
  "chassi": "9BWZZZ377VT004251",
  "quilometragem": 0,
  "preco": 145000.00
}
```

`id`, `status` e `cliente` **não fazem parte** do DTO de entrada: todo carro
cadastrado nasce com `status = DISPONIVEL` e sem cliente vinculado — isso é
decidido pelo sistema, não por quem preenche o formulário.

**Resposta** (`CarroResponseDTO`):

```json
{
  "id": 1,
  "modelo": "Corolla",
  "marca": "Toyota",
  "cor": "Prata",
  "anoFabricacao": 2023,
  "anoModelo": 2024,
  "placa": "ABC1D23",
  "chassi": "9BWZZZ377VT004251",
  "quilometragem": 0,
  "preco": 145000.00,
  "status": "DISPONIVEL",
  "cliente": null
}
```

O `cliente`, quando presente, aparece resumido (`{ "id": 3, "nome": "Maria Souza" }`)
— o `CarroResponseDTO` nunca expõe CPF, telefone ou e-mail do cliente
associado a um carro.

### Clientes

| Método | Rota             | Descrição                 |
|--------|------------------|-----------------------------|
| POST   | `/clientes`      | Cadastra um cliente         |
| GET    | `/clientes`      | Lista todos os clientes     |
| GET    | `/clientes/{id}` | Busca um cliente por id     |
| DELETE | `/clientes/{id}` | Remove um cliente           |

**`POST /clientes`** — corpo (`ClienteRequestDTO`):

```json
{
  "nome": "Maria Souza",
  "cpf": "12345678900",
  "telefone": "11999998888",
  "email": "maria.souza@email.com"
}
```

**Validações do `ClienteRequestDTO`:**

| Campo    | Regra                                              |
|----------|------------------------------------------------------|
| nome     | obrigatório                                           |
| cpf      | obrigatório, 11 dígitos numéricos, único              |
| telefone | obrigatório, DDD + número, 10 ou 11 dígitos numéricos |
| email    | obrigatório, formato de e-mail válido                 |


## Decisão: 
- Relacionamento ManyToOne entre Carro e Cliente,vários carros sejam associados a um cliente. O campo cliente_id não foi definido como obrigatório.
- O CPF obrigatório e único.
- A placa é única, não obrigatória(um veicylo pode chegar sem placa)
- O chassi obrigatório e único
- criar enum para existir só 3 tipos de status
