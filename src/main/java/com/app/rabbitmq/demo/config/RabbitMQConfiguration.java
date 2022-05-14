package com.app.rabbitmq.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String EXCHANGE_DIRECT = "spring_exchange.direct";
    public static final String EXCHANGE_FANOUT = "spring_exchange.fanout";
    public static final String EXCHANGE_TOPIC = "spring_exchange.topic";
    public static final String EXCHANGE_HEADER = "spring_exchange.header";
    public static final String QUEUE_A = "spring_queue.A";
    public static final String QUEUE_B = "spring_queue.B";
    public static final String QUEUE_ALL = "spring_queue.ALL";
    public static final String ROUTING_A = "spring_routing.A";
    public static final String ROUTING_B = "spring_routing.B";
    public static final String ROUTING_ALL = "spring_routing.*";

    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, false);
    }

    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, false);
    }

    @Bean
    public Queue queueAll() {
        return new Queue(QUEUE_ALL, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_DIRECT);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_TOPIC);
    }

    @Bean
    HeadersExchange headerExchange() {
        return new HeadersExchange(EXCHANGE_HEADER);
    }

    @Bean
    Binding bindingA(Queue queueA, DirectExchange exchange) {
        return BindingBuilder.bind(queueA)
                .to(exchange)
                .with(ROUTING_A);
    }

    @Bean
    Binding bindingB(Queue queueB, DirectExchange exchange) {
        return BindingBuilder.bind(queueB)
                .to(exchange)
                .with(ROUTING_B);
    }

    @Bean
    Binding bindingFanoutA(Queue queueA, FanoutExchange exchange) {
        return BindingBuilder.bind(queueA)
                .to(exchange);
    }

    @Bean
    Binding bindingFanoutB(Queue queueB, FanoutExchange exchange) {
        return BindingBuilder.bind(queueB)
                .to(exchange);
    }

    @Bean
    Binding bindingTopicA(Queue queueA, TopicExchange exchange) {
        return BindingBuilder.bind(queueA)
                .to(exchange)
                .with(ROUTING_A);
    }

    @Bean
    Binding bindingTopicB(Queue queueB, TopicExchange exchange) {
        return BindingBuilder.bind(queueB)
                .to(exchange)
                .with(ROUTING_B);
    }

    @Bean
    Binding bindingTopicALL(Queue queueAll, TopicExchange exchange) {
        return BindingBuilder.bind(queueAll)
                .to(exchange)
                .with(ROUTING_ALL);
    }

    @Bean
    Binding bindingHeaderA(Queue queueA, HeadersExchange exchange) {
        return BindingBuilder.bind(queueA)
                .to(exchange)
                .where("color")
                .matches("red");
    }

    @Bean
    Binding bindingHeaderB(Queue queueB, HeadersExchange exchange) {
        return BindingBuilder.bind(queueB)
                .to(exchange)
                .where("color")
                .matches("blue");
    }

    @Bean
    Binding bindingHeaderAll(Queue queueAll, HeadersExchange exchange) {
        return BindingBuilder.bind(queueAll)
                .to(exchange)
                .where("color")
                .matches("green");
    }


    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    // @Bean
    // public AmqpTemplate template(ConnectionFactory connectionFactory) {
    // final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    // rabbitTemplate.setMessageConverter(converter());
    // return rabbitTemplate;
    // }
}
