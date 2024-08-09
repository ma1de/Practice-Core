package me.ma1de.practice.match.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.ma1de.practice.match.Match;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class MatchEvent extends Event {
    @Getter(AccessLevel.PUBLIC)
    private static final HandlerList handlerList = new HandlerList();

    private final Match match;
    @Setter private boolean cancelled;

    public MatchEvent(Match match) {
        this.match = match;
        this.cancelled = false;
    }

    public MatchEvent(Match match, boolean cancelled) {
        this.match = match;
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}