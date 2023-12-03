package me.ma1de.practice.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.ma1de.practice.Practice;
import me.ma1de.practice.lobby.LobbyItem;

public class LobbyItemListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }

        if (!event.getAction().name().contains("RIGHT_")) {
            return;
        }

        if (Practice.getInstance().getLobbyItemHandler().getItems().stream().noneMatch(item -> item.toStack().isSimilar(event.getItem()))) {
            return;
        }

        LobbyItem item = Practice.getInstance().getLobbyItemHandler().getItems().stream().filter(it -> it.toStack().isSimilar(event.getItem())).findAny().orElse(null);
        assert item != null;

        item.handle(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().clear();

        Practice.getInstance().getLobbyItemHandler().getItems().forEach(it -> event.getPlayer().getInventory().setItem(it.getInvPosition(), it.toStack()));
    }
}
