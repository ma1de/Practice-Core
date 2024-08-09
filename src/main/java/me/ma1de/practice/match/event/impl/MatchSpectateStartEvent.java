package me.ma1de.practice.match.event.impl;

import lombok.Getter;
import me.ma1de.practice.match.Match;
import me.ma1de.practice.match.event.MatchEvent;
import org.bukkit.entity.Player;

@Getter
public class MatchSpectateStartEvent extends MatchEvent {
    private final Player spectator;

    public MatchSpectateStartEvent(Player spectator, Match match) {
        super(match);

        this.spectator = spectator;
    }
}
