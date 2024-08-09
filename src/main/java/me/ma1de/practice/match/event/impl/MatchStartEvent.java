package me.ma1de.practice.match.event.impl;

import me.ma1de.practice.match.Match;
import me.ma1de.practice.match.event.MatchEvent;

public class MatchStartEvent extends MatchEvent {
    public MatchStartEvent(Match match) {
        super(match);
    }
}
