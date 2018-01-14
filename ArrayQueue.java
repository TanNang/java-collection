package com.zfl9.collection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.Consumer;

public class ArrayQueue<E> implements Queue<E> {
    Object[] array = null;
    int size = 0;
    int head = -1;
    int tail = -1;
    static final int DEFAULT_INIT_CAPACITY = 16;

    public ArrayQueue() {
        array = new Object[DEFAULT_INIT_CAPACITY];
    }
    public ArrayQueue(int initCapacity) {
        array = new Object[initCapacity];
    }
    private ArrayQueue(Object[] array) {
        this.array = array;
        size = array.length;
        tail = size - 1;
    }
    @SuppressWarnings("unchecked")
    public static <T> ArrayQueue<T> of(T... elems) {
        return new ArrayQueue<>(elems.clone());
    }

    public int size() {
        return size;
    }
    public int capacity() {
        return array.length;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public ArrayQueue<E> clear() {
        for (int i = 0; i < size; i++)
            array[(head + i + 1) % array.length] = null;
        size = 0;
        head = tail = -1;
        return this;
    }

    private void manualExpansion(int minCapacity) {
        if (minCapacity < size)
            return;
        Object[] newArray = new Object[minCapacity];
        for (int i = 0; i < size; i++)
            newArray[i] = array[(head + i + 1) % array.length];
        head = -1;
        tail = size - 1;
        array = newArray;
    }
    private void autoExpansion(int numOfAddElem) {
        if (size + numOfAddElem > array.length)
            manualExpansion(array.length * 2);
    }

    public ArrayQueue<E> ensureCapacity(int minCapacity) {
        if (minCapacity > array.length)
            manualExpansion(minCapacity);
        return this;
    }
    public ArrayQueue<E> trimToSize() {
        manualExpansion(size);
        return this;
    }

    public ArrayQueue<E> enqueue(E elem) {
        autoExpansion(1);
        tail = (tail + 1) % array.length;
        array[tail] = elem;
        size++;
        return this;
    }
    public E dequeue() {
        if (size == 0)
            throw new IllegalStateException("queue is empty");
        head = (head + 1) % array.length;
        @SuppressWarnings("unchecked")
        E elem = (E) array[head];
        array[head] = null;
        size--;
        return elem;
    }
    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0)
            throw new IllegalStateException("queue is empty");
        return (E) array[(head + 1) % array.length];
    }

    public boolean hasElem(E elem) {
        if (elem == null) {
            for (int i = 0; i < size; i++) {
                if (elem == array[(head + i + 1) % array.length])
                    return true;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elem.equals(array[(head + i + 1) % array.length]))
                    return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public E[] toArray() {
        if (size == 0)
            return null;
        E[] elems = (E[]) Array.newInstance(array[(head + 1) % array.length].getClass(), size);
        for (int i = 0; i < size; i++)
            elems[i] = (E) array[(head + i + 1) % array.length];
        return elems;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            result.append(array[(head + i + 1) % array.length]);
            if (i != size - 1)
                result.append(", ");
        }
        return result.append("]").toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorImpl();
    }
    @Override
    @SuppressWarnings("unchecked")
    public void forEach(Consumer<? super E> action) {
        for (int i = 0; i < size; i++)
            action.accept((E) array[(head + i + 1) % array.length]);
    }
    private class IteratorImpl implements Iterator<E> {
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            return (E) array[(head + ++cursor) % array.length];
        }
    }
}
