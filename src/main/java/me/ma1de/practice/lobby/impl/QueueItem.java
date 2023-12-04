package me.ma1de.practice.lobby.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.ma1de.practice.gui.impl.QueueGui;
import me.ma1de.practice.lobby.LobbyItem;
import me.ma1de.practice.util.CC;

@Getter
public class QueueItem extends LobbyItem {
    private final boolean ranked;
    
    public QueueItem(boolean ranked) {
        super(
                "queue",
                (ranked ? CC.translate("&cJoin Ranked Queue") : CC.translate("&cJoin Unranked Queue")),
                (ranked ? Material.DIAMOND_SWORD : Material.IRON_SWORD),
                (ranked ? 0 : 1),
                true);

        this.ranked = ranked; 
    }

    @Override
    public void handle(Player player) {
        new QueueGui(ranked).open(player);
    }
}
