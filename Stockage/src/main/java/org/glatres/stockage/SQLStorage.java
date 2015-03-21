package org.glatres.stockage;

import java.sql.*;

public class SQLStorage extends StorageSystem {

    private Connection connection;

    @Override
    public StorageSystem init() {
        String directory = bot().rootFile().getAbsolutePath();
        System.setProperty("derby.system.home", directory);

        String url = "jdbc:derby:" + "core" + ";create=true;databaseName=core";
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
                System.out.println("Created table coreData");
            } catch (SQLException e) {
                if (tableAlreadyExists(e)) {
                    ;
                } else
                    throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        write("COREDATA", "BOT_NAME", "GLaDOS" + Math.random());
        String name = read("COREDATA", "BOT_NAME", String.class);
        System.out.println("Name: " + name);
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
        PreparedStatement stat = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        result = stat.executeQuery();
        return result;
    }

    @Override
    public <T> T read(String section, String key, Class<T> type) {
        try {
            ResultSet set = execQuery("SELECT * FROM " + section);
            if (set.next()) {
                String result = set.getString(key); // TODO: find proper type
                return (T) result;
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> StorageSystem write(String section, String key, T value) {
        // TODO Optimize the SQL part
        try {

            boolean alreadyExists = false;
            try {
                ResultSet set = execQuery("SELECT * FROM " + section);
                if (set.next()) {
                    if (set.getString(key) != null) {
                        alreadyExists = true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String query = null;
            if (alreadyExists) {
                query = "UPDATE " + section + " SET " + key + "='" + value + "'";
            } else {
                query = "INSERT INTO " + section + "(" + key + ") VALUES('" + value + "')";
            }
            exec(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
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
