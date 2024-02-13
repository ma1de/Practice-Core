package me.ma1de.practice;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import lombok.Getter;

@Getter
public enum Practice {
    INSTANCE;

    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting().create();

    private MongoClient client;
    private MongoDatabase database;

    public void load(JavaPlugin plugin) {
        client = new MongoClient(plugin.getConfig().getString("MONGO.HOST"), plugin.getConfig().getInt("MONGO.PORT"));
        database = client.getDatabase(plugin.getConfig().getString("MONGO.DATABASE"));
    }
}
