package me.ma1de.practice.tournament;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.practice.kit.Kit;

@Getter @AllArgsConstructor
public class Tournament {
    private List<UUID> players;
    private Kit kit;
    private int phase;
    private long started, ended;
    private boolean enabled;

    public void start() {
        Bukkit.getPluginManager().callEvent(new TournamentStartEvent(this));
    }
}
