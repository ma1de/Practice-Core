package me.ma1de.practice.util;

import org.bson.Document;

import lombok.experimental.UtilityClass;
import me.ma1de.practice.Practice;

@UtilityClass
public class BsonUtil {
    public Document parse(Object obj) {
        return Document.parse(Practice.getInstance().getGson().toJson(obj));
    }
}
