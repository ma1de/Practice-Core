package me.ma1de.practice.ladder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;

@Getter @AllArgsConstructor
public class Ladder {
    private String id, displayName;
    private MaterialData icon;
    private List<ItemStack> armor, items;
    private boolean supportsRanked, sumoOnly, enabled;
}