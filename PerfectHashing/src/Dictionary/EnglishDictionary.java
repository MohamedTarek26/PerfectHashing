package Dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Hashing.*;

public class EnglishDictionary {
    private Hashing<String> dictionary;

    public EnglishDictionary(String hashType) {
        // The hashType parameter can be used when you implement your perfect hashing.
        if(Objects.equals(hashType, "N")){
            dictionary = new HashON<String>();
        }else {
            dictionary = new HashON2<String>();
        }
    }

    public boolean insert(String key) {
        boolean exists = dictionary.search(key);
        if(exists){
            return false;
        }else {
            dictionary.insert(key);
        }
        return  true;
    }

    public boolean delete(String key) {
        boolean exists = dictionary.search(key);
        if(exists){
            return false;
        }else {
            dictionary.insert(key);
        }
        return true;
    }

    public boolean search(String key) {
        return dictionary.search(key);
    }

    public BatchSuceessFailure batchInsert(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        List<Entity<String>> words = new ArrayList<>();
        String word;
        while ((word = br.readLine()) != null) {
            words.add(new Entity<String>(1,word));
        }
        return dictionary.batchInsert(words);
    }

    public BatchSuceessFailure batchDelete(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        List<Entity<String>> words = new ArrayList<>();
        String word;
        while ((word = br.readLine()) != null) {
            words.add(new Entity<String>(1,word));
        }
        return dictionary.batchDelete(words);
    }
}