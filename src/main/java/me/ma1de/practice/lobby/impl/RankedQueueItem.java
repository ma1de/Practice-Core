package me.ma1de.practice.lobby.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.ma1de.practice.gui.impl.QueueGui;
import me.ma1de.practice.lobby.LobbyItem;
import me.ma1de.practice.util.CC;

public class RankedQueueItem extends LobbyItem {
    public RankedQueueItem() {
        super("ranked_queue", CC.translate("&cJoin Ranked Queue"), Material.DIAMOND_SWORD, 1, true);
    }
    
    @Override
    public void handle(Player player) {
        new QueueGui(true).open(player);
    }
}
