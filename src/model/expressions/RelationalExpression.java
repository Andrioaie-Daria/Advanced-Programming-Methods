package model.expressions;

import exceptions.InvalidOperationException;
import exceptions.InvalidTypeException;
import model.ADTs.DictionaryInterface;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

//import javax.swing.text.StyledEditorKit;

public class RelationalExpression implements Expression{
    private final Expression expression1;
    private final Expression expression2;
    private final String operator;

    public RelationalExpression(Expression firstExpression, Expression secondExpression, String operator){
        expression1 = firstExpression;
        expression2 = secondExpression;
        this.operator = operator;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> symbolTable, DictionaryInterface<Integer, Value> heap) throws Exception {
        Value value1,value2;
        value1= expression1.evaluate(symbolTable, heap);

        if(value1.getType().equals(new IntType())) {
            value2 = expression2.evaluate(symbolTable, heap);
            if(value2.getType().equals(new IntType())){
                int integer1, integer2;
                integer1 = ((IntValue) value1).getValue();
                integer2 = ((IntValue) value2).getValue();

                if(operator.equals("<"))
                    return new BoolValue(integer1 < integer2);
                if(operator.equals("<="))
                    return new BoolValue(integer1 <= integer2);

                if(operator.equals("=="))
                    return new BoolValue(integer1 == integer2);

                if(operator.equals("!="))
                    return new BoolValue(integer1 != integer2);

                if(operator.equals(">"))
                    return new BoolValue(integer1 > integer2);

                if(operator.equals(">="))
                    return new BoolValue(integer1 >= integer2);

                throw new InvalidOperationException("The binary operation for boolean values is not defined.");
            }
            throw new InvalidTypeException("Second operand is not an integer.");
        }
        throw new InvalidTypeException("First operand is not an integer.");
    }

    @Override
    public String toString() {
        String representation = "";
        representation += (this.expression1.toString());
        representation += (" " + this.operator + " ");
        representation += (this.expression2.toString());
        return representation;
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2= expression2.typeCheck(typeEnvironment);
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
