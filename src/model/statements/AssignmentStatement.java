package model.statements;

import exceptions.InvalidAssignmentException;
import exceptions.InvalidTypeException;
import exceptions.UndeclaredVariableException;
import model.ProgramState;
import model.expressions.Expression;
import model.ADTs.DictionaryInterface;
import model.types.Type;
import model.values.Value;

public class AssignmentStatement implements Statement {
    private String variableName;
    private Expression expression;

    public AssignmentStatement(String variableName, Expression newExpression){
        this.variableName = variableName;
        expression = newExpression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception{
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();
        if(symbolTable.isDefined(variableName)){
            Value newValue = expression.evaluate(symbolTable, heap);
            Type type = symbolTable.getValue(variableName).getType();
            if (newValue.getType().equals(type)){
                symbolTable.update(variableName, newValue);
            }
            else throw new InvalidAssignmentException("Type of variable and type of expression do not match.");
        }
        else throw new UndeclaredVariableException("Variable " + variableName + " is not declared.");
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type typeOfVariable = typeEnvironment.getValue(variableName);
        Type typeOfExpression = expression.typeCheck(typeEnvironment);
        if(typeOfVariable.equals(typeOfExpression))
            return typeEnvironment;
        else
            throw new InvalidTypeException("Assignment Exception: left hand side and right hand side have different values!");
    }

    public String toString(){
        return variableName + "=" + expression.toString();
    }
}
