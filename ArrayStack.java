package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public class ArrayStack<E> implements Stack<E> {
    ArrayList<E> stack;

    public ArrayStack() {
        stack = new ArrayList<>();
    }
    public ArrayStack(int initCapacity) {
        stack = new ArrayList<>(initCapacity);
    }
    private ArrayStack(ArrayList<E> stack) {
        this.stack = stack;
    }
    @SuppressWarnings("unchecked")
    public static <T> ArrayStack<T> of(T... elems) {
        return new ArrayStack<>(ArrayList.of(elems));
    }

    public int size() {
        return stack.size();
    }
    public int capacity() {
        return stack.capacity();
    }
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    public ArrayStack<E> clear() {
        stack.clear();
        return this;
    }

    public ArrayStack<E> ensureCapacity(int minCapacity) {
        stack.ensureCapacity(minCapacity);
        return this;
    }
    public ArrayStack<E> trimToSize() {
        stack.trimToSize();
        return this;
    }

    public ArrayStack<E> push(E elem) {
        stack.append(elem);
        return this;
    }
    public E pop() {
        return stack.pop();
    }
    public E peek() {
        return stack.get(stack.size() - 1);
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
