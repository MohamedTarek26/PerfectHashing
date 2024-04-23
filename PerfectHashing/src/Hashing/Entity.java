package Hashing;

public class Entity<T> {
    public long key;
    public T value;
    public Entity(){

    }
    public Entity(long key,T value)
    {
        this.key=key;
        this.value=value;
    }
}