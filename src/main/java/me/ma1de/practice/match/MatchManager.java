package me.ma1de.practice.match;

import me.ma1de.practice.manager.StorageManagerDatabase;

import java.util.Optional;
import java.util.UUID;

public class MatchManager extends StorageManagerDatabase<Match> {
    public MatchManager() {
        super("matches");
    }

    public Optional<Match> getMatchPlaying(UUID uuid) {
        return getObjects().stream().filter(match -> !match.isEnded() && match.getTeam(uuid).isPresent()).findAny();
    }

    public Optional<Match> getMatchSpectating(UUID uuid) {
        return getObjects().stream().filter(match -> !match.isEnded() && match.getSpectators().contains(uuid)).findAny();
    }
}