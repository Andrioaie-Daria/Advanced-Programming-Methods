package model.ADTs;
import exceptions.EmptyCollectionException;
import exceptions.InvalidPositionException;

import java.util.ArrayList;

public class MyList<TElem> implements ListInterface<TElem>{

    private final ArrayList<TElem> list;

    public MyList(){
        list = new ArrayList<TElem>();
    }

    @Override
    public void addLast(TElem newElem) {
        list.add(newElem);
    }

    @Override
    public boolean remove(TElem tElem) {
        return list.remove(tElem);
    }

    @Override
    public TElem pop() throws EmptyCollectionException {
        int size = this.list.size();
        if (size == 0) {
            throw new EmptyCollectionException("The list is empty!");
        }
        return this.list.remove(size - 1);
    }

    @Override
    public TElem get(int index) throws InvalidPositionException {
        if (index < 0 || index >= this.list.size()) {
            throw new InvalidPositionException("List: position is invalid!");
        }
        return this.list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public TElem getLast() throws EmptyCollectionException {
        int size = this.list.size();
        if (size == 0) {
            throw new EmptyCollectionException("The list is empty");
        }
        return this.list.get(size - 1);
    }

    @Override
    public ArrayList<TElem> getList() {
        return (ArrayList<TElem>) list;
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        for(TElem elem : this.list) {
            representation.append(elem.toString()).append("\n");
        }
        return representation.toString();
    }
}
