package me.ma1de.practice.gui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lombok.SneakyThrows;

public interface Gui {
    String getId();
    String getDisplayName();

    List<GuiButton> getButtons();
    
    boolean isEnabled();

    @SneakyThrows
    default void open(Player player) {
        if (getClass().getField("inv") == null) {
            throw new Exception("Unable to find field \"inv\"");
        }

        Inventory inv = getClass().getField("inv").get(getClass());
    }
}

