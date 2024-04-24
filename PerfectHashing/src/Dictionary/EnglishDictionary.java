package Dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class EnglishDictionary {
    private Hashtable<String, Boolean> dictionary;

    public EnglishDictionary(String hashType) {
        // The hashType parameter can be used when you implement your perfect hashing.
        dictionary = new Hashtable<>();
    }

    public void insert(String key) {
        dictionary.put(key, true);
    }

    public void delete(String key) {
        dictionary.remove(key);
    }

    public boolean search(String key) {
        return dictionary.containsKey(key);
    }

    public void batchInsert(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = br.readLine()) != null) {
                insert(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void batchDelete(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = br.readLine()) != null) {
                delete(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

