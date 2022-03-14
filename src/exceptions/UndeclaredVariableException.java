package exceptions;

public class UndeclaredVariableException extends Exception{
    public UndeclaredVariableException(){
        super("The variable is not defined.");
    }
    public UndeclaredVariableException(String message){
        super(message);
    }
}
