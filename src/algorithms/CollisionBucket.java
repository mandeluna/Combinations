package algorithms;

import java.lang.reflect.Array;

public class CollisionBucket<K, V> {
	int initialSize = 16;
	int numElements = 0;
	
	private K[] keys;
	private V[] values;
	
	/**
	 * Lazy initialize arrays because we don't know the types of entries until
	 * something is added
	 * 
	 * @param keyClass
	 * @param valueClass
	 * @param newSize
	 */
	private void reinitializeArrays(Class keyClass, Class valueClass, int newSize) {
		K[] newKeys = (K[]) Array.newInstance((Class<?>)keyClass, newSize);
		V[] newValues = (V[]) Array.newInstance((Class<?>)valueClass, newSize);
		if (keys != null) {
			for (int i=0; i < keys.length; i++) {
				newKeys[i] = keys[i];
				newValues[i] = values[i];
			}
		}
		keys = newKeys;
		values = newValues;
	}
	
	/**
	 * For testing and statistics
	 * 
	 * @return an int indicating the number of values stored in this collision bucket
	 */
	int bucketSize() {
		return numElements;
	}

	/**
	 * Stores the key/value pair in the receiver.
	 * 
	 * @param key
	 * @param value
	 * @return 1 if this operation added a new key, 0 if it replaced the value of an existing key 
	 */
	public int addPair(K key, V value) {
		if (key == null)
			throw new IndexOutOfBoundsException();
		
		if ((keys == null) || (values == null)) {
			reinitializeArrays(key.getClass(), value.getClass(), initialSize);
		}
		
		Integer emptySlotIndex = null;
		int startTableSize = keys.length;
		
		for (int i=0; i < startTableSize; i++) {
			if (keys[i] == null) {
				emptySlotIndex = new Integer(i);
				break;
			}
			if (keys[i].equals(key)) {
				// replace an entry
				values[i] = value;
				return 0;
			}
		}
		if (emptySlotIndex == null) {
			if ((numElements >= keys.length) || (numElements >= values.length)) {
				reinitializeArrays(key.getClass(), value.getClass(), numElements+8);
			}
			emptySlotIndex = startTableSize + 1;
		}
		keys[numElements] = key;
		values[numElements] = value;
		numElements++;
		return 1;
	}

	public V get(Object key) {
		if (key == null)
			return null;
		
		for (int i=0; i < numElements; i++) {
			if (key.equals(keys[i]))
				return values[i];
		}
		return null;
	}
}
