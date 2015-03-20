package org.glatres.stockage;

import java.sql.*;

public class SQLStockage extends StockageSystem {

    private String partition;
    private Connection connection;

    @Override
    public StockageSystem init() {
        partition = "core";
        String directory = bot().rootFile().getAbsolutePath();
        System.setProperty("derby.system.home", directory);

        String url = "jdbc:derby:" + partition + ";create=true;databaseName=core";
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private ResultSet exec(String query) {
        ResultSet result = null;
        try {
            Statement stat = connection.createStatement();
            result = stat.executeQuery(query);
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public <T> T read(String section, String key, Class<T> type) {
        // TODO Implement
        return null;
    }

    @Override
    public <T> StockageSystem write(String section, String key, T value) {
        // TODO Implement
        return this;
    }

    @Override
    public StockageSystem switchPartition(String newPartition) {
        partition = newPartition;
        // TODO: Implement switch
        return this;
    }

    @Override
    public String partition() {
        return partition;
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
