package com.zfl9.collection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.BiConsumer;

public class HashMap<K, V> implements Map<K, V> {
    static class Entry<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Entry<K, V> next;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    public HashMap(int initCapacity, float loadFactor) {
        table = (Entry<K, V>[]) new Entry[initCapacity];
        this.loadFactor = loadFactor;
    }
    public HashMap(int initCapacity) {
        this(initCapacity, DEFAULT_LOAD_FACTOR);
    }
    public HashMap() {
        this(DEFAULT_INIT_CAPACITY, DEFAULT_LOAD_FACTOR);
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
    public boolean isEmpty() {
        return size == 0;
    }
    public HashMap<K, V> clear() {
        for (int i = 0; i < table.length; i++)
            table[i] = null;
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

    public HashMap<K, V> ensureCapacity(int minCapacity) {
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
                return oldValue;
            }
        }
        autoExpansion(1);
        table[index] = new Entry<K, V>(hash, key, value, table[index]);
        size++;
        return null;
    }
    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (hash == entry.hash && (key == null ? key == entry.key : key.equals(entry.key)))
                return entry.value;
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
            V value = table[index].value;
            table[index] = table[index].next;
            size--;
            return value;
        } else {
            for (Entry<K, V> entry = table[index]; entry.next != null; entry = entry.next) {
                if (hash == entry.next.hash &&
                    (key == null ? key == entry.next.key : key.equals(entry.next.key))) {
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
        Entry<K, V> entry;
        for (int i = 0; i < table.length; i++) {
            for (entry = table[i]; entry != null; entry = entry.next) {
                if (value == null ? value == entry.value : value.equals(entry.value))
                    return true;
            }
        }
        return false;
    }

    public K[] keys() {
        if (size == 0)
            return null;
        Entry<K, V> entry = null;
        outer: for (int i = 0; i < table.length; i++) {
            for (entry = table[i]; entry != null; entry = entry.next)
                if (entry.key != null) break outer;
        }
        @SuppressWarnings("unchecked")
        K[] keys = (K[]) Array.newInstance(entry.key.getClass(), size);
        int index = 0;
        for (int i = 0; i < table.length; i++) {
            for (entry = table[i]; entry != null; entry = entry.next)
                keys[index++] = entry.key;
        }
        return keys;
    }
    public V[] values() {
        if (size == 0)
            return null;
        Entry<K, V> entry = null;
        outer: for (int i = 0; i < table.length; i++) {
            for (entry = table[i]; entry != null; entry = entry.next)
                if (entry.value != null) break outer;
        }
        @SuppressWarnings("unchecked")
        V[] values = (V[]) Array.newInstance(entry.value.getClass(), size);
        int index = 0;
        for (int i = 0; i < table.length; i++) {
            for (entry = table[i]; entry != null; entry = entry.next)
                values[index++] = entry.value;
        }
        return values;
    }
    public Entry<K, V>[] entries() {
        if (size == 0)
            return null;
        @SuppressWarnings({"rawtypes", "unchecked"})
        Entry<K, V> entries[] = (Entry<K, V>[]) new Entry[size], entry;
        int index = 0;
        for (int i = 0; i < table.length; i++) {
            for (entry = table[i]; entry != null; entry = entry.next)
                entries[index++] = entry;
        }
        return entries;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "{}";
        StringBuilder result = new StringBuilder("{");
        Entry<K, V> entry; int cnt = 0;
        for (int i = 0; i < table.length; i++) {
            for (entry = table[i]; entry != null; entry = entry.next) {
                result.append(entry.key + " = " + entry.value);
                if (++cnt != size) result.append(", ");
            }
        }
        return result.append("}").toString();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new IteratorImpl();
    }
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Entry<K, V> entry;
        for (int i = 0; i < table.length; i++) {
            for (entry = table[i]; entry != null; entry = entry.next)
                action.accept(entry.key, entry.value);
        }
    }
    class IteratorImpl implements Iterator<Map.Entry<K, V>> {
        Entry<K, V>[] entries = entries();
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public Entry<K, V> next() {
            return entries[cursor++];
        }

        @Override
        public void remove() {
            delete(entries[cursor - 1].key);
            entries[cursor - 1] = null;
        }
    }
}
