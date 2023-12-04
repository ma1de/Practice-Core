package me.ma1de.practice.queue;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.kit.Kit;

@Getter
public class QueueHandler {
    private final List<Queue> queues = Lists.newArrayList();

    public Queue getQueue(String id) {
        return queues.stream().filter(queue -> queue.getId().equals(id)).findAny().orElse(null);
    }

    public Queue getUnrankedQueue(String id) {
        return queues.stream().filter(queue -> queue.getId().equals(id) && !queue.isRanked()).findAny().orElse(null);
    }

    public Queue getRankedQueue(String id) {
        return queues.stream().filter(queue -> queue.getId().equals(id) && queue.isRanked()).findAny().orElse(null);
    }

    public void addQueue(Queue queue) {
        if (getQueue(queue.getId()) != null) {
            return;
        }

        queues.add(queue);
    }

    public void removeQueue(Queue queue) {
        if (getQueue(queue.getId()) == null) {
            return;
        }

        queues.remove(queue);
    }

    public void init() {
        for (Kit kit : Practice.getInstance().getKitHandler().getKits()) {
            addQueue(new Queue(kit.getId(), kit, Lists.newArrayList(), false, true));

            if (kit.isRanked()) {
                addQueue(new Queue(kit.getId(), kit, Lists.newArrayList(), true, true));
            }
        }

        queues.stream().forEach(Queue::start);
    }
}
