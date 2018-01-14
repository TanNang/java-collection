package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Queue<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    Queue<E> clear();

    Queue<E> enqueue(E elem);
    E dequeue();
    E peek();

    boolean hasElem(E elem);
    E[] toArray();

    Iterator<E> iterator();
    void forEach(Consumer<? super E> action);
}
