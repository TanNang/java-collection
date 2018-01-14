package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public class SLinkedStack<E> implements Stack<E> {
    SLinkedList<E> stack;

    public SLinkedStack() {
        stack = new SLinkedList<>();
    }
    private SLinkedStack(SLinkedList<E> stack) {
        this.stack = stack;
    }
    @SuppressWarnings("unchecked")
    public static <T> SLinkedStack<T> of(T... elems) {
        return new SLinkedStack<>(SLinkedList.of(elems));
    }

    public int size() {
        return stack.size();
    }
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    public SLinkedStack<E> clear() {
        stack.clear();
        return this;
    }

    public SLinkedStack<E> push(E elem) {
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
