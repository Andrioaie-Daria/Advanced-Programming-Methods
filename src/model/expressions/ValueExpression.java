package model.expressions;

import model.ADTs.DictionaryInterface;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value v){
        value = v;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> symbolTable, DictionaryInterface<Integer, Value> heap){
        return value;
    }
/*
    @Override
    public Type getType() {
        return value.getType();
    }*/

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        return value.getType();
    }

}
