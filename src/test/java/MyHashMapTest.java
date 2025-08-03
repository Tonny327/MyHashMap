import org.example.MyHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {

    private MyHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyHashMap<>();
    }

    @Test
    void testPutAndGet() {
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(3, map.get("three"));
    }

    @Test
    void testOverwriteValue() {
        map.put("key", 1);
        map.put("key", 2);

        assertEquals(2, map.get("key"));
        assertEquals(1, map.size()); // Убедимся, что размер не увеличился
    }

    @Test
    void testRemove() {
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertEquals(2, map.remove("two"));
        assertNull(map.get("two"));
        assertEquals(2, map.size());
    }

    @Test
    void testRemoveNonExistentKey() {
        assertNull(map.remove("unknown"));
    }

    @Test
    void testSize() {
        assertEquals(0, map.size());

        map.put("a", 10);
        assertEquals(1, map.size());

        map.put("b", 20);
        assertEquals(2, map.size());

        map.remove("a");
        assertEquals(1, map.size());
    }

    @Test
    void testIterator() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        Set<String> keys = new HashSet<>();
        for (String key : map) {
            keys.add(key);
        }

        assertEquals(Set.of("a", "b", "c"), keys);
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; i++) {
            map.put("key" + i, i);
        }

        for (int i = 0; i < 20; i++) {
            assertEquals(i, map.get("key" + i));
        }

        assertEquals(20, map.size());
    }
}

