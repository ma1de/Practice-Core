package me.ma1de.practice.util;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class PlayerUtil {
    public void clearInv(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[0]);
    }
}