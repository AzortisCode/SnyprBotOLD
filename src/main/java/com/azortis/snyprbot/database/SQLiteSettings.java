package com.azortis.snyprbot.database;

public class SQLiteSettings {
    private String fileName;
    private String filePath;

    public SQLiteSettings(String fileName, String filePath){
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
}
