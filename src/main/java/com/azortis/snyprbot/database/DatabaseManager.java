package com.azortis.snyprbot.database;

import com.azortis.snyprbot.Config;
import com.azortis.snyprbot.SnyprBot;

public class DatabaseManager {
    private Database database;
    private MySQL mySQL; //Stored, so the dataSource can be closed correctly.

    public DatabaseManager(){
        if(SnyprBot.getConfig().getGuildSettings().get("mysql").equals("true")){
            Config config = SnyprBot.getConfig();
            String host = config.getGuildSettings().get("host");
            String port = config.getGuildSettings().get("port");
            String database = config.getGuildSettings().get("database");
            String username = config.getGuildSettings().get("username");
            String password = config.getGuildSettings().get("password");
            MySQLSettings mySQLSettings = new MySQLSettings(host, port, database, username, password);
            mySQL = new MySQL(mySQLSettings);
            this.database = mySQL;
        }else{
            String fileName = "guildSettings";
            String filePath = SnyprBot.getDirectory();
            SQLiteSettings sqLiteSettings = new SQLiteSettings(fileName, filePath);
            this.database = new SQLite(sqLiteSettings);
        }
    }

    public void closeConnection() {
        if (mySQL != null) mySQL.close();
    }

    public Database getDatabase() {
        return database;
    }
}
