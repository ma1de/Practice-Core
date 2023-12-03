package me.ma1de.practice;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import lombok.Getter;
import me.ma1de.practice.arena.ArenaHandler;
import me.ma1de.practice.kit.KitHandler;
import me.ma1de.practice.match.MatchHandler;

@Getter
public class Practice extends JavaPlugin {
    private static Practice instance;

    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    private MongoClient client;
    private MongoDatabase mongoDatabase;

    private ArenaHandler arenaHandler;
    private KitHandler kitHandler;
    private MatchHandler matchHandler;

    @Override
    public void onEnable() {
        instance = this;

        long start = System.currentTimeMillis();

        try {
            client = new MongoClient("127.0.0.1", 27017);
            mongoDatabase = client.getDatabase("Practice");
        } catch (Exception ex) {
            getLogger().info("Unable to connect to the MongoDB database, shutting down...");
            getLogger().info("Exception (" + ex.getClass().getSimpleName() + ") message: " + ex.getMessage());
            System.exit(-1);
        }

        arenaHandler = new ArenaHandler();
        kitHandler = new KitHandler();
        matchHandler = new MatchHandler();

        long end = System.currentTimeMillis();

        getLogger().info("Started in " + ((end - start) / 1000) + " seconds.");
    }

    @Override
    public void onDisable() {
        instance = null;
        
        client.close();
    }

    public static Practice getInstance() {
        return instance;
    }
}
