# transaction-validation-service-demo
Demonstração de serviço de validação de transação para identificação de fraudes.

### Preparando o ambiente de desenvolvimento
Siga os passos abaixo antes de iniciar o projeto localmente.

- Versão JDK:

    **JDK 1.11**

Pelas inumeras vantagens que container nos oferece para agilizar o desenvolvimento e validação do projeto antes de chegarmos no ambiente de produção, optamos por usar o Kafka e o DynamoDB em Docker.
 
- Configurando Kafka em docker local

  - Utilizamos a seguinte imagem do docker que o confluente disponibiliza, realize o clone:
    
    ```git clone https://github.com/confluentinc/cp-docker-images```
   
  - Acessa a pasta /cp-docker-images/examples/kafka-single-node e execute o comando para baixar e executar o kafka:
    
    ```docker-compose up -d```
    
  - Crie os tópicos para que a aplicação possa produzir e consumir mensagens
  
    ```docker-compose exec kafka  kafka-topics --create --topic topic-transaction-created --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181 ```
  
  - Criando tópico de eventos de criação de transacao (Este topico a aplicação será consumer e realizará a aplicação de regras de negócio para descobrir possíveis fraudes):
  
    ```docker-compose exec kafka  kafka-topics --create --topic topic-transaction-created --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181 ```
  
  - Criando tópico de notificação de eventos de transações com fraude/sem fraude:
  
    ```docker-compose exec kafka  kafka-topics --create --topic topic-transaction-validation-notify --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181``` 

- Configurando DynamoDB em docker local

    Para não precisar criar tabelas em uma ambiente amazon e pagar por isso, optamos por utilizar o DynamoDB em Docker seguindo a documentação abaixo:

    * [Executando DynamoDB Local](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html)

    Após fazer a instalação da imagem em sua máquina, start o container:
    - `docker run -p 8000:8000 amazon/dynamodb-local`

    - Para criar as tabelas no DynamoDB local apenas execute, OBS:optei por deixar aberto nos testes de integração a criação externa das tabelas apenas em ambiente de desenvolvimento:

        ```./gradlew build``` 

### Regras de validações por produto
Por padrão toda vez que o consumer do Kafka receber um evento de criação de transação, o serviço realizará as seguintes validações (A nível de demonstração estamos validando transações de cartão de crédito e Mobile):
 - Transação de cartão de crédito TransactionType.CREDIT_CARD: 
    O sistema verifica se o remetente e receptor estão na lista de restrição, basicamente para simular por exemplo um serviço de KYC ou validações de lavagem de dinheiro e terrorismo
     (A nível de teste foi criada uma API onde é possível inserir os IDS dos customers para criar esse cenário de fraude)
 - Transação Mobile TransactionType.MOBILE:
    O sistema verifica se o valor da transação ultrapassa a média de gastos que aquele remetente normalmente tem no seu historico.
    (A nível de teste foi criada uma API onde é possível vincular o customerID e média de gastos para conseguir simular)
 
### APIs de simulação de integração
 As APIS abaixo foram criadas para simular integrações com serviços externos, no cenário de produção essas integrações se dariam por protocolo REST ou alguma outra integração sistemica.
 
 ### POST
 [/customer-restriction](#/customer-restriction)
 
 ### POST
 [/transaction-data-analytics](#/transaction-data-analytics)

#### POST /customer-restriction
Vincula um customerID na lista de restrição, caso esteja nessa lista as transações realizadas pelo mesmo serão consideradas como fraude.

**Response** 201 OK
```json
  {
    "customerId":  "123456789"
  }
```         

#### POST /transaction-data-analytics
Vincula um customerID em um valor de média de gastos em compras online, caso as transações realizadas pelo remetente ultrapassem esse limite será considerado fraude.

**Response** 201 OK

 ```json
   {
     "customerId":  "123456789"
   }
 ```         
 
### API Dados de validações realizadas
A API abaixo fornece todas as validações realizadas em transações processadas recebidas nos eventos do Kafka. (A nível de demonstração estamos retornando todas as validações):

 ### GET
 [/transaction-validator](#/transaction-validator)
 
 #### GET /transaction-validator
  **Response** 200 OK
```json
  [
    {
      "transactionId": "99999",
      "customerSendId": "11111",
      "customerReceivedId": "22222",
      "transactionDate": 1614454600,
      "transactionType": "CREDIT_CARD",
      "statusValidationType": "SUCCESS",
      "transactionValidationDate": 1212121212
    },
    {
      "transactionId": "88888",
      "customerSendId": "777",
      "customerReceivedId": "66666",
      "transactionDate": 1614454600,
      "transactionType": "MOBILE",
      "statusValidationType": "HIGHER_AVERAGE_ONLINE",
      "transactionValidationDate": 1212121212
    },
    {
      "transactionId": "555555",
      "customerSendId": "9999",
      "customerReceivedId": "333333",
      "transactionDate": 1614454600,
      "transactionType": "MOBILE",
      "statusValidationType": "RECEIVER_ON_RESTRICTED_LIST",
      "transactionValidationDate": 1212121212
    },
    {
      "transactionId": "66666",
      "customerSendId": "0101010",
      "customerReceivedId": "020202",
      "transactionDate": 1614454600,
      "transactionType": "MOBILE",
      "statusValidationType": "SENDER_ON_RESTRICTED_LIST",
      "transactionValidationDate": 1212121212
    }
  ]
```   
 
     
 

#### Documentação de referência
Por favor considere as documentações abaixo para seguir as melhores praticas:

* [Documentação oficial Gradle](https://docs.gradle.org)
* [Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-kafka)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [DynamoDB SDK](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-dynamodb.html)
