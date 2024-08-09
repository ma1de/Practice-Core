package me.ma1de.practice.util;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.*;

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