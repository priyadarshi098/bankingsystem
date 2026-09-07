package com.bank.accountservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    @Value("${kafka.topic.account-retrieved}")
    private String KAFKA_USER_ACCOUNT_RETRIEVED;

    @Bean
    public NewTopic getAccountRetrievedTopic(){
        return new NewTopic(KAFKA_USER_ACCOUNT_RETRIEVED, 3, (short)1);
    }
}
