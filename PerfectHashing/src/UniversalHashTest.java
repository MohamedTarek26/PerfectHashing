package TestPackage;

import Hashing.UniversalHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniversalHashTest {
    private UniversalHash hash;

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
    public void setUp(){
        hash = new UniversalHash(100000000000000L);
        // Setting a known value for the hashMatrix
        /*for(int i = 0; i < hash.hashMatrix.length; i++){
            for(int j = 0; j < hash.hashMatrix[0].length; j++){
                if(j%2 == 0) {
                    hash.hashMatrix[i][j] = true;
                }else{
                    hash.hashMatrix[i][j] = false;
                }
            }
        }*/
    }

    @Test
    void generateKey_should_return_the_same_key_for_the_same_object(){
        long l1, l2;
        l1 = hash.generateKey(1);
        l2 = hash.generateKey(1);
        assertTrue(l1 == l2);
        l1 = hash.generateKey("test");
        l2 = hash.generateKey("test");
        assertTrue(l1 == l2);
        l1 = hash.generateKey(1);
        l2 = hash.generateKey("1");
        assertTrue(l1 == l2);
    }

    @Test
    void hash_should_return_the_same_hash_for_the_same_key(){
        long l1, l2;
        Object test = 1;
        l1 = hash.generateKey(test);
        l2 = hash.generateKey(test);
        assertTrue(hash.hash(l2) == hash.hash(l1));
    }

    @Test
    void generateKey_should_generate_unique_keys_for_large_sets(){
        List<Long> uniqueRandomIntegers = generateUniqueRandomIntegers(1000000L, -1000000L, 100000L);
        List <Long> keys = new ArrayList<>();
        for(int i = 0; i < uniqueRandomIntegers.size(); i++){
            Long key = hash.generateKey(uniqueRandomIntegers.get(i));
            keys.add(key);
        }
        assertTrue(checkForUniqueValues(keys));
    }

    @Test
    void hash_should_work_on_large_sets(){
        List<Long> uniqueRandomIntegers = generateUniqueRandomIntegers(1000000L, -1000000L, 100000L);
        List <Long> hashes = new ArrayList<>();
        for(int i = 0; i < uniqueRandomIntegers.size(); i++){
            Long key = hash.generateKey(uniqueRandomIntegers.get(i));
            hashes.add(hash.hash(key));
        }
        assertTrue(checkForUniqueValues(hashes));
    }
}
