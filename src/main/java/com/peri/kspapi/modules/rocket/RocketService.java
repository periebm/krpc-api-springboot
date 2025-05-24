package com.peri.kspapi.modules.rocket;

import com.peri.kspapi.modules.krpc.KrpcService;
import krpc.client.services.SpaceCenter;

public class RocketService {
    private KrpcService krpcService;

    public String getVesselName() {
        SpaceCenter spaceCenter = SpaceCenter.newInstance(krpcService.getConnection());
    }
}
