package net.fatdog.simplekafkaproducer.producer;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProducerConfiguration {
    private final Map<String, Object> producerConfigs;

    public ProducerConfiguration() {
        producerConfigs = new HashMap<>();
        // Initialize default configurations
        producerConfigs.put("bootstrap.servers", "localhost:9092");
        producerConfigs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerConfigs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // Set default batch.size
        producerConfigs.put("batch.size", 16384);
    }

    public void updateBatchSize(int batchSize) {
        producerConfigs.put("batch.size", batchSize);
    }

    public Map<String, Object> getProducerConfigs() {
        return new HashMap<>(producerConfigs);
    }
}
