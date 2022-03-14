package model.ADTs;

import java.util.Collection;
import java.util.HashMap;

public class MyHeap<TKey, TValue> extends MyDictionary<TKey, TValue>{
    private int firstAvailablePosition = 1;

    public MyHeap() {
        super();
    }

    private int setNextAvailablePosition() {
        return this.firstAvailablePosition + 1;
    }

    public int getFirstAvailablePosition() {
        int positionCopy = this.firstAvailablePosition;
        this.firstAvailablePosition = setNextAvailablePosition();
        return positionCopy;
    }
}
