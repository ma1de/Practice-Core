package me.ma1de.practice.gui.impl;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.gui.Gui;
import me.ma1de.practice.gui.GuiButton;
import me.ma1de.practice.kit.Kit;
import me.ma1de.practice.queue.Queue;

@Getter @AllArgsConstructor
public class QueueGui implements Gui {
    private boolean ranked;

    private final Inventory inv = Bukkit.createInventory(null, 27, (ranked ? "Ranked " : "Unranked ") + "Queue");

    @Override
    public String getId() {
        return (ranked ? "ranked" : "unranked");
    }

    @Override
    public String getDisplayName() {
        return (ranked ? "Join Ranked Queue" : "Join Unranked Queue");
    }

    @Override
    public List<GuiButton> getButtons() {
        List<GuiButton> buttons = new ArrayList<>();

        for (Kit kit : Practice.getInstance().getKitHandler().getKits()) {
            if (!kit.isEnabled()) {
                continue;
            }

            if (kit.isRanked() != ranked) {
                continue;
            }

            buttons.add(new GuiButton() {
               @Override
               public String getId() {
                   return kit.getId();
               }

               @Override
               public String getDisplayName() {
                   return kit.getDisplayName();
               }

               @Override
               public Material getMaterial() {
                   return kit.getMaterial();
               }

               @Override
               public void handle(Player player) {
                   Queue queue = (ranked 
                           ? Practice.getInstance().getQueueHandler().getRankedQueue(kit.getId()) 
                           : Practice.getInstance().getQueueHandler().getUnrankedQueue(kit.getId()));

                   queue.addPlayer(player.getUniqueId());
               }

               @Override
               public boolean isEnabled() {
                   return true;
               }
            });
        }

        return buttons;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void open(Player player) {
       player.closeInventory();

       player.openInventory(inv);
       Practice.getInstance().getGuiHandler().getOpenGuis().put(player.getUniqueId(), this);
    }
}
