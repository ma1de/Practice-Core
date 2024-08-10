package me.ma1de.practice.queue.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ma1de.practice.queue.Queue;
import me.ma1de.practice.util.Pair;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class QueueFoundEvent extends Event {
    @Getter(AccessLevel.PUBLIC)
    private static final HandlerList handlerList = new HandlerList();

    private final Pair<UUID, UUID> pair;
    private final Queue queue;
    @Setter private String reasonCancelled;
    @Setter private boolean cancelled;

    public QueueFoundEvent(Pair<UUID, UUID> pair, Queue queue) {
        this.pair = pair;
        this.queue = queue;
        this.cancelled = false;
        this.reasonCancelled = "unspecified";
    }

    public QueueFoundEvent(Pair<UUID, UUID> pair, Queue queue, boolean cancelled) {
        this.pair = pair;
        this.queue = queue;
        this.cancelled = cancelled;
        this.reasonCancelled = "unspecified";
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
