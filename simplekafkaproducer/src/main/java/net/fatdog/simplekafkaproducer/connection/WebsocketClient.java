package net.fatdog.simplekafkaproducer.connection;

import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebSocketClient {
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final WebSocketClient client = new StandardWebSocketClient();
    private String id;

    public void connectToWebSocket() {
        client.doHandshake(new SimpleClientWebSocketHandler(), "ws://localhost:8080/my-websocket-endpoint");
        client.execute(new SimpleClientWebSocketHandler(), "ws://localhost:8080/my-websocket-endpoint");
    }

    private class SimpleClientWebSocketHandler implements WebSocketHandler {
        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            // Connection established
            this.id = session.getId();
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
            // Handle incoming message
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) {
            // Schedule a reconnection attempt after 3 seconds
            scheduler.schedule(this::connectToWebSocket, 3, TimeUnit.SECONDS);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
            // Connection closed
        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }
    }
}

