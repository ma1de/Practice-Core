package me.ma1de.practice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

@Getter
public class EventStart extends Event {
    private static final HandlerList handlerList = new HandlerList();

    private me.ma1de.practice.event.Event event;
    private boolean cancelled;

    public EventStart(me.ma1de.practice.event.Event event) {
        this.event = event;
        this.cancelled = false;
    }

    public EventStart(me.ma1de.practice.event.Event event, boolean cancelled) {
        this.event = event;
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
