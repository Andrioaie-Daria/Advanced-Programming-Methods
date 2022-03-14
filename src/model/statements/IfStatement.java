package model.statements;

import exceptions.InvalidTypeException;
import model.ADTs.DictionaryInterface;
import model.ADTs.StackInterface;
import model.ProgramState;
import model.expressions.Expression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements Statement{

    private final Expression condition;
    private final Statement conditionTrueStatement;
    private final Statement conditionFalseStatement;

    public IfStatement(Expression newCondition, Statement newConditionTrueStatement, Statement newConditionFalseStatement){
        condition = newCondition;
        conditionTrueStatement = newConditionTrueStatement;
        conditionFalseStatement = newConditionFalseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        StackInterface<Statement> executionStack = programState.getExecutionStack();
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();

        Value resultOfCondition = condition.evaluate(symbolTable, heap);

        // if the condition is not a boolean expression
        if(!resultOfCondition.getType().equals(new BoolType())){
            throw new InvalidTypeException("The conditional expression is not boolean");
        }
        if(((BoolValue) resultOfCondition).getValue() == true){
            executionStack.push(conditionTrueStatement);
        }
        else
            executionStack.push(conditionFalseStatement);
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type typeOfCondition = condition.typeCheck(typeEnvironment);
        if(typeOfCondition.equals(new BoolType())){
            conditionTrueStatement.typeCheck(typeEnvironment.clone());
            conditionFalseStatement.typeCheck(typeEnvironment.clone());
            return typeEnvironment;
        }
        else
            throw new InvalidTypeException("If statement: The condition is not of type Bool!");
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("if (" + condition.toString() + ") then { ");
        representation += conditionTrueStatement.toString();
        representation += "else { ";
        representation += conditionFalseStatement.toString() + "}";
        return representation;
    }
}
