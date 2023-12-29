package net.fatdog.simplekafkaproducer.updater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka/config")
public class KafkaConfigController {
    
    @Autowired
    private ProducerConfiguration producerConfiguration;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/update-batch-size")
    public ResponseEntity<String> updateBatchSize(@RequestParam int batchSize) {
        producerConfiguration.updateBatchSize(batchSize);
        kafkaProducerService.refreshProducer();
        return ResponseEntity.ok("Batch size updated to: " + batchSize);
    }
}
