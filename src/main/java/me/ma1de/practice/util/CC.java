package me.ma1de.practice.util;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class CC {
    public String translate(String x) {
        return ChatColor.translateAlternateColorCodes('&', x);
    }
}
