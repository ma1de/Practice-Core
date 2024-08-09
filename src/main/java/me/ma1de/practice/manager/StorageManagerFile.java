package me.ma1de.practice.manager;

import com.google.common.collect.Lists;
import io.leangen.geantyref.TypeToken;
import lombok.Getter;
import lombok.SneakyThrows;
import me.ma1de.practice.Practice;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@Getter
public class StorageManagerFile<T> implements StorageManager<T> {
    private List<T> objects;
    private final File file;

    public StorageManagerFile(String fileName) {
        this.objects = Lists.newArrayList();
        this.file = new File(Practice.getInstance().getDataFolder(), fileName + ".json");
    }

    @Override
    public List<T> getObjects() {
        return objects;
    }

    @Override
    @SneakyThrows
    public void onLoad() {
        if (!file.exists()) {
            boolean created = file.createNewFile();

            if (!created) {
                throw new Exception();
            }

            FileUtils.writeStringToFile(file, "[]");
        }

        objects = Practice.getInstance().getGson().fromJson(
                FileUtils.readFileToString(file),
                new TypeToken<List<T>>() {}.getType()
        );
    }

    @Override
    @SneakyThrows
    public void onShutdown() {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write("");
        }

        FileUtils.writeStringToFile(
                file,
                Practice.getInstance().getGson().toJson(objects)
        );
    }
}