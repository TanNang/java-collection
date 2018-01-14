package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Set<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    Set<E> clear();

    boolean add(E elem);
    boolean delete(E elem);
    boolean hasElem(E elem);

    boolean addAll(Set<? extends E> set);
    boolean deleteAll(Set<? extends E> set);
    boolean retainAll(Set<? super E> set);
    boolean containsAll(Set<? extends E> set);

    E[] toArray();

    Iterator<E> iterator();
    void forEach(Consumer<? super E> action);
}
