package com.app.rabbitmq.demo.consumer;

import com.app.rabbitmq.demo.config.RabbitMQConfiguration;
import com.app.rabbitmq.demo.dto.MyMessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Log
@Component
public class MessageConsumer {


    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_A)
    public void consumeMessageFromQueueA(MyMessage message) {
        System.out.println("Messagem da fila " + RabbitMQConfiguration.QUEUE_A + ": " + message.toString());
    }

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_B)
    public void consumeMessageFromQueueB(MyMessage message) {
        System.out.println("Messagem da fila " + RabbitMQConfiguration.QUEUE_B + ": " + message.toString());
    }

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_ALL)
    public void consumeMessageFromQueueAll(MyMessage message) {
        System.out.println("Messagem da fila " + RabbitMQConfiguration.QUEUE_ALL + ": " + message.toString());
    }

    //////////////////////////////////////////////
    // Para Exchanges Header o tipo vem text/plain
    //////////////////////////////////////////////

    // @RabbitListener(queues = RabbitMQConfiguration.QUEUE_A)
    // public void consumeMessageFromQueueA(String message) {
    //     System.out.println("Messagem da fila (tipo header formato String) " + RabbitMQConfiguration.QUEUE_A + ": " + message);
    // }

    // @RabbitListener(queues = RabbitMQConfiguration.QUEUE_B)
    // public void consumeMessageFromQueueB(String message) {
    //     System.out.println("Messagem da fila (tipo header formato String) " + RabbitMQConfiguration.QUEUE_B + ": " + message);
    // }

    // @RabbitListener(queues = RabbitMQConfiguration.QUEUE_ALL)
    // public void consumeMessageFromQueueAll(String message) {
    //     System.out.println("Messagem da fila (tipo header formato String) " + RabbitMQConfiguration.QUEUE_ALL + ": " + message);
    // }
}
