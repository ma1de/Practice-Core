package me.ma1de.practice.match;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.arena.Arena;
import me.ma1de.practice.ladder.Ladder;
import me.ma1de.practice.match.event.impl.MatchEndEvent;
import me.ma1de.practice.match.event.impl.MatchPreStartEvent;
import me.ma1de.practice.match.event.impl.MatchStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

@Getter
public class Match {
    private final Arena arena;
    private final Ladder ladder;
    private final List<MatchTeam> teams;
    private final List<UUID> deadPlayers;
    @Setter private MatchTeam winner;
    @Setter private long startedAt, endedAt;
    private final boolean ranked;

    @Setter private boolean started, ended;

    public Match(Arena arena, Ladder ladder, List<MatchTeam> teams, boolean ranked) {
        Preconditions.checkNotNull(arena);
        Preconditions.checkNotNull(ladder);
        Preconditions.checkArgument(arena.isEnabled());
        Preconditions.checkArgument(!teams.isEmpty());
        Preconditions.checkArgument(!arena.isBusy());

        this.arena = arena;
        this.ladder = ladder;
        this.teams = teams;
        this.ranked = ranked;

        this.deadPlayers = Lists.newArrayList();
        this.winner = null;
        this.started = false;
        this.startedAt = 0L;
        this.ended = false;
        this.endedAt = 0L;
    }

    public void startMatch() {
        Preconditions.checkArgument(arena.isEnabled());
        Preconditions.checkArgument(!arena.isBusy());

        Bukkit.getPluginManager().callEvent(new MatchPreStartEvent(this));

        this.setStarted(true);
        this.setEnded(false);
        this.setWinner(null);

        this.setStartedAt(System.currentTimeMillis());
        this.setEndedAt(0L);

        this.getDeadPlayers().clear();

        for (MatchTeam team : teams) {
            Location spawn = team == teams.get(0) ? arena.getFirstSpawn() : arena.getSecondSpawn();

            team.forEach(p -> p.teleport(spawn));
        }

        this.startCountdown();
    }

    public void endMatch(MatchTeam winner) {
        Bukkit.getPluginManager().callEvent(new MatchEndEvent(this));

        this.setEnded(true);
        this.setWinner(winner);

        this.setEndedAt(System.currentTimeMillis());

        for (MatchTeam team : teams) {
            team.forEach(p -> {
                p.sendMessage(ChatColor.RED + "Match ended.");

                Bukkit.getScheduler().runTaskLater(Practice.getInstance(), () -> p.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0)), 60L);
            });
        }
    }

    private void startCountdown() {
        new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                for (MatchTeam team : teams) {
                    if (countdown == 0) {
                        team.forEach(p -> {
                            p.sendMessage(ChatColor.YELLOW + "Match started!");
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, -1f);
                        });

                        Bukkit.getPluginManager().callEvent(new MatchStartEvent(Match.this));

                        cancel();
                        return;
                    }

                    team.forEach(p -> {
                        p.sendMessage(ChatColor.GREEN.toString() + (countdown = countdown - 1) + "...");
                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, 1f);
                    });

                }
            }
        }.runTaskTimer(Practice.getInstance(), 20L, 0L);
    }
}