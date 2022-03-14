package exceptions;

public class InvalidOperationException extends Exception {
    public InvalidOperationException(){
        super("The operation that you are trying to perform is invalid.");
    }
    public InvalidOperationException(String message){
        super(message);
    }
}
