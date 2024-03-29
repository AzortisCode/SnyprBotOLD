package com.azortis.snyprbot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.azortis.snyprbot.commands.PingCMD;
import com.azortis.snyprbot.commands.SetActivityCMD;
import com.azortis.snyprbot.commands.StopCMD;
import com.azortis.snyprbot.database.DatabaseManager;
import com.azortis.snyprbot.music.MusicManager;
import com.azortis.snyprbot.music.commands.*;
import com.azortis.snyprbot.settings.SettingsManager;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class SnyprBot {

    private static JDA client;
    private static Config config;
    private static String directory;
    private static DatabaseManager databaseManager;
    private static SettingsManager settingsManager;
    private static Map<String, Command> commandMap = new HashMap<>();
    private static MusicManager musicManager;

    public static void main(String[] args)throws Exception{
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
        directory = new File(SnyprBot.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        directory = directory.replace("%20", " ");
        loadConfig();
        databaseManager = new DatabaseManager();
        settingsManager = new SettingsManager();
        registerCommands();
        musicManager = new MusicManager();
        client = new JDABuilder(config.getToken()).build();
        client.addEventListener(new EventListener());
        client.awaitReady();
    }

    private static void loadConfig(){
        File configFile = new File(directory, "config.json");
        if(!configFile.exists())copy(SnyprBot.class.getClassLoader().getResourceAsStream("config.json"), configFile);
        try{
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(configFile));
            config = gson.fromJson(reader, Config.class);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void registerCommands(){
        //Standalone
        commandMap.put("ping", new PingCMD());

        //Music
        commandMap.put("play", new PlayCMD());
        commandMap.put("leave", new LeaveCMD());
        commandMap.put("skip", new SkipCMD());
        commandMap.put("repeat", new RepeatCMD());
        commandMap.put("pause", new PauseCMD());
        commandMap.put("resume", new ResumeCMD());

        //Bot owner
        commandMap.put("stop", new StopCMD());
        commandMap.put("setactivity", new SetActivityCMD());
    }

    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JDA getClient() { return client; }

    public static Config getConfig(){
        return config;
    }

    public static String getDirectory(){
        return directory;
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public static MusicManager getMusicManager() {
        return musicManager;
    }
}
