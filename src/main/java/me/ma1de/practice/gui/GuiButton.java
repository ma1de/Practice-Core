package me.ma1de.practice.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface GuiButton {
    String getId();
    String getDisplayName();

    Material getMaterial();

    void handle(Player player);

    boolean isEnabled();

    default ItemStack toStack() {
        ItemStack stack = new ItemStack(getMaterial());
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(getDisplayName());

        stack.setItemMeta(meta);
        return stack;
    }
}
