package me.ma1de.practice.duel;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
public class DuelHandler {
    private final List<Duel> duels = Lists.newArrayList();

    public DuelHandler() {
        Executors.newScheduledThreadPool(4).scheduleAtFixedRate(() -> {
            List<Duel> toRemove = duels.stream().filter(Duel::isExpired).collect(Collectors.toList());
            duels.removeAll(toRemove);

            Bukkit.getLogger().info("Cleaned " + toRemove.size() + " expired duels from cache.");
        }, 1L, 60L, TimeUnit.SECONDS);
    }

    public List<Duel> getDuels(UUID invited) {
        return duels.stream().filter(duel -> !duel.isExpired() && duel.getInvited().equals(invited)).collect(Collectors.toList());
    }

    public List<Duel> getDuelsFrom(UUID uuid) {
        return duels.stream().filter(duel -> !duel.isExpired() && duel.getInvitedBy().equals(uuid)).collect(Collectors.toList());
    }

    public void addDuel(Duel duel) {
        Preconditions.checkArgument(!duel.isExpired());
        Preconditions.checkArgument(duel.getInvited() != null);
        Preconditions.checkArgument(duel.getInvitedBy() != null);
        Preconditions.checkArgument(getDuels(duel.getInvited()).stream().anyMatch(d -> d.getInvited().equals(duel.getInvited()) && d.getInvitedBy().equals(duel.getInvitedBy())));

        duels.add(duel);
    }
}