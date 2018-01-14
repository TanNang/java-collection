package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.BiConsumer;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {
    interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V newValue);
    }

    int size();
    boolean isEmpty();
    Map<K, V> clear();

    V put(K key, V value);
    V get(K key);
    V delete(K key);

    boolean hasKey(K key);
    boolean hasValue(V value);

    K[] keys();
    V[] values();
    Map.Entry<K, V>[] entries();

    Iterator<Map.Entry<K, V>> iterator();
    void forEach(BiConsumer<? super K, ? super V> action);
}
