package me.ma1de.practice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import me.ma1de.practice.arena.ArenaManager;
import me.ma1de.practice.duel.DuelHandler;
import me.ma1de.practice.ladder.LadderManager;
import me.ma1de.practice.manager.StorageManagerDatabase;
import me.ma1de.practice.manager.StorageManagerFile;
import me.ma1de.practice.match.MatchManager;
import me.ma1de.practice.queue.QueueManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter(AccessLevel.PUBLIC)
public class Practice extends JavaPlugin {
    @Getter(AccessLevel.PUBLIC)
    private static Practice instance; // lombok is weird

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final ExecutorService service = Executors.newFixedThreadPool(4);

    private MongoClient client;
    private MongoDatabase mongoDatabase;

    private ArenaManager arenaManager;
    private LadderManager ladderManager;
    private MatchManager matchManager;
    private QueueManager queueManager;
    private DuelHandler duelHandler;


    @Override
    @SneakyThrows
    public void onEnable() {
        instance = this;

        client = new MongoClient(
                this.getConfig().getString("MONGO.ADDRESS", "127.0.0.1"),
                this.getConfig().getInt("MONGO.PORT", 27017)
        );

        mongoDatabase = this.client.getDatabase(
                this.getConfig().getString("MONGO.NAME", "Practice")
        );

        this.setupManagers();

        new Reflections("me.ma1de.practice").getSubTypesOf(StorageManagerFile.class).forEach(manager -> {
            try {
                manager.newInstance().onLoad();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        new Reflections("me.ma1de.practice").getSubTypesOf(StorageManagerDatabase.class).forEach(manager -> {
            try {
                manager.newInstance().onLoad();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        new Reflections("me.ma1de.practice").getSubTypesOf(Listener.class).forEach(clazz -> {
            try {
                Bukkit.getPluginManager().registerEvents(clazz.newInstance(), this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onDisable() {
        instance = null;

        new Reflections("me.ma1de.practice").getSubTypesOf(StorageManagerFile.class).forEach(manager -> {
            try {
                manager.newInstance().onShutdown();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        new Reflections("me.ma1de.practice").getSubTypesOf(StorageManagerDatabase.class).forEach(manager -> {
            try {
                manager.newInstance().onShutdown();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        client.close();
    }

    @SneakyThrows
    public void registerCustomListener(Class<? extends Listener> clazz) {
        Bukkit.getPluginManager().registerEvents(clazz.newInstance(), this);
    }

    protected void setupManagers() {
        this.arenaManager = new ArenaManager();
        this.ladderManager = new LadderManager();
        this.matchManager = new MatchManager();
        this.queueManager = new QueueManager();
        this.duelHandler = new DuelHandler();

        this.queueManager.prepopulate();
    }
}