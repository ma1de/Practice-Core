package me.ma1de.practice.match;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.arena.Arena;
import me.ma1de.practice.ladder.Ladder;
import me.ma1de.practice.match.event.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.util.MojangNameLookup;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class Match {
    private final Arena arena;
    private final Ladder ladder;
    private final List<MatchTeam> teams;
    private final List<UUID> deadPlayers, spectators;
    private final List<Block> placedBlocks;
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
        this.spectators = Lists.newArrayList();
        this.placedBlocks = Lists.newArrayList();
        this.winner = null;
        this.started = false;
        this.startedAt = 0L;
        this.ended = false;
        this.endedAt = 0L;
    }

    public Optional<MatchTeam> getTeam(UUID uuid) {
        return teams.stream().filter(team -> team.getUuids().contains(uuid)).findAny();
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

    public void addSpectator(UUID uuid, boolean silent) {
        Player player = Bukkit.getPlayer(uuid);
        Preconditions.checkNotNull(player);
        Preconditions.checkArgument(!getSpectators().contains(uuid));

        if (this.isEnded() && !silent) {
            player.sendMessage(ChatColor.RED + "You can't spectate this match.");
            return;
        }

        if ((System.currentTimeMillis() - getStartedAt()) < 5000L) {
            player.sendMessage(ChatColor.RED + "You can't spectate this match yet.");
            return;
        }

        MatchSpectateStartEvent event = new MatchSpectateStartEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            player.sendMessage(ChatColor.RED + "You can't spectate this match.");
            return;
        }

        this.getSpectators().add(uuid);

        Location spawn = arena.getSpectatorSpawn();
        player.teleport(spawn);

        if (!silent) {
            for (MatchTeam team : teams) {
                team.forEach(p -> p.sendMessage(ChatColor.YELLOW + player.getName() + " started spectating."));
            }
        }
    }

    public void removeSpectator(UUID uuid, boolean silent) {
        Preconditions.checkArgument(getSpectators().contains(uuid));

        Player player = Bukkit.getPlayer(uuid);
        Preconditions.checkNotNull(player);

        Bukkit.getPluginManager().callEvent(new MatchSpectateEndEvent(player, this));
        player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));

        if (!silent) {
            for (MatchTeam team : teams) {
                team.forEach(p -> p.sendMessage(ChatColor.YELLOW + player.getName() + " stopped spectating."));
            }
        }
    }

    public void markDead(UUID uuid) {
        Optional<MatchTeam> optTeam = getTeam(uuid);

        Preconditions.checkArgument(optTeam.isPresent());
        Preconditions.checkArgument(!getSpectators().contains(uuid));

        Player player = Bukkit.getPlayer(uuid);

        if (deadPlayers.size() >= optTeam.get().getUuids().size()) {
            this.endMatch(teams.stream().filter(team -> !team.equals(optTeam.get())).findAny().orElse(null));
        }

        if (player == null) {
            String name = MojangNameLookup.lookupName(uuid);

            for (MatchTeam team : teams) {
                team.forEach(p -> p.sendMessage(ChatColor.YELLOW + name + " disconnected."));

                if (!team.getUuids().contains(uuid)) {
                    return;
                }

                team.getUuids().remove(uuid);
            }
            return;
        }

        Bukkit.getPluginManager().callEvent(new MatchPlayerDeathEvent(player, optTeam.get(), this));

        player.spigot().respawn();
        this.addSpectator(uuid, true);
    }

    public void endMatch(MatchTeam winner) {
        Bukkit.getPluginManager().callEvent(new MatchEndEvent(this));

        this.setEnded(true);

        if (winner != null) {
            this.setWinner(winner);
        }

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
                        MatchStartEvent event = new MatchStartEvent(Match.this);
                        Bukkit.getPluginManager().callEvent(event);

                        if (event.isCancelled()) {
                            Match.this.endMatch(null);
                            return;
                        }

                        team.forEach(p -> {
                            p.sendMessage(ChatColor.YELLOW + "Match started!");
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, -1f);
                        });
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