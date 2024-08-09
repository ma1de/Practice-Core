package me.ma1de.practice.manager;

import com.google.common.collect.Lists;
import io.leangen.geantyref.TypeToken;
import lombok.Getter;
import lombok.SneakyThrows;
import me.ma1de.practice.Practice;
import me.ma1de.practice.util.FileUtil;
import org.bukkit.Bukkit;

import java.io.File;
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

            FileUtil.writeToFile(file, "[]");
        }

        try {
            objects = Practice.getInstance().getGson().fromJson(
                    FileUtil.readFile(file),
                    new TypeToken<List<T>>() {}.getType()
            );
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Unable to load " + this.getClass().getName() + ": " + ex.getMessage() + " (" + ex.getClass().getName() + ")");
            Bukkit.getLogger().severe("Shutting down...");
            System.exit(0);
        }
    }

    @Override
    @SneakyThrows
    public void onShutdown() {
        String serialized;

        try {
            serialized = Practice.getInstance().getGson().toJson(objects);
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Unable to serialize a list of objects in " + this.getClass().getName() + ": " + ex.getMessage() + " (" + ex.getClass().getName() + ")");
            Bukkit.getLogger().severe("Shutting down...");
            System.exit(0);
            return;
        }


        FileUtil.writeToFile(
                file,
                serialized
        );
    }
}