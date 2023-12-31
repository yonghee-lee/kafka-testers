package net.fatdog.simplekafkaproducer.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;


@Configuration
public class SimpleProducerConfig {
    
    private final Map<String, Object> producerConfigs;

    public SimpleProducerConfig() {
        
        producerConfigs = new HashMap<>();
        
        // Initialize default configurations
        producerConfigs.put("bootstrap.servers", "localhost:9092");
        producerConfigs.put("key.serializer", StringSerializer.class);
        producerConfigs.put("value.serializer", StringSerializer.class);
        
    }
    
    public void updateBatchSize(int batchSize) {
        producerConfigs.put("batch.size", batchSize);
    }

    
    public Map<String, Object> getProducerConfigs() {
        return new HashMap<>(producerConfigs);
    }

    
}
