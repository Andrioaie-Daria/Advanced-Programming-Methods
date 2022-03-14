package model.types;

import model.values.ReferenceValue;
import model.values.Value;

public class ReferenceType implements Type {
    Type innerType;

    public ReferenceType(Type inner){
        innerType = inner;
    }

    public Type getInnerType(){
        return innerType;
    }

    public boolean equals(Object another){
        if(another instanceof ReferenceType)
            return innerType.equals(((ReferenceType) another).getInnerType());
        return false;
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(innerType);
    }

    public String toString() {
        String representation = "";
        representation += ("Ref(" + this.innerType.toString() + ")");
        return representation;
    }
}
