package core.pool;

import core.exceptions.ConnectionPoolException;
import core.logger.CouponSystemLogger;
import core.sql_commands.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class ConnectionPool {

    private static final CouponSystemLogger LOGGER = CouponSystemLogger.getInstance(ConnectionPool.class.getName());

    private Set<Connection> connections = new HashSet<>();
    private final int MAX_CON = 10;
    private static ConnectionPool instance;

    private ConnectionPool() throws ConnectionPoolException {
        for (int i = 0; i < MAX_CON; i++) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                connections.add(DriverManager.getConnection(SQL.URL));
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "ConnectionPool initialization is failed. ", e);
            } catch (ClassNotFoundException e) {
                throw new ConnectionPoolException("ConnectionPool initialization is failed. ", e);
            }
        }
    }

    public synchronized static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionPool();
            } catch (ConnectionPoolException e) {
                LOGGER.log(Level.WARNING, "ConnectionPool initialization is failed. ", e);
            }
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        while (connections.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "ConnectionPool initialization is failed. ", e);
            }
        }
        Iterator<Connection> iterator = connections.iterator();
        Connection connection = iterator.next();
        iterator.remove();
        return connection;
    }

    public synchronized void returnConnection(Connection connection) {
        connections.add(connection);
        notify();
    }

    public synchronized void closeAllConnections() {
        while (connections.size() < MAX_CON) {
            try {
                wait();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Closing connection is failed ", e);
            }
        }
        Iterator<Connection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Closing connection is failed ", e);
            }
            iterator.remove();
        }
        instance = null;
    }
}
