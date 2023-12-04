package me.ma1de.practice.queue;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;

@Getter
public class QueueHandler {
    private final List<Queue> queues = Lists.newArrayList();

    public Queue getQueue(String id) {
        return queues.stream().filter(queue -> queue.getId().equals(id)).findAny().orElse(null);
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
}
