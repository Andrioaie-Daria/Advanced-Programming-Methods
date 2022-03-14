package exceptions;

public class InvalidTypeException extends Exception{
    public InvalidTypeException(){
        super("The type is invalid!");
    }
    public InvalidTypeException(String message){
        super(message);
    }
}
