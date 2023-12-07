package me.ma1de.practice.tournament;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

@Getter
public class TournamentStartEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();

    private final Tournament tournament;
    private boolean cancelled;

    public TournamentStartEvent(Tournament tournament) {
        this.tournament = tournament;
        this.cancelled = false;
    }

    public TournamentStartEvent(Tournament tournament, boolean cancelled) {
        this.tournament = tournament;
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
