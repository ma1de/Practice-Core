package me.ma1de.practice.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class CC {
    public String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}