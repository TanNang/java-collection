package com.zfl9.collection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.BiConsumer;

public class LinkedHashMap<K, V> implements Map<K, V> {
    static class Entry<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Entry<K, V> next;
        Entry<K, V> before, after;

        Entry(int hash, K key, V value, Entry<K, V> next,
              Entry<K, V> before, Entry<K, V> after)
        {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
            this.before = before;
            this.after = after;
        }
        Entry(int hash, K key, V value, Entry<K, V> next) {
            this(hash, key, value, next, null, null);
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public String toString() {
            return key + " = " + value;
        }
    }

    Entry<K, V>[] table;
    static final int DEFAULT_INIT_CAPACITY = 32;

    final float loadFactor;
    static final float DEFAULT_LOAD_FACTOR = 0.75F;

    int size;

    final boolean accessOrder;
    final Entry<K, V> HEAD = new Entry<>(0, null, null, null);
    final Entry<K, V> TAIL = new Entry<>(0, null, null, null);

    {
        HEAD.after = TAIL;
        TAIL.before = HEAD;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public LinkedHashMap(int initCapacity, float loadFactor, boolean accessOrder) {
        table = (Entry<K, V>[]) new Entry[initCapacity];
        this.loadFactor = loadFactor;
        this.accessOrder = accessOrder;
    }
    public LinkedHashMap(int initCapacity, float loadFactor) {
        this(initCapacity, loadFactor, false);
    }
    public LinkedHashMap(int initCapacity, boolean accessOrder) {
        this(initCapacity, DEFAULT_LOAD_FACTOR, accessOrder);
    }
    public LinkedHashMap(int initCapacity) {
        this(initCapacity, DEFAULT_LOAD_FACTOR, false);
    }
    public LinkedHashMap(boolean accessOrder) {
        this(DEFAULT_INIT_CAPACITY, DEFAULT_LOAD_FACTOR, accessOrder);
    }
    public LinkedHashMap() {
        this(DEFAULT_INIT_CAPACITY, DEFAULT_LOAD_FACTOR, false);
    }

    public int size() {
        return size;
    }
    public int capacity() {
        return table.length;
    }
    public float loadFactor() {
        return loadFactor;
    }
    public boolean accessOrder() {
        return accessOrder;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public LinkedHashMap<K, V> clear() {
        for (int i = 0; i < table.length; i++)
            table[i] = null;
        HEAD.after = TAIL;
        TAIL.before = HEAD;
        size = 0;
        return this;
    }

    static int hash(Object key) {
        return key == null ? 0 : key.hashCode();
    }
    static int indexFor(int hash, int length) {
        while (hash < 0) hash += length;
        return hash % length;
    }
    int indexFor(int hash) {
        return indexFor(hash, table.length);
    }

    void manualExpansion(int minCapacity) {
        if (minCapacity <= table.length)
            return;
        @SuppressWarnings({"rawtypes", "unchecked"})
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[minCapacity];
        Entry<K, V> entry; int index;
        for (int i = 0; i < table.length; i++) {
            while (table[i] != null) {
                entry = table[i];
                table[i] = entry.next;
                index = indexFor(entry.hash, minCapacity);
                entry.next = newTable[index];
                newTable[index] = entry;
            }
        }
        table = newTable;
    }
    void autoExpansion(int numOfAddElem) {
        if (size + numOfAddElem > table.length * loadFactor)
            manualExpansion(table.length * 2);
    }

    public LinkedHashMap<K, V> ensureCapacity(int minCapacity) {
        manualExpansion(minCapacity);
        return this;
    }

    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (hash == entry.hash && (key == null ? key == entry.key : key.equals(entry.key))) {
                V oldValue = entry.value;
                entry.value = value;
                if (accessOrder) {
                    entry.before.after = entry.after;
                    entry.after.before = entry.before;
                    entry.before = HEAD;
                    entry.after = HEAD.after;
                    HEAD.after = entry;
                    entry.after.before = entry;
                }
                return oldValue;
            }
        }
        autoExpansion(1);
        table[index] = new Entry<K, V>(hash, key, value, table[index], TAIL.before, TAIL);
        TAIL.before = table[index];
        TAIL.before.before.after = TAIL.before;
        size++;
        return null;
    }
    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (hash == entry.hash && (key == null ? key == entry.key : key.equals(entry.key))) {
                if (accessOrder) {
                    entry.before.after = entry.after;
                    entry.after.before = entry.before;
                    entry.before = HEAD;
                    entry.after = HEAD.after;
                    HEAD.after = entry;
                    entry.after.before = entry;
                }
                return entry.value;
            }
        }
        return null;
    }
    public V delete(K key) {
        int hash = hash(key);
        int index = indexFor(hash);
        if (table[index] == null)
            return null;
        if (hash == table[index].hash &&
            (key == null ? key == table[index].key : key.equals(table[index].key))) {
            table[index].before.after = table[index].after;
            table[index].after.before = table[index].before;
            V value = table[index].value;
            table[index] = table[index].next;
            size--;
            return value;
        } else {
            for (Entry<K, V> entry = table[index]; entry.next != null; entry = entry.next) {
                if (hash == entry.next.hash &&
                    (key == null ? key == entry.next.key : key.equals(entry.next.key))) {
                    entry.next.before.after = entry.next.after;
                    entry.next.after.before = entry.next.before;
                    V value = entry.next.value;
                    entry.next = entry.next.next;
                    size--;
                    return value;
                }
            }
        }
        return null;
    }

    public boolean hasKey(K key) {
        int hash = hash(key);
        int index = indexFor(hash);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (hash == entry.hash && (key == null ? key == entry.key : key.equals(entry.key)))
                return true;
        }
        return false;
    }
    public boolean hasValue(V value) {
        for (Entry<K, V> entry = HEAD.after; entry != TAIL; entry = entry.after) {
            if (value == null ? value == entry.value : value.equals(entry.value))
                return true;
        }
        return false;
    }

    public K[] keys() {
        if (size == 0)
            return null;
        @SuppressWarnings("unchecked")
        K[] keys = (K[]) Array.newInstance(HEAD.after.key.getClass(), size);
        int index = 0;
        for (Entry<K, V> entry = HEAD.after; entry != TAIL; entry = entry.after)
            keys[index++] = entry.key;
        return keys;
    }
    public V[] values() {
        if (size == 0)
            return null;
        @SuppressWarnings("unchecked")
        V[] values = (V[]) Array.newInstance(HEAD.after.value.getClass(), size);
        int index = 0;
        for (Entry<K, V> entry = HEAD.after; entry != TAIL; entry = entry.after)
            values[index++] = entry.value;
        return values;
    }
    public Entry<K, V>[] entries() {
        if (size == 0)
            return null;
        @SuppressWarnings({"rawtypes", "unchecked"})
        Entry<K, V>[] entries = (Entry<K, V>[]) new Entry[size];
        int index = 0;
        for (Entry<K, V> entry = HEAD.after; entry != TAIL; entry = entry.after)
            entries[index++] = entry;
        return entries;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "{}";
        StringBuilder result = new StringBuilder("{");
        for (Entry<K, V> entry = HEAD.after; entry != TAIL; entry = entry.after) {
            result.append(entry.key + " = " + entry.value);
            if (entry.after != TAIL)
                result.append(", ");
        }
        return result.append("}").toString();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new IteratorImpl();
    }
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (Entry<K, V> entry = HEAD.after; entry != TAIL; entry = entry.after)
            action.accept(entry.key, entry.value);
    }
    class IteratorImpl implements Iterator<Map.Entry<K, V>> {
        Entry<K, V> entry = HEAD.after;

        @Override
        public boolean hasNext() {
            return entry != TAIL;
        }

        @Override
        public Entry<K, V> next() {
            return (entry = entry.after).before;
        }

        @Override
        public void remove() {
            delete(entry.before.key);
        }
    }
}
