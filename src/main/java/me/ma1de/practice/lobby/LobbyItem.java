package me.ma1de.practice.lobby;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public abstract class LobbyItem {
    private String id, displayName;
    private Material material;
    private int invPosition;
    private boolean enabled;

    public abstract void handle(Player player);

    public ItemStack toStack() {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(displayName);

        stack.setItemMeta(meta);
        stack.setAmount(1);
        return stack;
    }
}
