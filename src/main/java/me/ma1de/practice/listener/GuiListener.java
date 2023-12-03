package me.ma1de.practice.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.ma1de.practice.Practice;
import me.ma1de.practice.gui.Gui;

public class GuiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!Practice.getInstance().getGuiHandler().getOpenGuis().containsKey(event.getWhoClicked().getUniqueId())) {
            return;
        }

        event.setCancelled(true);

        Gui gui = Practice.getInstance().getGuiHandler().getOpenGuis().get(event.getWhoClicked().getUniqueId());
        gui.getButtons().get(event.getRawSlot()).handle(((Player) event.getWhoClicked()));
    }
}
