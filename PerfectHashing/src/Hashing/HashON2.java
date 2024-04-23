package Hashing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class HashProvider {
    private static final long P = 73856093;
    private static final long M = 1000000007;

    public long hash(long x) {
        return ((P * x) % M);
    }
}
public class HashON2<T> {
    private List<Entity<T>> entities;
    private int size;
    private int rebuild;
    private int N;

    private int b;
    private int nelements;

    private HashProvider hashProvider = new HashProvider();

    public HashON2()
    {
        this.size = 3;
        this.rebuild = 0;
        //calculate number of bits required to represent size*size
        this.calculateN(size);
        this.entities=new ArrayList<>(Collections.nCopies(this.N,null));
        this.nelements = 0;
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
    public void rehash()
    {
        List<Entity<T>> oldEntities = entities;
        this.size = 2*size;
        this.calculateN(size);
        this.entities = new ArrayList<>(Collections.nCopies(this.N,null));
        this.rebuild++;
        this.nelements = 0;
        for(Entity<T> entity:oldEntities)
        {
            if(entity!=null)
            {
                put(entity.key,entity.value);
            }
        }
    }
    public boolean put(long key,T value)
    {
        int index = (int)hashProvider.hash(key) % size;
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
            return put(key,value);
        }
    }

    public boolean remove(long key)
    {
        int index = (int)hashProvider.hash(key) % size;
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

    public Object get(long key)
    {
        int index = (int)hashProvider.hash(key) % size;
        if(entities.get(index)==null)
        {
            System.out.println("Key does not exist");
            return null;
        }
        else if(entities.get(index).key==key)
        {
            return entities.get(index).value;
        }
        else
        {
            System.out.println("Key does not exist");
            return null;
        }
    }

    public BatchSuceessFailure batchPut(List<Entity<T>> entities)
    {
        int success = 0;
        int failure = 0;
        for(Entity<T> entity:entities)
        {
            if(put(entity.key,entity.value))
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

    public BatchSuceessFailure batchRemove(List<Long> keys)
    {
        int success = 0;
        int failure = 0;
        for(Long key:keys)
        {
            if(remove(key))
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
