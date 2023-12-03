package me.ma1de.practice.match;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.practice.arena.Arena;
import me.ma1de.practice.kit.Kit;

@Getter @AllArgsConstructor
public class Match {
    private Arena arena;
    private Kit kit;
    private List<UUID> firstTeam, secondTeam;
    private long started, endTime;
    private boolean ranked, ended;

    public List<UUID> getAllPlayers() {
        List<UUID> all = new ArrayList<>();
        all.addAll(firstTeam);
        all.addAll(secondTeam);

        return all;
    }

    public long getElapsed() {
        return endTime != 0 ? endTime - started : System.currentTimeMillis() - started;
    }

    public void start() {
        if (started != 0) {
            return;
        }

        started = System.currentTimeMillis();

        Bukkit.getPluginManager().callEvent(new MatchStartEvent(this));
    }

    public void end() {
        if (endTime != 0 || ended) {
            return;
        }

        endTime = System.currentTimeMillis();
        ended = true;

        Bukkit.getPluginManager().callEvent(new MatchEndEvent(this));
    }
}
