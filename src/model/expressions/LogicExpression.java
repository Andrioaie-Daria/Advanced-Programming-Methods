package model.expressions;

import exceptions.InvalidOperationException;
import exceptions.InvalidTypeException;
import model.ADTs.DictionaryInterface;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpression implements Expression {
    private final Expression firstExpression, secondExpression;
    private final String operator;

    public LogicExpression(Expression first, Expression second,String operator){
        firstExpression = first;
        secondExpression = second;
        this.operator = operator;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> symbolTable, DictionaryInterface<Integer, Value> heap) throws Exception {
        Value value1,value2;
        value1= firstExpression.evaluate(symbolTable, heap);

        if(value1.getType().equals(new BoolType())) {
            value2 = secondExpression.evaluate(symbolTable, heap);
            if(value2.getType().equals(new BoolType())){
                boolean bool1,bool2;
                bool1 = ((BoolValue) value1).getValue();
                bool2 = ((BoolValue) value1).getValue();

                if(operator.equals("and"))
                    return new BoolValue(bool1&&bool2);
                if(operator.equals("or"))
                    return new BoolValue(bool1||bool2);

                throw new InvalidOperationException("The binary operation for boolean values is not defined.");
            }
            throw new InvalidTypeException("Second operand is not a boolean.");
        }
        throw new InvalidTypeException("First operand is not a boolean.");
    }

    @Override
    public String toString() {
        String representation = "";
        representation += (this.firstExpression.toString());
        representation += (" " + this.operator + " ");
        representation += (this.firstExpression.toString());
        return representation;
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type type1, type2;
        type1 = firstExpression.typeCheck(typeEnvironment);
        type2= secondExpression.typeCheck(typeEnvironment);
        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new BoolType();
            }
            else
                throw new InvalidTypeException("Second operand is not an integer!");
        }
        else
            throw new InvalidTypeException("First operand is not an integer!");
    }

    /*@Override
    public Type getType() {
        return new BoolType();
    }*/
}
