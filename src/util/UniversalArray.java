package util;

import model.Product;

public interface UniversalArray<T> {
    void add(T element);
    void addAll(T[] elements);
    void delete(int index);
    int size();
    T get(int index);
    <E> E[] toArray(E[] a);
    Product[] toArray();
}