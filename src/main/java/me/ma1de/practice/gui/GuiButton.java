package me.ma1de.practice.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface GuiButton {
    String getId();
    String getDisplayName();

    Material getMaterial();

    void handle(Player player);

    boolean isEnabled();
}
