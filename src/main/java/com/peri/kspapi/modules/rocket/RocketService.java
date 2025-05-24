package com.peri.kspapi.modules.rocket;

import com.peri.kspapi.modules.krpc.KrpcService;
import krpc.client.Connection;
import krpc.client.services.SpaceCenter;
import org.springframework.stereotype.Service;

@Service
public class RocketService {
    private final KrpcService krpcService;

    public RocketService(KrpcService krpcService) {
        this.krpcService = krpcService;
    }

    public String getVesselName() throws Exception {
        Connection connection = krpcService.getConnection();
        System.out.println(connection);
        SpaceCenter spaceCenter = SpaceCenter.newInstance(krpcService.getConnection());
        return spaceCenter.getActiveVessel().getName();
    }
}
