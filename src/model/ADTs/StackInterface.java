package model.ADTs;
import exceptions.EmptyCollectionException;

import java.util.Stack;

public interface StackInterface<TElem> {
    int size();
    void push(TElem new_element);
    TElem pop() throws EmptyCollectionException;
    TElem top() throws EmptyCollectionException;
    boolean isEmpty();
    void clear();
    Stack<TElem> getStack();
    String toString();
}
