package me.ma1de.practice.event;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Event {
    private Location spawn, first, second;
    private List<UUID> waiting, playing;
    private EventType type;
    private boolean enabled;

    public void start() {
        Bukkit.getPluginManager().callEvent(new EventStart(this));
    }
}
