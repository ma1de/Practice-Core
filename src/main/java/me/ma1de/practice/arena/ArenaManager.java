package me.ma1de.practice.arena;

import lombok.Getter;
import me.ma1de.practice.manager.StorageManagerFile;

@Getter
public class ArenaManager extends StorageManagerFile<Arena> {
    public ArenaManager() {
        super("arenas");
    }
}