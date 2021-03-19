package com.example.productapi.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitmqConfig {
    
    private final Environment env;
    
    @Autowired
    public RabbitmqConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public Queue queue() {
        return new Queue(env.getProperty("rabbitmq.productapi.queue"));
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(env.getProperty("rabbitmq.productapi.exchange"));
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("rabbitmq.productapi.routingKey"));
    }

    @Bean
    public Queue queueOrderBackupProductApi() {
        return new Queue(env.getProperty("rabbitmq.order_backup_api_product_api.queue"));
    }

    @Bean
    public TopicExchange exchangeOrderBackupProductApi() {
        return new TopicExchange(env.getProperty("rabbitmq.order_backup_api_product_api.exchange"));
    }

    @Bean
    public Binding bindingOrderBackupProductApi(Queue queueOrderBackupProductApi, TopicExchange exchangeOrderBackupProductApi) {
        return BindingBuilder.bind(queueOrderBackupProductApi).to(exchangeOrderBackupProductApi).with(env.getProperty("rabbitmq.order_backup_api_product_api.routingKey"));
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory factory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
