package com.azortis.snyprbot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.azortis.snyprbot.commands.PingCMD;
import com.azortis.snyprbot.music.AudioManager;
import com.azortis.snyprbot.music.commands.LeaveCMD;
import com.azortis.snyprbot.music.commands.PlayCMD;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class SnyprBot {

    private static JDA client;
    private static Config config;
    private static String directory;
    private static Map<String, Command> commandMap = new HashMap<>();
    private static AudioManager audioManager;

    public static void main(String[] args)throws Exception{
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
        directory = new File(SnyprBot.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        directory = directory.replace("%20", " ");
        loadConfig();
        registerCommands();
        audioManager = new AudioManager();
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

    public static Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public static AudioManager getAudioManager() {
        return audioManager;
    }
}
