package me.ma1de.practice.match.event.impl;

import lombok.Getter;
import me.ma1de.practice.match.Match;
import me.ma1de.practice.match.MatchTeam;
import me.ma1de.practice.match.event.MatchEvent;
import org.bukkit.entity.Player;

@Getter
public class MatchPlayerDeathEvent extends MatchEvent {
    private final Player player;
    private final MatchTeam team;

    public MatchPlayerDeathEvent(Player player, MatchTeam team, Match match) {
        super(match);

        this.player = player;
        this.team = team;
    }
}
