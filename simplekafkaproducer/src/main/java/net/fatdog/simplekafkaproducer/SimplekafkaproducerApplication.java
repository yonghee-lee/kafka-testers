package net.fatdog.simplekafkaproducer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.fatdog.simplekafkaproducer.connection.WebSocketClient;

@SpringBootApplication
public class SimplekafkaproducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplekafkaproducerApplication.class, args);
	}

	@Bean
    public CommandLineRunner run(WebSocketClient webSocketClient) {
        return args -> {
            webSocketClient.connectToWebSocket();
        };
    }
}
