package TestPackage;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import Hashing.HashON2;
import Hashing.Entity;
import Hashing.BatchSuceessFailure;
import java.util.List;

public class HashON2Test {

    @Test
    public void testInsertSingleKey() {
        HashON2<String> hashTable = new HashON2<>();
        assertTrue(hashTable.insert("key1"));
    }

    @Test
    public void testInsertDuplicateKey() {
        HashON2<String> hashTable = new HashON2<>();
        hashTable.insert("key1");
        assertFalse(hashTable.insert("key1")); // Inserting duplicate key
    }

    @Test
    public void testInsertMultipleKeys() {
        HashON2<String> hashTable = new HashON2<>();
        assertTrue(hashTable.insert("key1"));
        assertTrue(hashTable.insert("key2"));
        assertTrue(hashTable.insert("key3"));
    }

    @Test
    public void testDeleteNonExistentKey() {
        HashON2<String> hashTable = new HashON2<>();
        assertFalse(hashTable.delete("key1")); // Deleting non-existent key
    }

    @Test
    public void testDeleteExistingKey() {
        HashON2<String> hashTable = new HashON2<>();
        hashTable.insert("key1");
        assertTrue(hashTable.delete("key1"));
    }

    @Test
    public void testDeleteNonExistentKeyAfterInsert() {
        HashON2<String> hashTable = new HashON2<>();
        hashTable.insert("key1");
        assertFalse(hashTable.delete("key2")); // Deleting non-existent key
    }

    @Test
    public void testSearchNonExistentKey() {
        HashON2<String> hashTable = new HashON2<>();
        assertFalse(hashTable.search("key1")); // Searching for non-existent key
    }

    @Test
    public void testSearchExistingKey() {
        HashON2<String> hashTable = new HashON2<>();
        hashTable.insert("key1");
        assertTrue(hashTable.search("key1"));
    }

    @Test
    public void testSearchNonExistentKeyAfterInsert() {
        HashON2<String> hashTable = new HashON2<>();
        hashTable.insert("key1");
        assertFalse(hashTable.search("key2")); // Searching for non-existent key
    }

    @Test
    public void testBatchInsertEmptyList() {
        HashON2<String> hashTable = new HashON2<>();
        List<Entity<String>> entities = new ArrayList<>();
        BatchSuceessFailure result = hashTable.batchInsert(entities);
        assertEquals(0, result.getSuccess());
        assertEquals(0, result.getFailure());
    }

    @Test
    public void testBatchInsertSingleEntity() {
        HashON2<String> hashTable = new HashON2<>();
        List<Entity<String>> entities = new ArrayList<>();
        entities.add(new Entity<String>(1,"key1")); // Explicit type argument
        BatchSuceessFailure result = hashTable.batchInsert(entities);
        assertEquals(1, result.getSuccess());
        assertEquals(0, result.getFailure());
    }

    @Test
    public void testBatchInsertMultipleEntities() {
        HashON2<String> hashTable = new HashON2<>();
        List<Entity<String>> entities = new ArrayList<>();
        entities.add(new Entity<String>(1,"key1")); // Explicit type argument
        entities.add(new Entity<String>(2,"key2")); // Explicit type argument
        BatchSuceessFailure result = hashTable.batchInsert(entities);
        assertEquals(2, result.getSuccess());
        assertEquals(0, result.getFailure());
    }

    @Test
    public void testBatchDeleteEmptyList() {
        HashON2<String> hashTable = new HashON2<>();
        List<Entity<String>> entities = new ArrayList<>();
        BatchSuceessFailure result = hashTable.batchDelete(entities);
        assertEquals(0, result.getSuccess());
        assertEquals(0, result.getFailure());
    }

    @Test
    public void testBatchDeleteSingleEntity() {
        HashON2<String> hashTable = new HashON2<>();
        hashTable.insert("key1");
        List<Entity<String>> entities = new ArrayList<>();
        entities.add(new Entity<String>(2,"key1")); // Explicit type argument
        BatchSuceessFailure result = hashTable.batchDelete(entities);
        assertEquals(1, result.getSuccess());
        assertEquals(0, result.getFailure());
    }

    @Test
    public void testBatchDeleteMultipleEntities() {
        HashON2<String> hashTable = new HashON2<>();
        hashTable.insert("key1");
        hashTable.insert("key2");
        List<Entity<String>> entities = new ArrayList<>();
        entities.add(new Entity<String>(2,"key1")); // Explicit type argument
        entities.add(new Entity<String>(2,"key2")); // Explicit type argument
        BatchSuceessFailure result = hashTable.batchDelete(entities);
        assertEquals(2, result.getSuccess());
        assertEquals(0, result.getFailure());
    }
}
