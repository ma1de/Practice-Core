package me.ma1de.practice.party;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Party {
    private UUID owner;
    private List<UUID> members;
}
