package me.ma1de.practice.match;

import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;

import lombok.Getter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.util.BsonUtil;

@Getter
public class MatchHandler {
    private final List<Match> matches = Lists.newArrayList();
    private final MongoCollection<Document> collection = Practice.getInstance().getMongoDatabase().getCollection("matches");

    public Match getMatch(UUID uuid) {
        return matches.stream().filter(match -> match.getAllPlayers().contains(uuid)).findAny().orElse(null);
    }

    public void addMatch(Match match) {
        if (matches.stream().anyMatch(m -> m == match)) {
            return;
        }

        matches.add(match);
        collection.insertOne(BsonUtil.parse(match));
    }
}
