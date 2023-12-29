package net.fatdog.simplekafkaproducer.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static long messageKey = 0; // Static variable to keep track of message key

    public void sendRandomString(String topic) {
        String messageKeyString = String.format("%010d", messageKey++);
        String randomString = generateRandomString(100);
        kafkaTemplate.send(new ProducerRecord<>(topic, messageKeyString, randomString));
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        while (length > 0) {
            result.append(characters.charAt(random.nextInt(characters.length())));
            length--;
        }
        return result.toString();
    }
}

