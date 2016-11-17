package algorithms;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Dictionary<K, V> implements Map<K, V> {
	
	int initialSize = 500000;
	int numEntries = 0;
	int numCollisions = 0;
	int maximumChain = 0;
	
	private K[] keys;
	private Object[] values;	// can either contain CollisionBucket or V instances
	
	private void initializeArrays(Class keyClass, Class valueClass) {
		keys = (K[]) Array.newInstance((Class<?>)keyClass, initialSize);
		values = new Object[initialSize];
	}

	@Override
	public void clear() {
		keys = null;
		values = null;
		numEntries = 0;
		maximumChain = 0;
		numCollisions = 0;
	}


	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public V get(Object key) {
		if (key == null)
			return null;
		
		int hash = Math.abs(key.hashCode() % keys.length);
		K hashKey = keys[hash];
		if (hashKey == null) {
			// null key means we may have a collision bucket
			CollisionBucket<K, V> collisionBucket = (CollisionBucket<K, V>) values[hash];
			if (collisionBucket != null) {
				return collisionBucket.get(key);
			}
		}
		else if (key.equals(hashKey)) {
			return (V) values[hash];
		}
		return null;
	}


	@Override
	public boolean isEmpty() {
		return numEntries == 0;
	}


	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public V put(K key, V value) {
		if (key == null)
			return null;
		
		if ((keys == null) || (values == null)) {
			initializeArrays(key.getClass(), value.getClass());
		}
		
		V previousValue = this.get(key);
		int hash = Math.abs(key.hashCode() % keys.length);
		
		CollisionBucket<K, V> collisionBucket;
		K hashKey;
		
		// the entry for the hashcode will be null if
		// (a) there is no entry for that key or
		// (b) there is an entry for that key with a collision bucket
		if ((hashKey = keys[hash]) == null) {
			if ((collisionBucket = (CollisionBucket) values[hash]) == null) {
				// new entry
				keys[hash] = key;
				values[hash] = value;
				numEntries++;
			}
			// if the key is null, then the value must be a collision bucket
			else {
				if (collisionBucket.addPair(key, value) > 0) {
					numEntries++;
					if (collisionBucket.bucketSize() > 1) {
						numCollisions++;
					}
					if (collisionBucket.bucketSize() > maximumChain)
						maximumChain = collisionBucket.bucketSize();
				}
			}
		}
		// replace existing value
		else if (hashKey.equals(key)) {
			keys[hash] = key;
			values[hash] = value;
		}
		// otherwise add a new value to the dictionary
		else {
			V hashValue = (V) values[hash];
			collisionBucket = new CollisionBucket<K, V>();
			keys[hash] = null;
			values[hash] = collisionBucket;
			collisionBucket.addPair(key, value);
			numEntries++;
			int numAdded = collisionBucket.addPair(hashKey, hashValue);
			if (numAdded > 0) {
				if (collisionBucket.bucketSize() > 1) {
					numCollisions++;
				}
				if (collisionBucket.bucketSize() > maximumChain)
					maximumChain = collisionBucket.bucketSize();
			}
		}
		
		return previousValue;
	}


	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}
}
