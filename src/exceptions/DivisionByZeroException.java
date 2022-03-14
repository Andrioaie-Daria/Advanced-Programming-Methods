package exceptions;

public class DivisionByZeroException extends Exception{
    public DivisionByZeroException(){
        super("Division by zero was attempted");
    }
    public DivisionByZeroException(String message){
        super(message);
    }
}
