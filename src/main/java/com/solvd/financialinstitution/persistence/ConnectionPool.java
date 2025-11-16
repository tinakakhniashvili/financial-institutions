package com.solvd.financialinstitution.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

public class ConnectionPool {

    private static final int POOL_SIZE = 10;
    private static final Deque<Connection> pool = new ArrayDeque<>();
    private static ConnectionPool instance;

    private static final String URL =
            "jdbc:mysql://localhost:3306/financial_institutions?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private ConnectionPool() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0; i < POOL_SIZE; i++) {
                pool.add(DriverManager.getConnection(URL, USER, PASSWORD));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) instance = new ConnectionPool();
            }
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        if (pool.isEmpty()) throw new RuntimeException("No connections available");
        return pool.poll();
    }

    public synchronized void releaseConnection(Connection connection) {
        pool.offer(connection);
    }
}
