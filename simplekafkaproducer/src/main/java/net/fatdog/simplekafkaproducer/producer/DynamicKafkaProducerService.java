package net.fatdog.simplekafkaproducer.producer;

@Service
public class DynamicKafkaProducerService {
    
    private final ProducerConfiguration producerConfiguration;
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public DynamicKafkaProducerService(ProducerConfiguration producerConfiguration) {
        this.producerConfiguration = producerConfiguration;
        initializeKafkaTemplate();
    }

    private void initializeKafkaTemplate() {
        if (kafkaTemplate != null) {
            // Close the existing producer
            kafkaTemplate.destroy();
        }
        // Initialize a new KafkaTemplate with updated configuration
        kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfiguration.getProducerConfigs()));
    }

    public void refreshProducer() {
        initializeKafkaTemplate();
    }

    public void send(String topic, String message) {
        String messageKeyString = String.format("%010d", messageKey++);
        kafkaTemplate.send(new ProducerRecord<>(topic, messageKeyString, message));
    }

}
