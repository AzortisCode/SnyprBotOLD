package com.azortis.snyprbot.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL implements Database{
    private HikariDataSource dataSource;

    MySQL(MySQLSettings settings){
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        config.addDataSourceProperty("serverName", settings.getHost());
        config.addDataSourceProperty("port", settings.getPort());
        config.addDataSourceProperty("databaseName", settings.getDatabase());
        config.setUsername(settings.getUsername());
        config.setPassword(settings.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("properties", "useUnicode=true;characterEncoding=utf8");

        this.dataSource = new HikariDataSource(config);
    }



    @Override
    public Connection getConnection() {
        try{
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    void close(){
        this.dataSource.close();
    }
}
