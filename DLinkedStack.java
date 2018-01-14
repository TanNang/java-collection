package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public class DLinkedStack<E> implements Stack<E> {
    DLinkedList<E> stack;

    public DLinkedStack() {
        stack = new DLinkedList<>();
    }
    private DLinkedStack(DLinkedList<E> stack) {
        this.stack = stack;
    }
    @SuppressWarnings("unchecked")
    public static <T> DLinkedStack<T> of(T... elems) {
        return new DLinkedStack<>(DLinkedList.of(elems));
    }

    public int size() {
        return stack.size();
    }
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    public DLinkedStack<E> clear() {
        stack.clear();
        return this;
    }

    public DLinkedStack<E> push(E elem) {
        stack.insert(0, elem);
        return this;
    }
    public E pop() {
        return stack.delete(0);
    }
    public E peek() {
        return stack.get(0);
    }

    public boolean hasElem(E elem) {
        return stack.hasElem(elem);
    }

    public E[] toArray() {
        return stack.toArray();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return stack.iterator();
    }
    @Override
    public void forEach(Consumer<? super E> action) {
        stack.forEach(action);
    }
}
