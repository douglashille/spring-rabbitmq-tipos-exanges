package com.app.rabbitmq.demo.publisher;

import com.app.rabbitmq.demo.config.RabbitMQConfiguration;
import com.app.rabbitmq.demo.dto.MyMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessagePublisher {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/sendDirect")
    public String sendDirect(@RequestBody MyMessage message) {
        String routingKey = RabbitMQConfiguration.ROUTING_A;
        template.convertAndSend(RabbitMQConfiguration.EXCHANGE_DIRECT, routingKey, message);
        return String.format("OK, mensagem enviada à rota %s!", routingKey);
    }

    @PostMapping("/sendFanout")
    public String sendFanoutend(@RequestBody MyMessage message) {
        String exchange = RabbitMQConfiguration.EXCHANGE_FANOUT;
        template.convertAndSend(exchange, "", message);
        return String.format("OK, mensagem enviada à exchange %s!", exchange);
    }

    @PostMapping("/sendTopic")
    public String sendTopic(@RequestBody MyMessage message) {
        String exchange = RabbitMQConfiguration.EXCHANGE_TOPIC;
        String routingKey = RabbitMQConfiguration.ROUTING_A;
        template.convertAndSend(exchange, routingKey, message);
        return String.format("OK, mensagem enviada à exchange %s, rota primária %s.", exchange, routingKey);
    }

    @PostMapping("/sendHeader/{color}")
    public String sendHeader(@RequestBody MyMessage message, @PathVariable("color") String color) {
        String exchange = RabbitMQConfiguration.EXCHANGE_HEADER;
        message.setName(message.getName() + " " + color);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("color", color);
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        MessageConverter messageConverter = new SimpleMessageConverter();
        Message mensagemSpring = messageConverter.toMessage(message.toJson(), messageProperties);
        // byte[] data = SerializationUtils.serialize(message);
        // Message mensagemSpring = MessageBuilder.withBody(data)
        // .setHeader("color", color)
        // .build();

        template.send(exchange, "", mensagemSpring);
        return String.format("OK, mensagem enviada à exchange %s.", exchange);
    }
}
