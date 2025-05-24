package com.peri.kspapi.modules.krpc;


import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.services.KRPC;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KrpcService {
    private Connection connection;

    public void connect() throws IOException, RPCException {
        if (connection != null) {
            return;
        }

        String name = "Krpc API";
        String host = "127.0.0.1";
        int port = 50000;
        int streamPort = 50001;
        System.out.println("Preaprando apra conectar:");
        connection = Connection.newInstance(name);
        System.out.println("CONECTOU: " + KRPC.newInstance(connection)
                .getStatus()
                .getVersion());
    }

    public Connection getConnection() throws Exception {
        if(connection == null) {
            throw new Exception("Connection not established.");
        }
        return connection;
    }

    public boolean isConnected() {
        return this.connection != null;
    }
}
