package me.ma1de.practice.match.event.impl;

import lombok.Getter;
import me.ma1de.practice.match.Match;
import me.ma1de.practice.match.event.MatchEvent;
import org.bukkit.entity.Player;

@Getter
public class MatchSpectateEndEvent extends MatchEvent {
    private final Player player;

    public MatchSpectateEndEvent(Player player, Match match) {
        super(match);

        this.player = player;
    }
}
