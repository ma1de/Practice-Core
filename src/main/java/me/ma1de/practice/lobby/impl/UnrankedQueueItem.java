package me.ma1de.practice.lobby.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.ma1de.practice.gui.impl.QueueGui;
import me.ma1de.practice.lobby.LobbyItem;
import me.ma1de.practice.util.CC;

public class UnrankedQueueItem extends LobbyItem {
    public UnrankedQueueItem() {
        super("uranked_queue", CC.translate("&cJoin Unranked Queue"), Material.IRON_SWORD, 0, true);
    }
    
    @Override
    public void handle(Player player) {
        new QueueGui(false).open(player);
    }
}
