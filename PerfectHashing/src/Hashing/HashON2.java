package Hashing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HashON2<T> implements Hashing{
    private List<Entity<T>> entities;
    private int size;
    private int rebuild;
    private int N;

    private int b;
    private int nelements;

    private UniversalHash hashProvider;

    public HashON2()
    {
        this.size = 3;
        this.rebuild = 0;
        //calculate number of bits required to represent size*size
        this.calculateN(size);
        this.entities=new ArrayList<>(Collections.nCopies(this.N,null));
        this.nelements = 0;
        this.hashProvider = new UniversalHash(this.N);
    }

    private void calculateN(int size)
    {
        int i = 1;
        while (i < size*size) {
            i <<= 1;
            this.b++;
        }
        this.N = i;
    }
    private void rehash()
    {
        List<Entity<T>> oldEntities = entities;
        this.size = 2*size;
        this.calculateN(size);
        this.hashProvider = new UniversalHash(this.N);
        this.entities = new ArrayList<>(Collections.nCopies(this.N,null));
        this.rebuild++;
        this.nelements = 0;
        for(Entity<T> entity:oldEntities)
        {
            if(entity!=null)
            {
                insert(entity.value);
            }
        }
    }

    public boolean insert(T value)
    {
        long key = hashProvider.generateKey(value);
        System.out.println("Key: "+key);
        int index = this.calcIndex(key);
        if(entities.get(index)==null)
        {
            entities.set(index,new Entity<T>(key,value));
            return true;
        } else if (entities.get(index).key == key) {
            System.out.println("Key already exists");
            return false;
        } else
        {
            this.rehash();
            return insert(value);
        }
    }

    public boolean delete(T value)
    {
        long key = hashProvider.generateKey(value);
        int index = this.calcIndex(key);
        if(entities.get(index)==null)
        {
            System.out.println("Key does not exist");
            return false;
        }
        else if(entities.get(index).key==key)
        {
            entities.set(index,null);
            return true;
        }
        else
        {
            System.out.println("Key does not exist");
            return false;
        }
    }

    public boolean search(T value)
    {
        long key = hashProvider.generateKey(value);
        int index = this.calcIndex(key);
        if(entities.get(index)==null)
        {
            System.out.println("Key does not exist");
            return false;
        }
        else if(entities.get(index).key==key)
        {
            return true;
        }
        else
        {
            System.out.println("Key does not exist");
            return false;
        }
    }

    public BatchSuceessFailure batchInsert(List<Entity<T>> entities)
    {
        int success = 0;
        int failure = 0;
        for(Entity<T> entity:entities)
        {
            if(insert(entity.value))
            {
                success++;
            }
            else
            {
                failure++;
            }
        }
        return new BatchSuceessFailure(success,failure);
    }

    public BatchSuceessFailure batchDelete(List<Entity<T>> entities)
    {
        int success = 0;
        int failure = 0;
        for(Entity<T> entity:entities)
        {
            if(delete(entity.value))
            {
                success++;
            }
            else
            {
                failure++;
            }
        }
        return new BatchSuceessFailure(success,failure);
    }
    private int calcIndex(long key)
    {
        return (int)hashProvider.hash(key);
    }


    public int getRebuilds() {
        return rebuild;
    }

    public int getB() {
        return b;
    }

    public int getN() {
        return N;
    }

    public int getNelements() {
        return nelements;
    }

}
