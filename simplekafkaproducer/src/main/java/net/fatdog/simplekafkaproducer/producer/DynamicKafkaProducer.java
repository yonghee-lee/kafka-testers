package net.fatdog.simplekafkaproducer.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

@Component
public class DynamicKafkaProducer {
    
    
    @Autowired
    private SimpleProducerConfig producerConfig;

    private KafkaTemplate<String, String> kafkaTemplate;

    public DynamicKafkaProducer() {
    
        initializeKafkaTemplate();
    }

    private synchronized void initializeKafkaTemplate() {
        if (kafkaTemplate != null) {
            // Close the existing producer
            kafkaTemplate.flush();
            kafkaTemplate.destroy();
        }
    }

    private void createKafkaTemplate() {
        this.kafkaTemplate = new KafkaTemplate<String, String>(producerFactory());
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig.getProducerConfigs());
    }

    public KafkaTemplate<String, String> kafkaTemplate() {
        if(this.kafkaTemplate == null)
            createKafkaTemplate();
        return this.kafkaTemplate;
    }

    public void refreshProducer() {
        initializeKafkaTemplate();
        createKafkaTemplate();
    }

    public void send(String topic, long messageKey, String message) {
        String messageKeyString = String.format("%010d", messageKey);
        kafkaTemplate.send(new ProducerRecord<>(topic, messageKeyString, message));
    }

}