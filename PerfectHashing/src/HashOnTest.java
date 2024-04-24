package TestPackage;

import Hashing.HashON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HashONTest {

    HashON<String> hashTable;

    public static List<Long> generateUniqueRandomIntegers(Long count, Long min, Long max) {
        if (count > (max - min + 1)) {
            throw new IllegalArgumentException("Cannot generate more unique integers than the range allows.");
        }

        Random random = new Random();
        Set<Long> uniqueIntegers = new HashSet<>();

        while (uniqueIntegers.size() < count) {
            long randomNumber = random.nextLong((max - min) + 1) + min;
            uniqueIntegers.add(randomNumber);
        }

        return new ArrayList<>(uniqueIntegers);
    }

    public static boolean checkForUniqueValues(List<Long> longList) {
        Set<Long> seenValues = new HashSet<>();

        for (Long value : longList) {
            if (!seenValues.add(value)) {
                // If we encounter a duplicate, return false
                return false;
            }
        }

        // All values are unique if we've iterated through the list without finding a duplicate
        return true;
    }

    @BeforeEach
    void setUp() {
        hashTable = new HashON<String>();
    }

    @Test
    void insert_should_insert_unique_values(){
        String[] keys = {"apple", "banana", "cherry", "date"};
        boolean inserted = false;
        for(String key : keys) {
            inserted = hashTable.insert(key);
        }
        assertTrue(inserted);
    }

    @Test
    void insert_should_not_insert_non_unique_values(){
        String[] keys = {"apple", "banana", "cherry", "date", "apple"};
        boolean inserted = true;
        for(String key : keys) {
            inserted = hashTable.insert(key);
        }
        assertFalse(inserted);
    }

    @Test
    void delete_should_work_properly() {
        String[] keys = {"apple", "banana", "cherry", "date", "elderberry"};
        boolean deleted;
        for (String key : keys) {
            hashTable.insert(key);
        }
        deleted = hashTable.delete("banana");
        assertTrue(deleted);
        deleted = hashTable.delete("banana");
        assertFalse(deleted);
        deleted = hashTable.delete("apple");
        assertTrue(deleted);
        deleted = hashTable.delete("grape");
        assertFalse(deleted);
    }

    @Test
    void search_should_work_properly_with_the_given_values() {
        String[] keys = {"apple", "banana", "cherry", "date", "elderberry"};
        boolean search;
        for (String key : keys) {
            hashTable.insert(key);
        }
        search = hashTable.search("banana");
        assertTrue(search);
        search = hashTable.search("apple");
        assertTrue(search);
        search = hashTable.search("grape");
        assertFalse(search);
    }

    @Test
    void hashing_should_work_with_minimum_collision(){
        Long size = 1000000L;
        List<Long> uniqueRandomIntegers = generateUniqueRandomIntegers(size, -1000000L, 100000L);
        boolean insert = false, search = false, delete = false;
        for(int i = 0; i < uniqueRandomIntegers.size(); i++){
            insert = hashTable.insert(uniqueRandomIntegers.get(i).toString());
        }
        assertEquals(size, hashTable.size);
        for(int i = 0; i < uniqueRandomIntegers.size(); i++){
            search = hashTable.search(uniqueRandomIntegers.get(i).toString());
        }
        for(int i = 0; i < uniqueRandomIntegers.size(); i++) {
            delete = hashTable.delete(uniqueRandomIntegers.get(i).toString());
        }
        assertTrue(insert);
        assertTrue(search);
        assertTrue(delete);
        assertEquals(0, hashTable.size);
    }
}
