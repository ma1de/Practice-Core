package me.ma1de.practice;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter(AccessLevel.PUBLIC)
public class Practice extends JavaPlugin {
    @Getter(AccessLevel.PUBLIC)
    private static Practice instance; // gson is weird

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}