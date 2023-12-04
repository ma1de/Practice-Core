package me.ma1de.practice.util;

import lombok.Getter;
import lombok.Setter;

/**
 * A pair of two objects.
 */
@Getter @Setter
public class Pair<T, K> {
    /**
     * First element of the pair.
     */
    private T first;

    /**
     * Second element of the pair.
     */
    private K second;
}
