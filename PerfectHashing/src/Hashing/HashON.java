package Hashing;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HashON<T> implements Hashing {


    private int capacity;
    public int size;
//    private int rebuild;
    private int hashCount;
    private List<T>[] firstLevelTable;
    private List<T>[] secondLevelTable;
//    private HashProvider hashProvider = new HashProvider();
//    private UniversalHashing universalHashing = new UniversalHashing();
    private List<HashFunction> secondLevelHashFunctions;

    public HashON(int capacity) {
        this.capacity = capacity;
        this.size = 0;
//        this.rebuild = 0;
        this.hashCount = 0;
        this.firstLevelTable = new List[capacity];
        this.secondLevelTable = new List[capacity];
        this.secondLevelHashFunctions = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            this.firstLevelTable[i] = new ArrayList<>();
            this.secondLevelTable[i] = new ArrayList<>();
            this.secondLevelHashFunctions.add(new HashFunction());
        }
    }

    @Override
    public boolean insert(Object key) {
        int index = firstLevelHash(key);
        this.hashCount++;
        if (firstLevelTable[index].contains(key)) {
            return false;
        }
        firstLevelTable[index].add((T) key);
        secondLevelTable[index].add((T) key);
        this.size++;
        if(this.size >= capacity) {
            rehashSecondLevel(index);
        }
        return true;

    }

    @Override
    public boolean delete(Object key) {
        int index = firstLevelHash(key);
        this.hashCount++;
        if (firstLevelTable[index].contains(key)) {
            firstLevelTable[index].remove(key);
            this.size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean search(Object key) {
        int index = firstLevelHash(key);
        this.hashCount++;
        return containsKeyInFirstLevel(index, (T) key);
    }
    private boolean containsKeyInFirstLevel(int index, T key) {
        for (T element : firstLevelTable[index]) {
            if (element.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private int firstLevelHash(Object key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    private void rehashSecondLevel(int index) {
        int newSize = secondLevelTable[index].size() * secondLevelTable[index].size();
        List<T> newTable = new ArrayList<>(Collections.nCopies(newSize, null));

        for (T key : secondLevelTable[index]) {
            if (key == null) {
                continue;
            }
            int newIndex = getSecondLevelHash(index, key, newSize);

            if (newTable.get(newIndex) != null) {
                secondLevelHashFunctions.set(index, new HashFunction());
                rehashSecondLevel(index);
                return;
            }

            newTable.set(newIndex, key);
        }

        // Replace the second-level table for the index with newTable
        secondLevelTable[index] = newTable;
    }

    private int getSecondLevelHash(int index, T key, int newSize) {
        HashFunction hashFunction = secondLevelHashFunctions.get(index % secondLevelHashFunctions.size());
        return Math.abs(hashFunction.hash(key)) % newSize;
    }
    @Override
    public BatchSuceessFailure batchInsert(String[] keys) {
        return null;
    }

    @Override
    public BatchSuceessFailure batchDelete(String[] keys) {
        return null;
    }


}

class HashFunction {
    public int hash(Object key) {
        // Implement your hash function here
        return Math.abs(key.hashCode());
    }
}

