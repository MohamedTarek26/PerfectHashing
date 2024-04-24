package Hashing;
public interface Hashing<T> {
    public boolean insert(T key);
    public boolean delete(T key);
    public boolean search(T key);
    public BatchSuceessFailure batchInsert(String[] keys);
    public BatchSuceessFailure batchDelete(String[] keys);
}
