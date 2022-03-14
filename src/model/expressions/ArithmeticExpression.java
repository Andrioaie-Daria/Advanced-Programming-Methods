package model.expressions;

import exceptions.DivisionByZeroException;
import exceptions.InvalidOperationException;
import exceptions.InvalidTypeException;
import model.ADTs.DictionaryInterface;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression {
    private final Expression firstExpression;
    private final Expression secondExpression;
    private final String operator;

    public ArithmeticExpression(String operator, Expression firstExp, Expression secondExp) {
        this.firstExpression = firstExp;
        this.secondExpression = secondExp;
        this.operator = operator;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> symbolTable, DictionaryInterface<Integer, Value> heap) throws Exception {
        Value firstValue, secondValue;

        firstValue = this.firstExpression.evaluate(symbolTable, heap);
        secondValue = this.secondExpression.evaluate(symbolTable, heap);
        if(firstValue.getType().equals(new IntType())){
            int firstInt = ((IntValue)firstValue).getValue();

            if(secondValue.getType().equals(new IntType())){

                int secondInt = ((IntValue)secondValue).getValue();

                if (this.operator.equals("+")) {
                    return new IntValue(firstInt + secondInt);
                }

                else if (this.operator.equals("-")) {
                    return new IntValue(firstInt - secondInt);
                }

                else if (this.operator.equals("*")) {
                    return new IntValue(firstInt * secondInt);
                }

                else if (this.operator.equals("/")) {
                    if (secondInt == 0) {
                        throw new DivisionByZeroException("ArithmeticExpression: Division by zero");
                    }
                    return new IntValue(firstInt / secondInt);
                }
                else { // If I check the correctness of the operand before this (e.g. in the controller/repo), I could just have case 3 as else
                    throw new InvalidOperationException("ArithmeticExpression: Invalid operator");
                }
            }
            else{
                throw new InvalidTypeException("Second operand is not an integer.");
            }
        }
        else{
            throw new InvalidTypeException("First operand is not an integer.");
        }

    }

    /*@Override
    public Type getType() {
        return new IntType();
    }
*/
    @Override
    public String toString() {
        String representation = "";
        representation += (this.firstExpression.toString());
        representation += (" " + this.operator + " ");
        representation += (this.secondExpression.toString());
        return representation;
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type type1, type2;
        type1 = firstExpression.typeCheck(typeEnvironment);
        type2= secondExpression.typeCheck(typeEnvironment);
        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new IntType();
            }
            else
                throw new InvalidTypeException("Second operand is not an integer!");
        }
        else
            throw new InvalidTypeException("First operand is not an integer!");
    }
}
