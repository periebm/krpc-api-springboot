package com.peri.kspapi.modules.krpc;

import com.peri.kspapi.modules.krpc.dto.ConnectedResponse;
import com.peri.kspapi.modules.krpc.dto.MessageResponse;
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

    @GetMapping()
    public ResponseEntity<ConnectedResponse> isConnected() {
        boolean response = krpcService.isConnected();

        return ResponseEntity.ok(new ConnectedResponse(response));
    }

    @PostMapping("/connect")
    public ResponseEntity<MessageResponse> connectToKrpc() {
        try {
            krpcService.connect();
            return ResponseEntity.ok(new MessageResponse("Conectado."));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new MessageResponse("Erro ao conectar: " + e.getMessage()));
        }
    }
}
