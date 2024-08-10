package me.ma1de.practice.lobby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

@Getter @AllArgsConstructor
public enum LobbyItem {
    UNRANKED_QUEUE(0, ChatColor.GREEN + "Join Unranked Queue", new MaterialData(Material.IRON_SWORD)),
    RANKED_QUEUE(1, ChatColor.YELLOW + "Join Ranked Queue", new MaterialData(Material.DIAMOND_SWORD)),
    LEAVE_QUEUE(0, ChatColor.RED + "Leave Queue", new MaterialData(Material.INK_SACK, DyeColor.RED.getData()));

    private final int slot;
    private final String displayName;
    private final MaterialData icon;

    public ItemStack getItemStack() {
        ItemStack stack = new ItemStack(this.icon.getItemType(), 1, (short) 1, (byte) this.icon.getData());
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(this.displayName);
        stack.setItemMeta(meta);
        return stack;
    }

    public static void giveItems(Player player) {
        PlayerUtil.clearInv(player);

        if (Practice.getInstance().getQueueManager().isQueueing(player.getUniqueId())) {
            player.getInventory().setItem(LEAVE_QUEUE.slot, LEAVE_QUEUE.getItemStack());
            return;
        }

        player.getInventory().setItem(UNRANKED_QUEUE.slot, UNRANKED_QUEUE.getItemStack());
        player.getInventory().setItem(RANKED_QUEUE.slot, RANKED_QUEUE.getItemStack());
    }
}
