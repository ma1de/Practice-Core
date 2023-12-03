package me.ma1de.practice.kit;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Kit {
    private String id, displayName;
    private List<ItemStack> items, armor;
    private boolean ranked, enabled;
}
