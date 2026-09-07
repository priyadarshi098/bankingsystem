package com.bank.notifications.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceKafkaListener {

    @KafkaListener(topics="account-retrieved")
    public void handleAccountRetrieved(String message) {

        System.out.println("message recieved "+message);

    }
     @KafkaListener(topics="account-retrieved")
    public void handleAccountRetrieved1(String message) {

        System.out.println("message recieved "+message);

    }
     @KafkaListener(topics="account-retrieved")
    public void handleAccountRetrieved2(String message) {

        System.out.println("message recieved "+message);

    }
     @KafkaListener(topics="account-retrieved")
    public void handleAccountRetrieved3(String message) {

        System.out.println("message recieved "+message);

    }
     @KafkaListener(topics="account-retrieved")
    public void handleAccountRetrieved4(String message) {

        System.out.println("message recieved "+message);

    }
     @KafkaListener(topics="account-retrieved")
    public void handleAccountRetrieved5(String message) {

        System.out.println("message recieved "+message);

    }

}
