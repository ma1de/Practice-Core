package me.ma1de.practice.util;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import me.ma1de.practice.Practice;

import java.io.*;
import java.util.concurrent.Future;

@UtilityClass
public class FileUtil {
    @SneakyThrows
    public String readFile(File file) {
        Preconditions.checkArgument(file.exists());
        Preconditions.checkNotNull(file);

        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }

        return builder.toString();
    }

    // WIP
    @SneakyThrows
    public String readFileAsync(File file) {
        Preconditions.checkArgument(file.exists());
        Preconditions.checkNotNull(file);

        Future<String> future = Practice.getInstance().getService().submit(() -> {
            StringBuilder builder = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }

            return builder.toString();
        });

        while (!future.isDone()) {
            long a = 0L; // implement proper logging later, for now just do something
        }

        return future.get();
    }

    @SneakyThrows
    public void writeToFile(File file, String text) {
        Preconditions.checkArgument(file.exists());
        Preconditions.checkNotNull(text);

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write("");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        }
    }
}