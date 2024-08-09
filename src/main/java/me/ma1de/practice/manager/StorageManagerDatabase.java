package me.ma1de.practice.manager;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.ma1de.practice.Practice;
import org.bson.Document;

import java.util.List;

@Getter
public class StorageManagerDatabase<T> implements StorageManager<T> {
    private final List<T> objects;
    private final MongoCollection<Document> collection;

    public StorageManagerDatabase(String collectionName) {
        this.objects = Lists.newArrayList();
        this.collection = Practice.getInstance().getMongoDatabase().getCollection(collectionName);
    }

    @Override
    public List<T> getObjects() {
        return objects;
    }

    @Override
    public void onLoad() {
        for (Document doc : collection.find()) {
            objects.add(Practice.getInstance().getGson().fromJson(
                    doc.toJson(),
                    new TypeToken<T>() {}.getType()
            ));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onShutdown() {
        for (Document doc : collection.find()) {
            T objSerialized = Practice.getInstance().getGson().fromJson(
                    doc.toJson(),
                    new TypeToken<T>() {}.getType()
            );

            if (objects.contains(objSerialized)) {
                continue;
            }

            objects.add(objSerialized);
        }

        for (T obj : objects) {
            if (collection.count(Document.parse(Practice.getInstance().getGson().toJson(obj))) > 0) {
                continue;
            }

            collection.insertOne(Document.parse(Practice.getInstance().getGson().toJson(obj)));
        }
    }
}