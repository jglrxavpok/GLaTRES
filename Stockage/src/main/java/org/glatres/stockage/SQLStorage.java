package org.glatres.stockage;

import java.sql.*;

import org.glatres.lang.words.*;
import org.glatres.utils.*;

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

            createCoreTable();

            createLanguageTable("english");
            createLanguageTable("french");
        } catch (Exception e) {
            e.printStackTrace();
        }

        write("COREDATA", "BOT_NAME", "GLaTRES");
        String name = read("COREDATA", "BOT_NAME", String.class);

        System.out.println("Name: " + name);
        System.out.println(getWordInfos("english", "hello"));
        return this;
    }

    private void createCoreTable() throws SQLException {
        String createStat = "create table coreData (\n";
        createStat += "  ID INTEGER NOT NULL\n";
        createStat += "  PRIMARY KEY GENERATED ALWAYS AS IDENTITY\n";
        createStat += "  (START WITH 0, INCREMENT BY 1),\n";
        createStat += "  BOT_NAME VARCHAR(30)\n";
        createStat += ")";

        if (createTable(createStat)) {
            System.out.println("Created table coreData");
        }
    }

    private void createLanguageTable(String lang) throws SQLException {
        String createStat = "create table LANG." + lang + " (\n";
        createStat += "  ID INTEGER NOT NULL\n";
        createStat += "  PRIMARY KEY GENERATED ALWAYS AS IDENTITY\n";
        createStat += "  (START WITH 0, INCREMENT BY 1),\n";
        createStat += "  WORD VARCHAR(50) NOT NULL\n,";
        createStat += "  PRONUNCIATION VARCHAR(50)\n,";
        createStat += "  TYPE VARCHAR(50)\n";
        createStat += ")";

        if (createTable(createStat)) {
            System.out.println("Created table LANG." + lang);
        }
    }

    public StorageSystem saveWord(String lang, String word, String pronunciation, WordType type) {
        return saveWord(new WordInfo(lang, word, pronunciation, type));
    }

    public StorageSystem saveWord(WordInfo info) {
        write("LANG." + info.language(), 0, 0, new String[] { "WORD", "PRONUNCIATION", "TYPE" },
                new String[] { info.word(), info.pronunciation(), info.type().name() });
        return this;
    }

    public WordInfo getWordInfos(String lang, String word) {
        try {
            String query = "SELECT * FROM LANG." + lang + " \n";
            query += "  WHERE WORD='" + word + "'";
            ResultSet set = execQuery(query);
            if (set.next()) {
                String dbword = set.getString("WORD");
                String pronunciation = set.getString("pronunciation");
                String type = set.getString("TYPE");

                return new WordInfo(lang, dbword, pronunciation, WordType.valueOf(type));
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean createTable(String createStat) throws SQLException {
        try {
            exec(createStat);
        } catch (SQLException e) {
            if (tableAlreadyExists(e)) {
                return false;
            } else
                throw e;
        }
        return true;
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
        Object[] values = new Object[] { value };
        return write(section, 0, 0, new String[] { key }, values);
    }

    @Override
    public StorageSystem write(String section, int mainKeyIndex, int mainValueIndex, String[] keys, Object[] values) {
        // TODO Optimize the SQL part
        String keysJoined = Strings.join(keys, ",", "");
        String valuesJoined = Strings.join(values, ",", "'");
        try {

            boolean alreadyExists = false;
            try {
                String checkQuery = "SELECT * FROM " + section + " WHERE " + keys[mainKeyIndex] + " = '" + values[mainValueIndex] + "'";
                ResultSet set = execQuery(checkQuery);
                if (set.next()) {
                    if (set.getString(keys[mainKeyIndex]) == null) {
                        alreadyExists = true;
                    }
                }
                System.out.println(alreadyExists);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String query = null;
            if (alreadyExists) {
                query = "UPDATE " + section + " SET ";
                for (int i = 0; i < keys.length; i++) {
                    query += "  " + keys[i] + " = '" + values[i] + "'";
                    if (i != keys.length - 1)
                        query += ",";
                }
            } else {
                query = "INSERT INTO " + section + "(" + keysJoined + ") VALUES(" + valuesJoined + ")";
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
