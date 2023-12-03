package me.ma1de.practice.lobby;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public abstract class LobbyItem {
    private String id, displayName;
    private Material material;
    private boolean enabled;

    public abstract void handle(Player player);
}
