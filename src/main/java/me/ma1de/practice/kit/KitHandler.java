package me.ma1de.practice.kit;

import java.util.List;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import lombok.Getter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.util.BsonUtil;

@Getter
public class KitHandler {
    private final List<Kit> kits = Lists.newArrayList();
    private final MongoCollection<Document> collection = Practice.getInstance().getMongoDatabase().getCollection("kits");

    public Kit getKit(String id) {
        return kits.stream().filter(kit -> kit.getId().equals(id)).findAny().orElse(null);
    }

    public void addKit(Kit kit) {
        if (getKit(kit.getId()) != null) {
            return;
        }

        kits.add(kit);
        collection.insertOne(BsonUtil.parse(kit));
    }

    public void removeKit(Kit kit) {
        if (getKit(kit.getId()) == null) {
            return;
        }

        kits.remove(kit);
        collection.findOneAndDelete(Filters.eq(kit.getId()));
    }
}
