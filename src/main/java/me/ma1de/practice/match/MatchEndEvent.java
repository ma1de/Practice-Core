package me.ma1de.practice.match;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

@Getter
public class MatchEndEvent extends Event {
    private static HandlerList handlerList = new HandlerList();

    private final Match match;
    private boolean cancelled;

    public MatchEndEvent(Match match) {
        this.match = match;
        this.cancelled = false;
    }

    public MatchEndEvent(Match match, boolean cancelled) {
        this.match = match;
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
