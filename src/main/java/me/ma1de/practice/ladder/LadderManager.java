package me.ma1de.practice.ladder;

import lombok.Getter;
import me.ma1de.practice.manager.StorageManagerFile;

@Getter
public class LadderManager extends StorageManagerFile<Ladder> {
    public LadderManager() {
        super("ladders");
    }
}