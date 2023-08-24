package com.example.pipayshopapi.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 

 
@Configuration
public class DailyActiveQueueConfig {
      public static final String EXCHANGE_NAME = "DailyActive";
      public static final String QUEUE_A = "DailyActive";
      // 声明 xExchange
      @Bean("DailyActiveExchange")
      public DirectExchange DailyActive(){
       return new DirectExchange(EXCHANGE_NAME);
      }

      //声明队列 A
      @Bean("queueA")
      public Queue queueA(){
            return QueueBuilder.durable(QUEUE_A).build();
      }
      // 声明队列 A 绑定 DailyActiveExchange
      @Bean
      public Binding queueaBindingX(@Qualifier("queueA") Queue queueA, @Qualifier("DailyActiveExchange") DirectExchange Exchange){
        return BindingBuilder.bind(queueA).to(Exchange).with("DailyActive");
      }

}