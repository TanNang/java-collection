package com.zfl9.collection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.Consumer;

public class LinkedHashSet<E> implements Set<E> {
    static class Node<E> {
        final int hash;
        final E data;
        Node<E> next;
        Node<E> before, after;

        Node(int hash, E data, Node<E> next, Node<E> before, Node<E> after) {
            this.hash = hash;
            this.data = data;
            this.next = next;
            this.before = before;
            this.after = after;
        }
        Node(int hash, E data, Node<E> next) {
            this(hash, data, next, null, null);
        }
    }

    Node<E>[] table;
    static final int DEFAULT_INIT_CAPACITY = 32;

    final float loadFactor;
    static final float DEFAULT_LOAD_FACTOR = 0.75F;

    int size;

    final Node<E> HEAD = new Node<>(0, null, null);
    final Node<E> TAIL = new Node<>(0, null, null);
    {
        HEAD.after = TAIL;
        TAIL.before = HEAD;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public LinkedHashSet(int initCapacity, float loadFactor) {
        table = (Node<E>[]) new Node[initCapacity];
        this.loadFactor = loadFactor;
    }
    public LinkedHashSet(int initCapacity) {
        this(initCapacity, DEFAULT_LOAD_FACTOR);
    }
    public LinkedHashSet() {
        this(DEFAULT_INIT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    @SuppressWarnings("unchecked")
    public static <T> LinkedHashSet<T> of(T... elems) {
        LinkedHashSet<T> set = new LinkedHashSet<>((int) (elems.length / DEFAULT_LOAD_FACTOR) + 1);
        for (T elem : elems)
            set.add(elem);
        return set;
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
    public LinkedHashSet<E> clear() {
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
        Node<E>[] newTable = (Node<E>[]) new Node[minCapacity];
        Node<E> node; int index;
        for (int i = 0; i < table.length; i++) {
            while (table[i] != null) {
                node = table[i];
                table[i] = node.next;
                index = indexFor(node.hash, minCapacity);
                node.next = newTable[index];
                newTable[index] = node;
            }
        }
        table = newTable;
    }
    void autoExpansion(int numOfAddElem) {
        if (size + numOfAddElem > table.length * loadFactor)
            manualExpansion(table.length * 2);
    }

    public LinkedHashSet<E> ensureCapacity(int minCapacity) {
        manualExpansion(minCapacity);
        return this;
    }

    public boolean add(E elem) {
        int hash = hash(elem);
        int index = indexFor(hash);
        for (Node<E> node = table[index]; node != null; node = node.next)
            if (hash == node.hash && (elem == null ? elem == node.data : elem.equals(node.data)))
                return false;
        autoExpansion(1);
        table[index] = new Node<E>(hash, elem, table[index], TAIL.before, TAIL);
        TAIL.before = table[index];
        TAIL.before.before.after = TAIL.before;
        size++;
        return true;
    }
    public boolean delete(E elem) {
        int hash = hash(elem);
        int index = indexFor(hash);
        if (table[index] == null)
            return false;
        if (hash == table[index].hash &&
            (elem == null ? elem == table[index].data : elem.equals(table[index].data))) {
            table[index].before.after = table[index].after;
            table[index].after.before = table[index].before;
            table[index] = table[index].next;
            size--;
            return true;
        } else {
            for (Node<E> node = table[index]; node.next != null; node = node.next) {
                if (hash == node.next.hash &&
                    (elem == null ? elem == node.next.data : elem.equals(node.next.data))) {
                    node.next.before.after = node.next.after;
                    node.next.after.before = node.next.before;
                    node.next = node.next.next;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean hasElem(E elem) {
        int hash = hash(elem);
        int index = indexFor(hash);
        for (Node<E> node = table[index]; node != null; node = node.next)
            if (hash == node.hash && (elem == null ? elem == node.data : elem.equals(node.data)))
                return true;
        return false;
    }

    public boolean addAll(Set<? extends E> set) {
        boolean modified = false;
        for (E elem : set)
            if (add(elem))
                modified = true;
        return modified;
    }
    public boolean deleteAll(Set<? extends E> set) {
        boolean modified = false;
        for (E elem : set)
            if (delete(elem))
                modified = true;
        return modified;
    }
    public boolean retainAll(Set<? super E> set) {
        boolean modified = false;
        for (E elem : toArray()) {
            if (!set.hasElem(elem)) {
                delete(elem);
                modified = true;
            }
        }
        return modified;
    }
    public boolean containsAll(Set<? extends E> set) {
        for (E elem : set)
            if (!hasElem(elem))
                return false;
        return true;
    }

    public E[] toArray() {
        if (size == 0)
            return null;
        @SuppressWarnings("unchecked")
        E[] array = (E[]) Array.newInstance(HEAD.after.data.getClass(), size);
        int index = 0;
        for (Node<E> node = HEAD.after; node != TAIL; node = node.after)
            array[index++] = node.data;
        return array;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";
        StringBuilder result = new StringBuilder("[");
        for (Node<E> node = HEAD.after; node != TAIL; node = node.after) {
            result.append(node.data);
            if (node.after != TAIL)
                result.append(", ");
        }
        return result.append("]").toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorImpl();
    }
    @Override
    public void forEach(Consumer<? super E> action) {
        for (Node<E> node = HEAD.after; node != TAIL; node = node.after)
            action.accept(node.data);
    }
    class IteratorImpl implements Iterator<E> {
        Node<E> node = HEAD.after;

        @Override
        public boolean hasNext() {
            return node != TAIL;
        }

        @Override
        public E next() {
            return (node = node.after).before.data;
        }

        @Override
        public void remove() {
            delete(node.before.data);
        }
    }
}
