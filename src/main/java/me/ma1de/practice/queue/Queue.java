package me.ma1de.practice.queue;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.ma1de.practice.ladder.Ladder;
import me.ma1de.practice.queue.event.QueueFoundEvent;
import me.ma1de.practice.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Getter @AllArgsConstructor
public class Queue {
    private Ladder ladder;
    private List<UUID> queueing;
    @Setter private boolean frozen, ranked;

    public Queue() {
        Executors.newScheduledThreadPool(2).scheduleAtFixedRate(
                this.getThread(),
                1L,
                300L,
                TimeUnit.MILLISECONDS
        );
    }

    public Optional<Pair<UUID, UUID>> getNextPair() {
        if (queueing.size() < 2) {
            return Optional.empty();
        }

        return Optional.of(
                new Pair<>(queueing.get(0), queueing.get(1))
        );
    }

    // Simple queueing system
    // TODO implement ranked queue
    public Thread getThread() {
        return new Thread(() -> {
            // Notify everyone and kick them from the queue
            if (this.frozen) {
                this.messageAll(ChatColor.RED + "The " + (ranked ? "Ranked " : "Unranked ") + ladder.getDisplayName() + " queue is frozen for the time being.");
                this.queueing.clear();
                return;
            }

            Optional<Pair<UUID, UUID>> nextPair = getNextPair();

            if (!nextPair.isPresent()) {
                return;
            }

            Pair<UUID, UUID> pair = nextPair.get();

            if (this.ranked) {
                this.messagePair(pair, ChatColor.RED + "Ranked is closed for now.");
                this.removePair(pair);
                return;
            }

            QueueFoundEvent event = new QueueFoundEvent(
                    pair,
                    this
            );

            Bukkit.getPluginManager().callEvent(event);

            this.removePair(pair);

            if (event.isCancelled()) {
                this.messagePair(pair, ChatColor.RED + "Unable to queue at this point, reason: " + event.getReasonCancelled());
            }
        });
    }

    public void addPlayer(UUID uuid) {
        Preconditions.checkNotNull(uuid);
        Preconditions.checkNotNull(Bukkit.getPlayer(uuid));
        Preconditions.checkArgument(!queueing.contains(uuid));

        this.queueing.add(uuid);
    }

    public void removePair(Pair<UUID, UUID> pair) {
        Preconditions.checkNotNull(pair);
        Preconditions.checkNotNull(pair.getKey());
        Preconditions.checkNotNull(pair.getValue());

        Preconditions.checkArgument(this.queueing.contains(pair.getKey()));
        Preconditions.checkArgument(this.queueing.contains(pair.getValue()));

        this.queueing.remove(pair.getKey());
        this.queueing.remove(pair.getValue());
    }

    private void messagePair(Pair<UUID, UUID> pair, String message) {
        Stream.of(pair.getKey(), pair.getValue()).map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(p -> p.sendMessage(message));
    }

    private void messageAll(String message) {
        queueing.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(p -> p.sendMessage(message));
    }
}