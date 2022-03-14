package model.ADTs;
import model.types.Type;
import model.values.Value;

import java.util.Collection;
import java.util.HashMap;

public class MyDictionary<TKey, TValue> implements DictionaryInterface<TKey, TValue>{
    private HashMap<TKey, TValue> dictionary;

    public MyDictionary(){
        this.dictionary = new HashMap<TKey, TValue>();
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public boolean isDefined(TKey tKey) {
        return dictionary.containsKey(tKey);
    }

    @Override
    public boolean exists(TValue tValue) {
        return dictionary.containsValue(tValue);
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public void update(TKey tKey, TValue newValue) {
        /// Replaces the entry for the specified key only if it is currently mapped to some value.
        dictionary.replace(tKey, newValue);
    }

    @Override
    public void insert(TKey tKey, TValue newValue) {
        dictionary.put(tKey, newValue);
    }

    @Override
    public void clear() {
        dictionary.clear();
    }

    @Override
    public TValue getValue(TKey tKey) {
        // Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
        return dictionary.get(tKey);
    }

    @Override
    public TValue remove(TKey tKey) {
        /// Removes the mapping for the specified key from this map if present.
        return dictionary.remove(tKey);
    }

    @Override
    public Collection<TValue> getAllValues() {
        return dictionary.values();
    }

    @Override
    public Collection<TKey> getAllKeys() {
        return dictionary.keySet();
    }

    @Override
    public String toString() {
        String representation = "";

        for (TKey key :this.dictionary.keySet()) {
            representation += (key.toString() + " -> " + this.dictionary.get(key).toString() + "\n");
        }

        return representation;
    }

    @Override
    public void setContent(HashMap<TKey, TValue> newContent) {
        this.dictionary = newContent;

    }

    @Override
    public HashMap<TKey, TValue> getAllPairs() {
        return this.dictionary;
    }

    @Override
    public DictionaryInterface<TKey, TValue> clone() {
        DictionaryInterface<TKey, TValue> cloneDictionary = new MyDictionary<>();
        for(TKey key : dictionary.keySet()){
            cloneDictionary.insert(key, dictionary.get(key));
        }
        return cloneDictionary;
    }
}
