
/*
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2019
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 */


package org.acme.rest.json.database;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.util.Map;

import static java.util.Collections.emptyMap;

public class H2DatabaseTestResource implements QuarkusTestResourceLifecycleManager {

    private Server tcpServer;

    @Override
    public Map<String, String> start() {

        try {
            tcpServer = Server.createTcpServer();
            tcpServer.start();
            System.out.println("[INFO] H2 database started in TCP server mode on port " + tcpServer.getPort());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emptyMap();
    }

    @Override
    public synchronized void stop() {
        if (tcpServer != null) {
            tcpServer.stop();
            System.out.println("[INFO] H2 database was shut down");
            tcpServer = null;
        }
    }
}