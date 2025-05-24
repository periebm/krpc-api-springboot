package com.peri.kspapi.modules.rocket;

import com.peri.kspapi.modules.krpc.KrpcService;
import com.peri.kspapi.modules.krpc.dto.MessageResponse;
import com.peri.kspapi.modules.rocket.dto.VesselNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ksp/rocket")
@RequiredArgsConstructor
public class RocketController {
    private final RocketService rocketService;

    @GetMapping("/vessel-name")
    public ResponseEntity<VesselNameResponse> getVesselName() throws Exception {
            String vesselName = rocketService.getVesselName();
            return ResponseEntity.ok(new VesselNameResponse(vesselName));
    }
}
