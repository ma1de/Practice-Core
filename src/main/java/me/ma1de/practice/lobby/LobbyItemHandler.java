package me.ma1de.practice.lobby;

import java.util.List;

import org.reflections.Reflections;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class LobbyItemHandler {
    private final List<LobbyItem> items = Lists.newArrayList();

    public LobbyItem getItem(String id) {
        return items.stream().filter(item -> item.getId().equals(id)).findAny().orElse(null);
    }

    @SneakyThrows
    public void init() {
        for (Class<? extends LobbyItem> clazz : new Reflections("me.ma1de.practice.lobby.impl").getSubTypesOf(LobbyItem.class)) {
            items.add(clazz.newInstance());
        }
    }
}
