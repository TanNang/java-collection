package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public class SLinkedQueue<E> implements Queue<E> {
    SLinkedList<E> queue;

    public SLinkedQueue() {
        queue = new SLinkedList<>();
    }
    private SLinkedQueue(SLinkedList<E> queue) {
        this.queue = queue;
    }
    @SuppressWarnings("unchecked")
    public static <T> SLinkedQueue<T> of(T... elems) {
        return new SLinkedQueue<>(SLinkedList.of(elems));
    }

    public int size() {
        return queue.size();
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    public SLinkedQueue<E> clear() {
        queue.clear();
        return this;
    }

    public SLinkedQueue<E> enqueue(E elem) {
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
