package me.ma1de.practice.arena;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import lombok.Getter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.util.BsonUtil;

@Getter
public class ArenaHandler {
    private final List<Arena> arenas = Lists.newArrayList();
    private final MongoCollection<Document> collection = Practice.getInstance().getMongoDatabase().getCollection("arenas");

    public Arena getArena(String id) {
        return arenas.stream().filter(arena -> arena.getId().equals(id)).findAny().orElse(null);
    }
    
    public Arena getRandomArena() {
        return arenas.get(ThreadLocalRandom.current().nextInt(0, arenas.size() - 1));
    }

    public void addArena(Arena arena) {
        if (getArena(arena.getId()) == null) {
            return;
        }

        arenas.add(arena);
        collection.insertOne(BsonUtil.parse(arena));
    }

    public void removeArena(Arena arena) {
        if (getArena(arena.getId()) == null) {
            return;
        }

        arenas.remove(arena);
        collection.findOneAndDelete(Filters.eq("id", arena.getId()));
    }
}
