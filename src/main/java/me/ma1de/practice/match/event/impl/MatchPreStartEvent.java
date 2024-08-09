package me.ma1de.practice.match.event.impl;

import me.ma1de.practice.match.Match;
import me.ma1de.practice.match.event.MatchEvent;

public class MatchPreStartEvent extends MatchEvent {
    public MatchPreStartEvent(Match match) {
        super(match);
    }
}
