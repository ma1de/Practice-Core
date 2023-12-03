package me.ma1de.practice.gui;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.reflections.Reflections;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class GuiHandler {
    private final List<Gui> guis = Lists.newArrayList();
    private final Map<UUID, Gui> openGuis = Maps.newHashMap();

    public Gui getGui(String id) {
        return guis.stream().filter(gui -> gui.getId().equals(id)).findAny().orElse(null);
    }

    @SneakyThrows
    public void init() {
        for (Class<? extends Gui> clazz : new Reflections("me.ma1de.practice.gui.impl").getSubTypesOf(Gui.class)) {
            Gui gui = clazz.newInstance();

            if (!gui.isEnabled()) {
                continue;
            }

            guis.add(gui);
        }
    }
}
