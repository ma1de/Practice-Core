package me.ma1de.practice.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

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
        
        if (gui.getButtons().get(event.getSlot()) == null) {
            return;
        }

        gui.getButtons().get(event.getSlot()).handle(((Player) event.getWhoClicked()));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!Practice.getInstance().getGuiHandler().getOpenGuis().containsKey(event.getPlayer().getUniqueId())) {
            return;
        }

        Practice.getInstance().getGuiHandler().getOpenGuis().remove(event.getPlayer().getUniqueId());
    }
}
