package model.expressions;

import exceptions.UndeclaredVariableException;
import model.ADTs.DictionaryInterface;
import model.types.IntType;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements Expression {
    private final String variableName;

    public VariableExpression(String name){
        variableName = name;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> symbolTable, DictionaryInterface<Integer, Value> heap) throws Exception{
        if(!symbolTable.isDefined(variableName)){
            throw new UndeclaredVariableException("VariableExpression: Variable " + this.variableName + " is not defined");
        }
        return symbolTable.getValue(variableName);
    }


    /*@Override
    public Type getType() {
        return new IntType();
    }*/

    public String toString(){
        return variableName;
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        return typeEnvironment.getValue(variableName);
    }
}
