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
            String createStat = "create table coreData (\n";
            createStat += " ID          INTEGER NOT NULL\n";
            createStat += "PRIMARY KEY GENERATED ALWAYS AS IDENTITY\n";
            createStat += "(START WITH 0, INCREMENT BY 1),\n";
            createStat += "BOT_NAME VARCHAR(30)\n";
            createStat += ")";
            try {
                exec(createStat);
            } catch (SQLException e) {
                if (tableAlreadyExists(e)) {
                    return this; // That's OK
                }
                throw e;
            }

            System.out.println("Created table coreData");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private boolean tableAlreadyExists(SQLException e) {
        return "X0Y32".equals(e.getSQLState());
    }

    private boolean exec(String query) throws SQLException {
        boolean result = false;
        Statement stat = connection.createStatement();
        result = stat.execute(query);
        stat.close();
        return result;
    }

    private ResultSet execQuery(String query) throws SQLException {
        ResultSet result = null;
        Statement stat = connection.createStatement();
        result = stat.executeQuery(query);
        stat.close();

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
