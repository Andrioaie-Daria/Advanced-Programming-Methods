package model.values;
import model.types.IntType;
import model.types.Type;

public class IntValue implements Value{
    int value;
    public static final int DEFAULT_INT_VALUE = 0;

    public IntValue() {
        this.value = IntValue.DEFAULT_INT_VALUE;
    }

    public IntValue(int v){
        value=v;
    }

    public int getValue(){
        return value;
    }

    public String toString(){
        String representation = "";
        representation += String.valueOf(this.value);
        return representation;
    }
    public Type getType(){
        return new IntType();
    }

    @Override
    public boolean equals(Object another){
        return (another instanceof IntValue && ((IntValue)another).getValue() == this.value);
    }

}
