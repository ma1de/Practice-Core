package me.ma1de.practice.match.event.impl;

import me.ma1de.practice.match.Match;
import me.ma1de.practice.match.event.MatchEvent;

public class MatchEndEvent extends MatchEvent {
    public MatchEndEvent(Match match) {
        super(match);
    }
}
