# Gestão de sessões de votação
Teste tecnico feito para a DBC Company/Sicredi.

## Descrição
O serviço implementa uma API REST que permite criar pautas de
votação, abrir sessões e votar. Após o fim da votação, o resultado
é computado e um evento é propagado num tópico do Kafka.

A documentação da API pode ser acessada em [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/).

## Tecnologias
- Java 21 / Spring Boot 3
- Kafka
- PostgreSQL

## Deploy
Este repositório contém um arquivo `docker-compose.yml` que pode ser utilizado 
para executar a aplicação na máquina local usando o comando.

```shell
docker-compose up -d
```

Uma imagem Docker da versão mais recente da aplicação foi disponibilizada no Docker Hub,
para evitar a necessidade de build manual.
> 💡 Uma instância de Kafdrop foi incluída no `docker-compose.yml` para facilitar a visualização
> dos tópicos e mensagens do Kafka. Acesse em: [http://localhost:9000](http://localhost:9000).

## Serviço de validação de permissão por CPF
O serviço de validação do CPF, parte da tarefa bonus 1, estava retornando
`UNABLE_TO_VOTE` para todos os CPFs que testei (Até mesmo o CPF de exemplo
da print da especificação que retornava `ABLE_TO_VOTE`). Acreditei que 
seria um bug, mas para evitar que isso atrapalhasse os testes da aplicação, 
criei uma feature flag para poder controlar dinâmicamente esse comportamento.

Ela está desativada por padrão, mas pode ser ativada setando a variável
de ambiente `WHITELIST_ATIVADO=true` no container da aplicação, pelo
arquivo `docker-compose.yml`. A validação ocorre no fluxo de cadastro
de associados.

## Escolhas de design
### Versionamento de APIs
A estratégia de versionamento de APIs adotada foi a de versionamento por URL,
enquanto que no código a estratégia foi o uso de um pacote `v1` que contém
os controllers e DTOs dos objetos de request e response relativos a essa versão.

Optei por incluir no package de versionamento apenas as classes que fazem
parte da API _per se_, não incluíndo as implementações a nível de negócio.
Uma abordagem preferível, na minha visão, para um versionamento que cubra
as implementações de negócio seria uma estratégia de diferentes projetos 
e/ou repositórios.

### Interações com o banco de dados sem usar objetos
Em momentos que a interação com o banco de dados não envolvia a manipulação
de dados e apenas leitura ou escritas de um único campo, optei por utilizar 
queries escritas diretamente em HQL ou SQL, ao invés de usar objetos 
e as funcionalidades de ORM do Hibernate.

O motivo disto era evitar a necessidade de instanciação de objetos que não
seriam utilizados, o que evita uso desnecessário de memória e por consequencia,
da necessidade do Garbage Collector de atuar com mais frequencia, evitando
os problemas de performance que isso causaria.

Também procurei delegar a responsabilidade de gerar os relatórios das 
apurações de votos para o banco de dados, que consegue fazer isso de forma
mais eficiente do que se fosse implementada a lógica na aplicação. Utilizei
uma query SQL mapeando os resultados para uma projection, ficando na 
aplicação apenas a tarefa de formatar os resultados no formato do evento
que será propagado no Kafka.

### Uso de `VARCHAR(2000)` ao invés de `TEXT`
Apesar dos campos `TITULO` e `DESCRICAO` da tabela `PAUTA` não serem
requisitos da aplicação explícitos da aplicação, adicionei-os para
manter a aplicação próxima de um cenário real.

Por conta disso, preferi evitar o uso de `TEXT` por conta dos problemas 
que esse tipo possui na capacidade de indexação em consultas e optei pelo
VARCHAR com limite de 2000 caracteres. Apesar de a aplicação não possuir
funcionalidades que exijam essa capacidade, acredito que o tipo `TEXT` seria
algo de um calibre muito grande para um campo dessa natureza.

### Exceptions sem stack-trace
Por conta de as exceptions que extendem de `RegraVioladaException` serem 
usadas apenas para controle de fluxo, estas já estão acompanhadas
de mensagens de log que contextualizam sobre eventuais erros e
permitem a rastreabilidade. Dessa maneira, optei por desativar
a geração de stack-trace para essas exceções. 

Isso evita um gasto desnecessário de RAM (E novamente, a necessidade do 
Garbage Collector de atuar para liberar esses objetos não usados da memória), 
visto que essas exceções tem um uso muito restrito e não precisariamos do 
stack-trace delas para entender a origem dos erros.

## Possibilidades de melhoria
### Apuração dos votos assíncrona
Atualmente, a apuração dos votos é feita de forma síncrona, de maneira que
cada pauta é apurada sequencialmente durante a execução do job. Porém,
nenhuma apuração depende da outra, então seria possível paralelizar a 
apuração de cada pauta.

Não implementei isso por conta de não estar claro pra mim se vão haver tantas
pautas abertas ao mesmo tempo. Entendo que os votos serão uma carga muito grande,
mas a apuração em si me deixou na dúvida.

Dessa forma, os eventuais probleminhas que poderiam surgir com a paralelização
desse processo, mesmo que sejam isolados, talvez não sejam justificáveis.

### Uso de shedlock
Pensei em implementar o [ShedLock](https://github.com/lukas-krecan/ShedLock)
no projeto mas fiquei na dúvida se isso não
seria considerado _overengineering_, afinal não tenho informações sobre
quantas replicas essa aplicação vai ser executada. 

E mesmo que venha a ser executada em mais de uma, talvez não seja algo que 
aconteça de imediato e nem com tanta frequencia.