package me.ma1de.practice.arena;

import org.bukkit.Location;
import org.bukkit.Material;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Arena {
    private String id, name;
    private Material icon;
    private Location first, second, spectator;
    private boolean enabled;
}
