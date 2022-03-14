package exceptions;

public class EmptyCollectionException extends Exception{
    public EmptyCollectionException(){
        super("The collection is empty!");
    }
    public EmptyCollectionException(String message){
        super(message);
    }
}
