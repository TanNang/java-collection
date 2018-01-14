package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public class DLinkedQueue<E> implements Queue<E> {
    DLinkedList<E> queue;

    public DLinkedQueue() {
        queue = new DLinkedList<>();
    }
    private DLinkedQueue(DLinkedList<E> queue) {
        this.queue = queue;
    }
    @SuppressWarnings("unchecked")
    public static <T> DLinkedQueue<T> of(T... elems) {
        return new DLinkedQueue<>(DLinkedList.of(elems));
    }

    public int size() {
        return queue.size();
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    public DLinkedQueue<E> clear() {
        queue.clear();
        return this;
    }

    public DLinkedQueue<E> enqueue(E elem) {
        queue.append(elem);
        return this;
    }
    public E dequeue() {
        return queue.delete(0);
    }
    public E peek() {
        return queue.get(0);
    }

    public boolean hasElem(E elem) {
        return queue.hasElem(elem);
    }

    public E[] toArray() {
        return queue.toArray();
    }

    @Override
    public String toString() {
        return queue.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }
    @Override
    public void forEach(Consumer<? super E> action) {
        queue.forEach(action);
    }
}
