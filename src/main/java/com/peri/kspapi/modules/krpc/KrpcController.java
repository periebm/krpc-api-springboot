package com.peri.kspapi.modules.krpc;

import com.peri.kspapi.modules.krpc.dto.ConnectedResponse;
import com.peri.kspapi.modules.krpc.dto.MessageResponse;
import krpc.client.services.KRPC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ksp/krpc")
@RequiredArgsConstructor
public class KrpcController {
    private final KrpcService krpcService;

    @GetMapping("/is-connected")
    public ResponseEntity<ConnectedResponse> isConnected() {
        boolean response = krpcService.isConnected();

        return ResponseEntity.ok(new ConnectedResponse(response));
    }

    @GetMapping("/game-scene")
    public ResponseEntity<MessageResponse> getGameScene() throws Exception {
        KRPC.GameScene gameScene = krpcService.getGameScene();

        return ResponseEntity.ok(new MessageResponse(gameScene.name()));
    }

    @PostMapping("/connect")
    public ResponseEntity<MessageResponse> connectToKrpc() throws Exception {
        krpcService.connect();
        return ResponseEntity.ok(new MessageResponse("Connected."));
    }
}
