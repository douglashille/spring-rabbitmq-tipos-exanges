# springboot-rabbitmq-example

branch teste

## Producer , tipos de exchage
    Direct
    Fanout
    Topic
    Header
        O tipo header o receiver recebe string e demais podem ser diretamente convertios no objeto enviado pelo producer
    
## Consumer
    @RabbitListener(queues = "nomeDaFila")
