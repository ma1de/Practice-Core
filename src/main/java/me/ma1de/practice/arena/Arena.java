package me.ma1de.practice.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.practice.util.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter @AllArgsConstructor
public class Arena {
    private String id, displayName;
    private Material icon;
    private Cuboid bounds;
    private Location firstSpawn, secondSpawn, spectatorSpawn;
    private boolean busy, enabled;
}