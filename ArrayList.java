package com.zfl9.collection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.RandomAccess;
import java.util.function.Consumer;

public class ArrayList<E> implements List<E>, RandomAccess {
    private Object[] array = null;
    private int size = 0;

    private static final int DEFAULT_INIT_CAPACITY = 16;

    public ArrayList() {
        array = new Object[DEFAULT_INIT_CAPACITY];
    }
    public ArrayList(int initCapacity) {
        array = new Object[initCapacity];
    }
    private ArrayList(Object[] array) {
        this.array = array;
        size = array.length;
    }
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> of(T... elems) {
        return new ArrayList<>(elems.clone());
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
    public ArrayList<E> clear() {
        for (int i = 0; i < size; i++)
            array[i] = null;
        size = 0;
        return this;
    }

    private void manualExpansion(int minCapacity) {
        if (minCapacity < size)
            return;
        Object[] newArr = new Object[minCapacity];
        for (int i = 0; i < size; i++)
            newArr[i] = array[i];
        array = newArr;
    }
    private void autoExpansion(int numOfAddElem) {
        if (size + numOfAddElem > array.length)
            manualExpansion(array.length * 2);
    }

    public ArrayList<E> ensureCapacity(int minCapacity) {
        if (minCapacity > array.length)
            manualExpansion(minCapacity);
        return this;
    }
    public ArrayList<E> trimToSize() {
        manualExpansion(size);
        return this;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) array[index];
    }
    public E set(int index, E newElem) {
        @SuppressWarnings("unchecked")
        E oldElem = (E) array[index];
        array[index] = newElem;
        return oldElem;
    }

    public ArrayList<E> append(E elem) {
        autoExpansion(1);
        array[size++] = elem;
        return this;
    }
    public E pop() {
        @SuppressWarnings("unchecked")
        E elem = (E) array[--size];
        array[size] = null;
        return elem;
    }

    public ArrayList<E> insert(int index, E elem) {
        autoExpansion(1);
        for (int i = size; i > index; i--)
            array[i] = array[i - 1];
        array[index] = elem;
        size++;
        return this;
    }
    public E delete(int index) {
        @SuppressWarnings("unchecked")
        E elem = (E) array[index];
        for (int i = index; i < size - 1; i++)
            array[i] = array[i + 1];
        array[--size] = null;
        return elem;
    }

    public boolean deleteFirst(E elem) {
        if (elem == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    delete(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elem.equals(array[i])) {
                    delete(i);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean deleteLast(E elem) {
        if (elem == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    delete(i);
                    return true;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (elem.equals(array[i])) {
                    delete(i);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean deleteAll(E elem) {
        boolean deleted = false;
        while (deleteFirst(elem)) deleted = true;
        return deleted;
    }

    public int firstIndexOf(E elem) {
        if (elem == null) {
            for (int i = 0; i < size; i++) {
                if (elem == array[i])
                    return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elem.equals(array[i]))
                    return i;
            }
        }
        return -1;
    }
    public int lastIndexOf(E elem) {
        if (elem == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elem == array[i])
                    return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (elem.equals(array[i]))
                    return i;
            }
        }
        return -1;
    }
    public boolean hasElem(E elem) {
        return firstIndexOf(elem) != -1;
    }

    @SuppressWarnings("unchecked")
    public E[] toArray() {
        if (size == 0)
            return null;
        E[] elems = (E[]) Array.newInstance(array[0].getClass(), size);
        for (int i = 0; i < size; i++)
            elems[i] = (E) array[i];
        return elems;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            result.append(array[i]);
            if (i != size - 1)
                result.append(", ");
        }
        result.append("]");
        return result.toString();
    }
    public String toStringReverse() {
        if (size == 0)
            return "[]";
        StringBuilder result = new StringBuilder("[");
        for (int i = size - 1; i >= 0; i--) {
            result.append(array[i]);
            if (i != 0)
                result.append(", ");
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorImpl();
    }
    @Override
    @SuppressWarnings("unchecked")
    public void forEach(Consumer<? super E> action) {
        for (int i = 0; i < size; i++)
            action.accept((E) array[i]);
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
            return (E) array[cursor++];
        }

        @Override
        public void remove() {
            delete(--cursor);
        }
    }
}
