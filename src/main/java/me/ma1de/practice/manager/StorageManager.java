package me.ma1de.practice.manager;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface StorageManager<T> {
    List<T> getObjects();

    default Optional<T> getRandomObject(Predicate<T> pred) {
        List<T> objs = getAllObjects(pred);

        if (objs.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(objs.get(new Random().nextInt(objs.size() - 1)));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    default Optional<T> getObject(Predicate<T> pred) {
        return getObjects().stream().filter(pred).findAny();
    }

    default List<T> getAllObjects(Predicate<T> pred) {
        return getObjects().stream().filter(pred).collect(Collectors.toList());
    }

    default void addObject(T obj) {
        Preconditions.checkArgument(
                !getObjects().contains(obj),
                "Can't add an object which isn't in a list of objects (" + this.getClass().getName() + ")"
        );

        getObjects().add(obj);
    }

    default void removeObject(T obj) {
        Preconditions.checkArgument(
                getObjects().contains(obj),
                "Can't remove an object which is already in a list of objects (" + this.getClass().getName() + ")"
        );

        getObjects().remove(obj);
    }

    void onLoad();
    void onShutdown();
}