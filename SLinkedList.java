package com.zfl9.collection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.Consumer;

public class SLinkedList<E> implements List<E> {
    static class Node<E> {
        E data = null;
        Node<E> next = null;

        Node() {}
        Node(E data) { this.data = data; }
        Node(E data, Node<E> next) { this.data = data; this.next = next; }
    }

    final Node<E> HEAD = new Node<>();
    Node<E> tail = HEAD;
    int size = 0;

    public SLinkedList() {}

    @SuppressWarnings("unchecked")
    public static <T> SLinkedList<T> of(T... elems) {
        SLinkedList<T> list = new SLinkedList<>();
        final Node<T> HEAD = list.HEAD;
        Node<T> tail = HEAD;
        for (int i = 0; i < elems.length; i++)
            tail = tail.next = new Node<T>(elems[i]);
        list.size = elems.length;
        list.tail = tail;
        return list;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public SLinkedList<E> clear() {
        HEAD.next = null;
        size = 0;
        return this;
    }

    public E get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        Node<E> node = HEAD;
        for (int i = 0; i <= index; i++, node = node.next);
        return node.data;
    }
    public E set(int index, E newElem) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        Node<E> node = HEAD;
        for (int i = 0; i <= index; i++, node = node.next);
        E oldElem = node.data;
        node.data = newElem;
        return oldElem;
    }

    public SLinkedList<E> append(E elem) {
        tail = tail.next = new Node<E>(elem);
        size++;
        return this;
    }
    public E pop() {
        if (size == 0)
            throw new IndexOutOfBoundsException("list is empty");
        Node<E> node = HEAD;
        for (; node.next.next != null; node = node.next);
        E elem = node.next.data;
        node.next = null;
        size--;
        return elem;
    }

    public SLinkedList<E> insert(int index, E elem) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        Node<E> node = HEAD;
        for (int i = 0; i < index; i++, node = node.next);
        node.next = new Node<E>(elem, node.next);
        size++;
        return this;
    }
    public E delete(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        Node<E> node = HEAD;
        for (int i = 0; i < index; i++, node = node.next);
        E elem = node.next.data;
        node.next = node.next.next;
        size--;
        return elem;
    }

    public boolean deleteFirst(E elem) {
        if (size == 0)
            return false;
        if (elem == null) {
            for (Node<E> node = HEAD; node.next != null; node = node.next) {
                if (elem == node.next.data) {
                    node.next = node.next.next;
                    size--;
                    return true;
                }
            }
        } else {
            for (Node<E> node = HEAD; node.next != null; node = node.next) {
                if (elem.equals(node.next.data)) {
                    node.next = node.next.next;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean deleteLast(E elem) {
        int lastIndex = -1;
        Node<E> node = HEAD.next;

        if (elem == null) {
            for (int i = 0; i < size; i++, node = node.next) {
                if (elem == node.data)
                    lastIndex = i;
            }
        } else {
            for (int i = 0; i < size; i++, node = node.next) {
                if (elem.equals(node.data))
                    lastIndex = i;
            }
        }

        if (lastIndex == -1)
            return false;

        node = HEAD;
        for (int i = 0; i < lastIndex; i++, node = node.next);
        node.next = node.next.next;
        size--;
        return true;
    }
    public boolean deleteAll(E elem) {
        boolean deleted = false;
        while (deleteFirst(elem)) deleted = true;
        return deleted;
    }

    public int firstIndexOf(E elem) {
        Node<E> node = HEAD;
        if (elem == null) {
            for (int i = 0; i < size; i++, node = node.next)
                if (elem == node.next.data) return i;
        } else {
            for (int i = 0; i < size; i++, node = node.next)
                if (elem.equals(node.next.data)) return i;
        }
        return -1;
    }
    public int lastIndexOf(E elem) {
        int lastIndex = -1;
        Node<E> node = HEAD;
        if (elem == null) {
            for (int i = 0; i < size; i++, node = node.next)
                if (elem == node.next.data) lastIndex = i;
        } else {
            for (int i = 0; i < size; i++, node = node.next)
                if (elem.equals(node.next.data)) lastIndex = i;
        }
        return lastIndex;
    }
    public boolean hasElem(E elem) {
        return firstIndexOf(elem) != -1;
    }

    public E[] toArray() {
        if (size == 0)
            return null;
        @SuppressWarnings("unchecked")
        E[] array = (E[]) Array.newInstance(HEAD.next.data.getClass(), size);
        int i = 0;
        for (Node<E> node = HEAD; node.next != null; node = node.next)
            array[i++] = node.next.data;
        return array;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";
        StringBuilder result = new StringBuilder("[");
        for (Node<E> node = HEAD; node.next != null; node = node.next) {
            result.append(node.next.data);
            if (node.next.next != null)
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
        for (Node<E> node = HEAD; node.next != null; node = node.next)
            action.accept(node.next.data);
    }
    private class IteratorImpl implements Iterator<E> {
        Node<E> node = HEAD;

        @Override
        public boolean hasNext() {
            return node.next != null;
        }

        @Override
        public E next() {
            return (node = node.next).data;
        }
    }
}
