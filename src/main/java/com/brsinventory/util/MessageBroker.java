package com.brsinventory.util;

import com.brsinventory.messages.BusBookingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class MessageBroker {

    private final JmsTemplate jmsTemplate;

    @Autowired
    MessageBroker(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public void sendConfirmBookingMessage(String destination, BusBookingMessage busBookingMessage) {
        jmsTemplate.convertAndSend(destination, busBookingMessage);
        System.out.println("Sent message: " + busBookingMessage);
    }
}
