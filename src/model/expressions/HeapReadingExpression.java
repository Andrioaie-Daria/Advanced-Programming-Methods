package model.expressions;

import exceptions.InvalidTypeException;
import exceptions.UndeclaredVariableException;
import model.ADTs.DictionaryInterface;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class HeapReadingExpression implements Expression{
    Expression expression;

    public HeapReadingExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> symbolTable, DictionaryInterface<Integer, Value> heap) throws Exception {
        Value expression_value = expression.evaluate(symbolTable, heap);
        if(! (expression_value instanceof ReferenceValue))
            throw new InvalidTypeException("The expression is not of type Reference");

        int heap_address = ((ReferenceValue)expression_value).getHeapAddress();

        if(!heap.isDefined(heap_address))
            throw new UndeclaredVariableException("HeapReadingExpression: Undefined variable at address 0x" + Integer.toHexString(heap_address));


        return heap.getValue(heap_address);
    }

    /*@Override
    public Type getType() {
        return new ReferenceType(new IntType());
    }*/

    @Override
    public String toString() {
        String representation = "";
        representation += ("*(" + this.expression.toString() + ")");
        return representation;
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type type = expression.typeCheck(typeEnvironment);
        if(type instanceof ReferenceType){
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInnerType();
        }
        else throw new InvalidTypeException("The argument of the heap reading expression is not of type reference!");
    }
}
