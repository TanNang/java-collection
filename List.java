package com.zfl9.collection;

import java.util.Iterator;
import java.util.function.Consumer;

public interface List<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    List<E> clear();

    E get(int index);
    E set(int index, E newElem);

    List<E> append(E elem);
    E pop();

    List<E> insert(int index, E elem);
    E delete(int index);

    boolean deleteFirst(E elem);
    boolean deleteLast(E elem);
    boolean deleteAll(E elem);

    int firstIndexOf(E elem);
    int lastIndexOf(E elem);
    boolean hasElem(E elem);

    E[] toArray();

    Iterator<E> iterator();
    void forEach(Consumer<? super E> action);
}
