package net.fatdog.simplekafkaproducer.updater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.fatdog.simplekafkaproducer.producer.DynamicKafkaProducer;
import net.fatdog.simplekafkaproducer.producer.SimpleProducerConfig;

@RestController
@RequestMapping("/kafka/config")
public class KafkaConfigController {
    
    @Autowired
    private SimpleProducerConfig producerConfig;

    @Autowired
    private DynamicKafkaProducer kafkaProducer;

    @PostMapping("/update-batch-size")
    public ResponseEntity<String> updateBatchSize(@RequestParam int batchSize) {
        producerConfig.updateBatchSize(batchSize);
        kafkaProducer.refreshProducer();
        return ResponseEntity.ok("Batch size updated to: " + batchSize);
    }
}
