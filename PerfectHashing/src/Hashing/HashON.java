package Hashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HashON<T> implements Hashing<T> {

    public static final int CAPACITY = 5;

    private final int capacity;
    public int size;
    private int hashCount;

    private UniversalHash firstLevelHashFunction;
    private List<T>[] firstLevelTable;
    private List<T>[] secondLevelTable;
    private List<UniversalHash> secondLevelHashFunctions;

    public HashON() {
        this.capacity = CAPACITY;
        this.size = 0;
        this.hashCount = 0;
        this.firstLevelTable = new List[capacity];
        this.firstLevelHashFunction = new UniversalHash(capacity);
        this.secondLevelTable = new List[capacity];
        this.secondLevelHashFunctions = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            this.firstLevelTable[i] = new ArrayList<>();
            this.secondLevelTable[i] = new ArrayList<>();
            this.secondLevelHashFunctions.add(new UniversalHash(1));
        }
    }

    @Override
    public boolean insert(T key) {
        int index = firstLevelHash(key);
        this.hashCount++;
        if (firstLevelTable[index].contains(key)) {
            return false;
        }
        firstLevelTable[index].add(key);
        secondLevelTable[index].add(key);
        this.size++;
        if (firstLevelTable[index].size() > 1) {
            rehashSecondLevel(index);
        }
        return true;
    }

@Override
public boolean delete(T key) {
    int index = firstLevelHash(key);
    this.hashCount++;
    if (firstLevelTable[index].contains(key)) {
        firstLevelTable[index].remove(key);
        secondLevelTable[index].remove(key);
        this.size--;
        return true;
    }
    return false;
}

    @Override
    public boolean search(T key) {
        int firstLevelIndex = firstLevelHash(key);
        this.hashCount++;
        if(firstLevelTable[firstLevelIndex].isEmpty()) {
            return false;
        }
        int secondLevelIndex = getSecondLevelHash(firstLevelIndex, key, secondLevelTable[firstLevelIndex].size());
        return secondLevelTable[firstLevelIndex].get(secondLevelIndex) != null &&
               secondLevelTable[firstLevelIndex].get(secondLevelIndex).equals(key);
    }

    @Override
    public BatchSuceessFailure batchDelete(List<Entity<T>> entities){
        return null;
    }

    @Override
    public BatchSuceessFailure batchInsert(List<Entity<T>> entities){
        return null;
    }

    private int firstLevelHash(T key) {
        return (int) (firstLevelHashFunction.hash(firstLevelHashFunction.generateKey(key)) % capacity);
    }

private void rehashSecondLevel(int index) {
    int newSize = firstLevelTable[index].size() * firstLevelTable[index].size();
    List<T> newTable = new ArrayList<>(Collections.nCopies(newSize, null));

    for (T key : firstLevelTable[index]) {
        if (key == null) {
            continue;
        }
        int newIndex = getSecondLevelHash(index, key, newSize);

        if (newTable.get(newIndex) != null) {
            secondLevelHashFunctions.set(index, new UniversalHash(newSize));
            rehashSecondLevel(index);
            return;
        }

        newTable.set(newIndex, key);
    }

    secondLevelTable[index] = newTable;
}

    private int getSecondLevelHash(int index, T key, int newSize) {
        UniversalHash universalHash = secondLevelHashFunctions.get(index % secondLevelHashFunctions.size());
        long keyHash = universalHash.generateKey(key);
        return (int) (universalHash.hash(keyHash) % newSize);
//        return (int) (universalHash.hash(universalHash.generateKey(key)) % newSize);
    }

}
