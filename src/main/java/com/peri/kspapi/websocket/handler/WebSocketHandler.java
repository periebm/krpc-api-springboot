package com.peri.kspapi.websocket.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peri.kspapi.modules.rocket.RocketService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<WebSocketSession, ScheduledFuture<?>> telemetryTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final RocketService rocketService;

    public WebSocketHandler(ObjectMapper mapper, RocketService rocketService) {
        this.rocketService = rocketService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        JsonNode node = mapper.readTree(message.getPayload());
        String command = node.get("command")
                .asText();

        switch (command) {
            case "connect-krpc":
                break;

            case "start-telemetry":
                startTelemetryStream(session);
                break;
            case "stop-telemetry":
                stopTelemetryStream(session);
                break;
            case "launch-rocket":
                launchRocket(session);
                break;
            default:
                sendError(session, "Unknown command: " + command);
        }
    }

    private void startTelemetryStream(WebSocketSession session) throws IOException {
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Iniciando ciclo de telemetria...");
                Map<String, Object> telemetry = rocketService.getTelemetryData();
                telemetry.put("timestamp", System.currentTimeMillis());

                String response = mapper.writeValueAsString(telemetry);
                synchronized (session) {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(response));
                    } else {
                        System.out.println("Sessão fechada, parando telemetria");
                        stopTelemetryStream(session);
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro no stream de telemetria: " + e.getClass().getName() + " - " + e.getMessage());
                e.printStackTrace();
                stopTelemetryStream(session);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        telemetryTasks.put(session, future);
    }

    private void stopTelemetryStream(WebSocketSession session) {
        ScheduledFuture<?> future = telemetryTasks.remove(session);
        if (future != null) {
            future.cancel(false);
        }
    }

    private void launchRocket(WebSocketSession session) throws IOException {
        // Aqui você chamaria o RocketService real
        String response = "{\"type\":\"launch-response\",\"status\":\"success\"}";
        session.sendMessage(new TextMessage(response));
    }

    private void sendError(WebSocketSession session, String message) throws IOException {
        String error = String.format("{\"type\":\"error\",\"message\":\"%s\"}", message);
        session.sendMessage(new TextMessage(error));
    }
}