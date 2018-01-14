package com.zfl9.collection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.Consumer;

public class DLinkedList<E> implements List<E> {
    static class Node<E> {
        E data = null;
        Node<E> prev = null;
        Node<E> next = null;

        Node() {}
        Node(E data) { this.data = data; }
        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    final Node<E> HEAD = new Node<>();
    final Node<E> TAIL = new Node<>();
    int size = 0;

    {
        HEAD.next = TAIL;
        TAIL.prev = HEAD;
    }

    public DLinkedList() {}

    @SuppressWarnings("unchecked")
    public static <T> DLinkedList<T> of(T... elems) {
        DLinkedList<T> list = new DLinkedList<>();
        final Node<T> TAIL = list.TAIL;
        for (int i = 0; i < elems.length; i++) {
            TAIL.prev = new Node<T>(elems[i], TAIL.prev, TAIL);
            TAIL.prev.prev.next = TAIL.prev;
        }
        list.size = elems.length;
        return list;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public DLinkedList<E> clear() {
        HEAD.next = TAIL;
        TAIL.prev = HEAD;
        size = 0;
        return this;
    }

    public E get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        if (index <= size >> 1) {
            Node<E> node = HEAD.next;
            for (int i = 0; i < index; i++, node = node.next);
            return node.data;
        } else {
            Node<E> node = TAIL.prev;
            for (int i = size - 1; i > index; i--, node = node.prev);
            return node.data;
        }
    }
    public E set(int index, E newElem) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        if (index <= size >> 1) {
            Node<E> node = HEAD.next;
            for (int i = 0; i < index; i++, node = node.next);
            E oldElem = node.data;
            node.data = newElem;
            return oldElem;
        } else {
            Node<E> node = TAIL.prev;
            for (int i = size - 1; i > index; i--, node = node.prev);
            E oldElem = node.data;
            node.data = newElem;
            return oldElem;
        }
    }

    public DLinkedList<E> append(E elem) {
        TAIL.prev = new Node<E>(elem, TAIL.prev, TAIL);
        TAIL.prev.prev.next = TAIL.prev;
        size++;
        return this;
    }
    public E pop() {
        E elem = TAIL.prev.data;
        TAIL.prev = TAIL.prev.prev;
        TAIL.prev.next = TAIL;
        size--;
        return elem;
    }

    public DLinkedList<E> insert(int index, E elem) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        if (index <= size >> 1) {
            Node<E> node = HEAD;
            for (int i = 0; i < index; i++, node = node.next);
            node.next = new Node<E>(elem, node, node.next);
            node.next.next.prev = node.next;
            size++;
            return this;
        } else {
            Node<E> node = TAIL;
            for (int i = size; i > index; i--, node = node.prev);
            node.prev = new Node<E>(elem, node.prev, node);
            node.prev.prev.next = node.prev;
            size++;
            return this;
        }
    }
    public E delete(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        if (index <= size >> 1) {
            Node<E> node = HEAD;
            for (int i = 0; i < index; i++, node = node.next);
            E elem = node.next.data;
            node.next = node.next.next;
            node.next.prev = node;
            size--;
            return elem;
        } else {
            Node<E> node = TAIL;
            for (int i = size - 1; i > index; i--, node = node.prev);
            E elem = node.prev.data;
            node.prev = node.prev.prev;
            node.prev.next = node;
            size--;
            return elem;
        }
    }

    public boolean deleteFirst(E elem) {
        if (elem == null) {
            for (Node<E> node = HEAD; node.next != TAIL; node = node.next) {
                if (elem == node.next.data) {
                    node.next = node.next.next;
                    node.next.prev = node;
                    size--;
                    return true;
                }
            }
        } else {
            for (Node<E> node = HEAD; node.next != TAIL; node = node.next) {
                if (elem.equals(node.next.data)) {
                    node.next = node.next.next;
                    node.next.prev = node;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean deleteLast(E elem) {
        if (elem == null) {
            for (Node<E> node = TAIL; node.prev != HEAD; node = node.prev) {
                if (elem == node.prev.data) {
                    node.prev = node.prev.prev;
                    node.prev.next = node;
                    size--;
                    return true;
                }
            }
        } else {
            for (Node<E> node = TAIL; node.prev != HEAD; node = node.prev) {
                if (elem.equals(node.prev.data)) {
                    node.prev = node.prev.prev;
                    node.prev.next = node;
                    size--;
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
            Node<E> node = HEAD.next;
            for (int i = 0; i < size; i++, node = node.next)
                if (elem == node.data) return i;
        } else {
            Node<E> node = HEAD.next;
            for (int i = 0; i < size; i++, node = node.next)
                if (elem.equals(node.data)) return i;
        }
        return -1;
    }
    public int lastIndexOf(E elem) {
        if (elem == null) {
            Node<E> node = TAIL.prev;
            for (int i = size - 1; i >= 0; i--, node = node.prev)
                if (elem == node.data) return i;
        } else {
            Node<E> node = TAIL.prev;
            for (int i = size - 1; i >= 0; i--, node = node.prev)
                if (elem.equals(node.data)) return i;
        }
        return -1;
    }
    public boolean hasElem(E elem) {
        return firstIndexOf(elem) != -1;
    }

    public E[] toArray() {
        if (size == 0)
            return null;
        @SuppressWarnings("unchecked")
        E[] array = (E[]) Array.newInstance(HEAD.next.data.getClass(), size);
        Node<E> node = HEAD.next;
        for (int i = 0; i < size; i++, node = node.next)
            array[i] = node.data;
        return array;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";
        StringBuilder result = new StringBuilder("[");
        for (Node<E> node = HEAD.next; node != TAIL; node = node.next) {
            result.append(node.data);
            if (node.next != TAIL)
                result.append(", ");
        }
        return result.append("]").toString();
    }
    public String toStringReverse() {
        if (size == 0)
            return "[]";
        StringBuilder result = new StringBuilder("[");
        for (Node<E> node = TAIL.prev; node != HEAD; node = node.prev) {
            result.append(node.data);
            if (node.prev != HEAD)
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
        for (Node<E> node = HEAD.next; node != TAIL; node = node.next)
            action.accept(node.data);
    }
    private class IteratorImpl implements Iterator<E> {
        Node<E> node = HEAD.next;

        @Override
        public boolean hasNext() {
            return node != TAIL;
        }

        @Override
        public E next() {
            return (node = node.next).prev.data;
        }

        @Override
        public void remove() {
            node.prev = node.prev.prev;
            node.prev.next = node;
            size--;
        }
    }
}
