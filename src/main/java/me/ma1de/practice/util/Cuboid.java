package me.ma1de.practice.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Location;

import java.util.List;

@Getter
public class Cuboid {
    private final Location first;
    private final Location second;

    public Cuboid(Location first, Location second) {
        this.first = Preconditions.checkNotNull(first);
        this.second = Preconditions.checkNotNull(second);

        Preconditions.checkArgument(
                first.getWorld().getName().equalsIgnoreCase(second.getWorld().getName()),
                "Unable to create a Cuboid with two locations in two different world."
        );
    }

    public double getMaxX() {
        return Math.max(first.getX(), second.getX());
    }

    public double getMinX() {
        return Math.min(first.getX(), second.getX());
    }

    public double getMaxY() {
        return Math.max(first.getY(), second.getY());
    }

    public double getMinY() {
        return Math.min(first.getY(), second.getY());
    }

    public double getMaxZ() {
        return Math.max(first.getZ(), second.getZ());
    }

    public double getMinZ() {
        return Math.min(first.getZ(), second.getZ());
    }

    public List<Location> getAllLocations() {
        List<Location> locs = Lists.newArrayList();

        for (double x = getMinX(); x < getMaxX(); x++) {
            for (double y = getMinY(); y < getMaxY(); y++) {
                for (double z = getMinZ(); z < getMaxZ(); z++) {
                    locs.add(new Location(
                            first.getWorld(),
                            x,
                            y,
                            z
                    ));
                }
            }
        }

        return locs;
    }

    public boolean contains(Location loc) {
        return (loc.getX() > getMinX() && loc.getX() < getMaxX())
                && (loc.getY() > getMinY() && loc.getY() < getMaxY())
                && (loc.getZ() > getMinZ() && loc.getZ() < getMaxZ());
    }
}