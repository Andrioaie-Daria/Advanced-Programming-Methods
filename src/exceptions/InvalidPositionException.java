package exceptions;

public class InvalidPositionException extends Exception{
    public InvalidPositionException(){
        super("The position is invalid!");
    }
    public InvalidPositionException(String message){
        super(message);
    }
}
