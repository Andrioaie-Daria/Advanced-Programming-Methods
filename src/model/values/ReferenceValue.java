package model.values;

import model.types.ReferenceType;
import model.types.Type;

public class ReferenceValue implements Value{
    int heap_address;
    Type referenced_type;
    public static final int DEFAULT_HEAP_ADDRESS = 0;

    public ReferenceValue(int address_, Type location_type_){
        heap_address = address_;
        referenced_type = location_type_;
    }

    public ReferenceValue(Type referencedType) {
        this.heap_address = ReferenceValue.DEFAULT_HEAP_ADDRESS;
        this.referenced_type = referencedType;
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof ReferenceValue && ((ReferenceValue)another).getHeapAddress() == this.heap_address);
    }

    @Override
    public Type getType() {
        return new ReferenceType(referenced_type);
    }

    public int getHeapAddress() {
        return this.heap_address;
    }

    public Type getReferencedType() {
        return this.referenced_type;
    }

    public String toString() {
        String representation = "";
        representation += ("(0x" + Integer.toHexString(this.heap_address) + ", " + this.referenced_type.toString() + ")");
        return representation;
    }

}
