package util;

import model.Product;

import java.util.Arrays;

public class UniversalArrayImpl<T> implements UniversalArray<T> {
    private Object[] array;

    public UniversalArrayImpl(T[] a) {
        array = a;
    }

    public UniversalArrayImpl() {
        this.array = new Object[]{};
    }

    @Override
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public void add(T element) {
        array = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = element;
    }

    @Override
    public void addAll(Object[] elements) {
        for (var element : elements) {
            add((T) element);
        }
    }

    @Override
    public void delete(int index) {
        array[index] = null;
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        array = Arrays.copyOf(array, array.length - 1);
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        int size = size();
        if (a.length < size)
            return Arrays.copyOf(array, size, (Class<? extends E[]>) a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public Product[] toArray() {
        return (Product[]) Arrays.copyOf(array, array.length, Object[].class);
    }
}