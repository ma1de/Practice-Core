package me.ma1de.practice.gui;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;

@Getter
public class GuiHandler {
    private final List<Gui> guis = Lists.newArrayList();
    private final Map<UUID, Gui> openGuis = Maps.newHashMap();

    public Gui getGui(String id) {
        return guis.stream().filter(gui -> gui.getId().equals(id)).findAny().orElse(null);
    }

    public void addGui(Gui gui) {
        if (getGui(gui.getId()) != null) {
            return;
        }

        guis.add(gui);
    }
}
