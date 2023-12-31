package net.fatdog.simplekafkaproducer.connection;

import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WebSocketClient {
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final StandardWebSocketClient client = new StandardWebSocketClient();
    private final SimpleClientWebSocketHandler webSocketHandler = new SimpleClientWebSocketHandler();

    public void connectToWebSocket() {
            
        client.execute(webSocketHandler, "ws://localhost:8080/my-websocket-endpoint");
        log.info("Client id: " + webSocketHandler.getId());
        
    }

    public SimpleClientWebSocketHandler getHandler() {
        return this.webSocketHandler;
    }

    private class SimpleClientWebSocketHandler implements WebSocketHandler {
        
        private String id;
        private String message;
        
        public String getId() {
            return this.id;
        }

        public String getMessage() {
            return this.message;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            // Connection established
            this.id = session.getId();
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
            // Handle incoming message
            // Need Trigger!
            this.message = (String) message.getPayload();
            log.info("message: "+ this.message);
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) {
            // Schedule a reconnection attempt after 3 seconds
            scheduler.schedule(() -> connectToWebSocket(), 3, TimeUnit.SECONDS);
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

