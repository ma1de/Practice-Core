package me.ma1de.practice.listener;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.ma1de.practice.Practice;
import me.ma1de.practice.match.MatchEndEvent;
import me.ma1de.practice.match.MatchStartEvent;
import me.ma1de.practice.util.CC;

public class MatchListener implements Listener {
    @EventHandler
    public void onStart(MatchStartEvent event) {
        for (Player player : event.getMatch().getAllPlayers().stream().map(uuid -> Bukkit.getPlayer(uuid)).collect(Collectors.toList())) {
            player.getInventory().clear();

            for (Player online : Bukkit.getOnlinePlayers().stream().filter(p -> !event.getMatch().getAllPlayers().contains(p.getUniqueId())).collect(Collectors.toList())) {
                online.hidePlayer(player);
                player.hidePlayer(online);
            }

            event.getMatch().getKit().getItems().forEach(item -> player.getInventory().addItem(item));

            player.getInventory().setHelmet(event.getMatch().getKit().getArmor().get(0));
            player.getInventory().setChestplate(event.getMatch().getKit().getArmor().get(1));
            player.getInventory().setLeggings(event.getMatch().getKit().getArmor().get(2));
            player.getInventory().setBoots(event.getMatch().getKit().getArmor().get(3));

            if (event.getMatch().getFirstTeam().contains(player.getUniqueId())) {
                player.teleport(event.getMatch().getArena().getFirst());
            } else {
                player.teleport(event.getMatch().getArena().getSecond());
            }

            new BukkitRunnable() {
                int timer = 6;

                @Override
                public void run() {
                    timer--;

                    if (timer == 0) {
                        cancel();
                        return;
                    }

                    player.sendMessage(CC.translate("&cMatch is gonna start in " + timer + " seconds..."));
                }
            }.runTaskTimer(Practice.getInstance(), 0L, 20 * 5L);
        }
    }

    @EventHandler
    public void onEnd(MatchEndEvent event) {
        for (Player player : event.getMatch().getAllPlayers().stream().map(Bukkit::getPlayer).collect(Collectors.toList())) {
            player.sendMessage(CC.translate("&cMatch has ended."));

            for (Player online : Bukkit.getOnlinePlayers().stream().filter(p -> !event.getMatch().getAllPlayers().contains(p.getUniqueId())).collect(Collectors.toList())) {
                online.showPlayer(player);
                player.showPlayer(online);
            }

            player.getInventory().clear();
            Arrays.asList(player.getInventory().getArmorContents()).clear();
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 70, 0));

            Practice.getInstance().getLobbyItemHandler().getItems().forEach(it -> player.getInventory().setItem(it.getInvPosition(), it.toStack()));
        }
    }
}
