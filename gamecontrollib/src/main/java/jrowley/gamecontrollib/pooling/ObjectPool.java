package jrowley.gamecontrollib.pooling;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrowley on 11/2/15.
 */
public class ObjectPool<T> {
    public interface PoolObjectFactory<T> {
        public T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public ObjectPool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<>(maxSize);
    }

    public T newObject() {
        T object = null;

        if(freeObjects.isEmpty()) {
            object = factory.createObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }

        return object;
    }

    public void free(T object) {
        if(freeObjects.size() < maxSize) {
            freeObjects.add(object);
        }
    }
}
