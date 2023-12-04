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
import me.ma1de.practice.util.CC;

@Getter @AllArgsConstructor
public class QueueGui implements Gui {
    private boolean ranked;

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
        Inventory inv = Bukkit.createInventory(null, 27, (ranked ? "Ranked " : "Unranked ") + "Queue");
        player.closeInventory();

        for (GuiButton button : getButtons()) {
           inv.addItem(button.toStack()); 
        }

        if (inv.isEmpty()) {
            inv.addItem(new GuiButton(){
                @Override
                public String getId() {
                    return "placeholder";
                }

                @Override
                public String getDisplayName() {
                    return CC.translate("&cThere are no queues available.");
                }

                @Override
                public Material getMaterial() {
                    return Material.BARRIER;
                }

                @Override
                public void handle(Player player) {
                    player.sendMessage(CC.translate("&cThere are no queues available."));
                    player.closeInventory();
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            }.toStack());
        }

        player.openInventory(inv);
        Practice.getInstance().getGuiHandler().getOpenGuis().put(player.getUniqueId(), this);
    }
}
