package com.example.pipayshopapi.config;

import com.example.pipayshopapi.util.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import java.util.HashMap;
import java.util.Map;
 
@Configuration
public class DelayedQueueConfig {

    @Bean
    public Queue delayedQueue() {
    return new Queue(Constants.DELAYED_QUEUE_NAME);
    }
    //自定义交换机 我们在这里定义的是一个延迟交换机
    @Bean
    public CustomExchange delayedExchange() {
      Map<String, Object> args = new HashMap<>();
      //自定义交换机的类型
      args.put("x-delayed-type", "direct");
      return new CustomExchange(Constants.DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }
    @Bean
    public Binding bindingDelayedQueue(@Qualifier("delayedQueue") Queue queue, @Qualifier("delayedExchange") CustomExchange delayedExchange) {
      return BindingBuilder.bind(queue).to(delayedExchange).with(Constants.DELAYED_ROUTING_KEY).noargs();
    }
}