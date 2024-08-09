package me.ma1de.practice.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter @AllArgsConstructor
public class MatchTeam {
    private List<UUID> uuids;

    public List<Player> getAllOnlinePlayers() {
        return uuids.stream().filter(Objects::nonNull).map(Bukkit::getPlayer).collect(Collectors.toList());
    }

    public void forEach(Consumer<Player> consumer) {
        this.getAllOnlinePlayers().stream().distinct().forEach(consumer);
    }
}