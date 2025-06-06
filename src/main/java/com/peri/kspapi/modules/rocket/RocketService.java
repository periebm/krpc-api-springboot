package com.peri.kspapi.modules.rocket;

import com.peri.kspapi.modules.krpc.KrpcService;
import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.services.SpaceCenter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        return spaceCenter.getActiveVessel()
                .getName();
    }

    public Map<String, Object> getTelemetryData() throws Exception {
        Connection connection = krpcService.getConnection();
        SpaceCenter spaceCenter = SpaceCenter.newInstance(connection);
        SpaceCenter.Vessel vessel = spaceCenter.getActiveVessel();
        SpaceCenter.ReferenceFrame refframe = vessel.getOrbit()
                .getBody()
                .getReferenceFrame();
        SpaceCenter.Flight flight = vessel.flight(refframe);

        // Use HashMap em vez de Map.of() para criar um mapa mutável
        Map<String, Object> res = new HashMap<>();
        res.put("altitude", flight.getMeanAltitude());
        res.put("velocity", flight.getSpeed());
        res.put("throttle", vessel.getControl().getThrottle());

        System.out.println(res);
        return res;
    }
}
