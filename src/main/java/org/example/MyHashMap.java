package org.example;
import java.util.Objects;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashMap<K, V> implements Iterable<K>{
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.6f;

    private Entry<K, V>[] table;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = new Entry[DEFAULT_CAPACITY];
    }

    private static class Entry<K, V> {
        final K key;
        V value;
        boolean deleted = false;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int indexFor(Object key, int length) {
        return Math.abs(Objects.hashCode(key)) % length;
    }

    public void put(K key, V value) {
        if ((float) size / table.length > LOAD_FACTOR) {
            resize();
        }

        int index = indexFor(key, table.length);

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            Entry<K, V> entry = table[probeIndex];

            if (entry == null || entry.deleted) {
                table[probeIndex] = new Entry<>(key, value);
                size++;
                return;
            }

            if (Objects.equals(entry.key, key)) {
                entry.value = value; // просто обновляем
                return;
            }
        }

        throw new IllegalStateException("HashMap is full");
    }


    public V get(K key) {
        int index = indexFor(key, table.length);

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            Entry<K, V> entry = table[probeIndex];

            if (entry == null) return null;
            if (!entry.deleted && Objects.equals(entry.key, key)) {
                return entry.value;
            }
        }

        return null;
    }

    public V remove(K key) {
        int index = indexFor(key, table.length);

        for (int i = 0; i < table.length; i++) {
            int probeIndex = (index + i) % table.length;
            Entry<K, V> entry = table[probeIndex];

            if (entry == null) return null;
            if (!entry.deleted && Objects.equals(entry.key, key)) {
                entry.deleted = true;
                size--;
                return entry.value;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null && !entry.deleted) {
                put(entry.key, entry.value);
            }
        }
    }
    @Override
    public Iterator<K> iterator(){
        return new KeyIterator();
    }
    private class KeyIterator implements Iterator<K> {
        private int index = 0;

        public boolean hasNext() {
            while (index < table.length) {
                Entry<K, V> entry = table[index];
                if (entry != null && !entry.deleted) {
                    return true;
                }
                index++;
            }
            return false;
        }

        public K next() {
            while (index < table.length) {
                Entry<K, V> entry = table[index++];
                if (entry != null && !entry.deleted) {
                    return entry.key;
                }
            }
            throw new NoSuchElementException();
        }
    }
}
