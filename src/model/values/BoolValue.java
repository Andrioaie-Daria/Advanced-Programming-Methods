package model.values;
import model.types.Type;
import model.types.BoolType;

public class BoolValue implements Value{
    boolean value;
    public static final boolean DEFAULT_BOOL_VALUE = false;

    public BoolValue() {
        this.value = BoolValue.DEFAULT_BOOL_VALUE;
    }

    public BoolValue(boolean v){
        value = v;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }


    @Override
    public String toString() {
        String representation = "";
        representation += String.valueOf(this.value);
        return representation;
    }

    @Override
    public boolean equals(Object another){
        return (another instanceof BoolValue && ((BoolValue)another).getValue() == this.value);
    }
}
