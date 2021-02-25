package monitoringapi.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitmqConfig {

    private Environment env;

    @Autowired
    public RabbitmqConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public Queue queue() {
        return new Queue(env.getProperty("rabbitmq.userapi.queue"));
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(env.getProperty("rabbitmq.userapi.exchange"));
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("rabbitmq.userapi.routingKey"));
    }

    @Bean
    public Queue queueProductApiBackupOrderApi() {
        return new Queue(env.getProperty("rabbitmq.order_backup_api_product_api.queue"));
    }

    @Bean
    public TopicExchange exchangeProductApiBackupOrderApi() {
        return new TopicExchange(env.getProperty("rabbitmq.order_backup_api_product_api.exchange"));
    }

    @Bean
    public Binding bindingProductApiBackupOrderApi(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(env.getProperty("rabbitmq.order_backup_api_product_api.routingKey"));
    }

    // @Bean
    // public Queue queueProductApi() {
    //     return new Queue(env.getProperty("rabbitmq.productapi.queue"));
    // }

    // @Bean
    // public TopicExchange exchangeProductApi() {
    //     return new TopicExchange(env.getProperty("rabbitmq.productapi.exchange"));
    // }

    // @Bean
    // public Binding bindingProductApi(Queue queueProductApi, TopicExchange exchangeProductApi) {
    //     return BindingBuilder.bind(queueProductApi).to(exchangeProductApi).with(env.getProperty("rabbitmq.productapi.routingKey"));
    // }

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