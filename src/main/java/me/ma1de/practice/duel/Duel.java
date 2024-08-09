package me.ma1de.practice.duel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @AllArgsConstructor
public class Duel {
    private UUID invited, invitedBy;
    private long invitedAt;
    @Setter private boolean accepted;

    public long getRemaining() {
        return System.currentTimeMillis() - this.invitedAt;
    }

    public boolean isExpired() {
        return this.accepted || this.getRemaining() >= 60000L;
    }
}