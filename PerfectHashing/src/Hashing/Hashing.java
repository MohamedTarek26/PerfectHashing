package Hashing;

import java.util.List;

public interface Hashing<T> {
    public boolean insert(T key);
    public boolean delete(T key);
    public boolean search(T key);
    public BatchSuceessFailure batchInsert(List<Entity<T>> entities);
    public BatchSuceessFailure batchDelete(List<Entity<T>> entities);
}
