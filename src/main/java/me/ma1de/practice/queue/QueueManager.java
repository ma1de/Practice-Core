package me.ma1de.practice.queue;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.ladder.Ladder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class QueueManager {
    private final List<Queue> queues = Lists.newArrayList();

    public Optional<Queue> getQueue(Ladder ladder, boolean ranked) {
        return queues.stream().filter(queue -> queue.getLadder().getId().equalsIgnoreCase(ladder.getId()) && queue.isRanked() == ranked).findAny();
    }

    public boolean isQueueing(UUID uuid) {
        return queues.stream().anyMatch(queue -> queue.getQueueing().contains(uuid));
    }

    public void prepopulate() {
        Preconditions.checkArgument(queues.isEmpty());

        Practice.getInstance().getLadderManager().getObjects().forEach(ladder -> {
            queues.add(new Queue(ladder, new ArrayList<>(), false, false));

            if (ladder.isSupportsRanked()) {
                queues.add(new Queue(ladder, new ArrayList<>(), false, true));
            }
        });
    }
}