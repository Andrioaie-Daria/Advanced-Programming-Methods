package model.values;

import model.types.StringType;
import model.types.Type;

import java.util.Objects;

public class StringValue implements Value{
    String value;
    public static final String DEFAULT_STRING_VALUE = "";

    public StringValue() {
        this.value = StringValue.DEFAULT_STRING_VALUE;
    }

    public StringValue(String v){
        value = v;
    }

    public String getValue(){
        return value;
    }

    public String toString(){
        String representation = "";
        representation += this.value;
        return representation;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof StringValue && Objects.equals(((StringValue) another).getValue(), this.value));
    }

}
