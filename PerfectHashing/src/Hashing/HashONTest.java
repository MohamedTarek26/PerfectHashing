package Hashing;

import java.util.Arrays;

public class HashONTest {

    public static void main(String[] args) {
        testInsertion();
        testDeletion();
        testSearch();
        testCollision();
    }

    private static void testInsertion() {
        HashON<String> hashTable = new HashON<String>(10);

        System.out.println("Insertion Test:");
        System.out.println("---------------");

        String[] keys = {"apple", "banana", "cherry", "date", "elderberry"};

        for (String key : keys) {
            boolean inserted = hashTable.insert(key);
            System.out.println("Inserting " + key + ": " + (inserted ? "Success" : "Failed"));
        }

        System.out.println("Size after insertion: " + hashTable.size);
        System.out.println();
    }

    private static void testDeletion() {
        HashON<String> hashTable = new HashON<String>(10);

        System.out.println("Deletion Test:");
        System.out.println("--------------");

        String[] keys = {"apple", "banana", "cherry", "date", "elderberry"};

        for (String key : keys) {
            hashTable.insert(key);
        }

        System.out.println("Size before deletion: " + hashTable.size);

        boolean deleted = hashTable.delete("banana");
        System.out.println("Deleting 'banana': " + (deleted ? "Success" : "Failed"));

        deleted = hashTable.delete("apple");
        System.out.println("Deleting 'apple': " + (deleted ? "Success" : "Failed"));

        System.out.println("Size after deletion: " + hashTable.size);
        System.out.println();
    }

    private static void testSearch() {
        HashON<String> hashTable = new HashON<String>(10);

        System.out.println("Search Test:");
        System.out.println("------------");

        String[] keys = {"apple", "banana", "cherry", "date", "elderberry"};

        for (String key : keys) {
            hashTable.insert(key);
        }

        System.out.println("Searching for 'banana': " + hashTable.search("banana"));
        System.out.println("Searching for 'apple': " + hashTable.search("apple"));
        System.out.println("Searching for 'grape': " + hashTable.search("grape"));
    }

    private static void testCollision() {
        HashON<String> hashTable = new HashON<String>(5); // Small capacity to force collisions

        System.out.println("Collision Test:");
        System.out.println("---------------");

        String[] keys = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape"};

        for (String key : keys) {
            boolean inserted = hashTable.insert(key);
            System.out.println("Inserting " + key + ": " + (inserted ? "Success" : "Failed"));
        }

        System.out.println("Size after insertion: " + hashTable.size);
        System.out.println();
    }
}
