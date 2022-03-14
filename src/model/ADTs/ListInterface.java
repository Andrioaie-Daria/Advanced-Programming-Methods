package model.ADTs;
import exceptions.EmptyCollectionException;
import exceptions.InvalidPositionException;

import java.util.ArrayList;

public interface ListInterface<TElem> {
    void addLast(TElem newElem);
    boolean remove(TElem elem);
    TElem pop() throws EmptyCollectionException;
    TElem get(int index) throws InvalidPositionException;
    int size();
    String toString();
    void clear();
    boolean isEmpty();
    TElem getLast() throws EmptyCollectionException;
    ArrayList<TElem> getList();
}
