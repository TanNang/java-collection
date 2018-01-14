package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Stack<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    Stack<E> clear();

    Stack<E> push(E elem);
    E pop();
    E peek();

    boolean hasElem(E elem);
    E[] toArray();

    Iterator<E> iterator();
    void forEach(Consumer<? super E> action);
}
