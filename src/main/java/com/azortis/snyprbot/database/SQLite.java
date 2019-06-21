package com.azortis.snyprbot.database;

import com.azortis.snyprbot.SnyprBot;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("all")
public class SQLite implements Database{
    private String jdbcUrl;

    SQLite(SQLiteSettings settings){
        File dbFile = new File(settings.getFilePath(), settings.getFileName() + ".db");
        try{
            if(!dbFile.exists()){
                dbFile.createNewFile();
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not generate database file! Shutting down...");
            System.exit(0);
        }
        this.jdbcUrl = "jdbc:sqlite:" + dbFile.getPath();
    }

    @Override
    public Connection getConnection() {
        try{
            return DriverManager.getConnection(jdbcUrl);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
