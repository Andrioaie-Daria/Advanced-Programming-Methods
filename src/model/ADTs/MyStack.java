package model.ADTs;
import exceptions.EmptyCollectionException;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Stack;

public class MyStack<TElem> implements StackInterface<TElem>{
    private final Stack<TElem> stack;

    public MyStack(){
        stack = new Stack<TElem>();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public void push(TElem new_element) {
        stack.push(new_element);
    }

    @Override
    public TElem pop() throws EmptyCollectionException {
        if(stack.size() == 0){
            throw new EmptyCollectionException("Stack is empty!");
        }
        return stack.pop();
    }

    @Override
    public TElem top() throws EmptyCollectionException {
        if(stack.size() == 0){
            throw new EmptyCollectionException("Stack is empty!");
        }
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public String toString(){
        StringBuilder representation = new StringBuilder();
        ArrayList<String> stackReverse = new ArrayList<>();
        for (TElem currentElement : this.stack) {
            stackReverse.add(0, currentElement.toString());
        }
        for(String elementString : stackReverse){
            representation.append(elementString).append("\n");
        }
        return representation.toString();
    }

    @Override
    public Stack<TElem> getStack() {
        Stack<TElem> stackReverse = new Stack<>();
        Stack<TElem> stackCopy = (Stack<TElem>) stack.clone();

        while(!stackCopy.isEmpty())
            stackReverse.push(stackCopy.pop());

        return stackReverse;
    }
}
