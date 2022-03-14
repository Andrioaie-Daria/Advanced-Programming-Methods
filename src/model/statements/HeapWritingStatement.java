package model.statements;

import exceptions.InvalidTypeException;
import exceptions.UndeclaredVariableException;
import model.ADTs.DictionaryInterface;
import model.ProgramState;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class HeapWritingStatement implements Statement{
    String variable_name;
    Expression expression;

    public HeapWritingStatement(String variable_name, Expression expression){
        this.variable_name = variable_name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();

        Value variable_value = symbolTable.getValue(variable_name);
        if(!symbolTable.isDefined(variable_name))
            throw new UndeclaredVariableException("Variable " + variable_name + " is not defined");

        Type variable_type = variable_value.getType();
        if(!(variable_type instanceof ReferenceType))
            throw new InvalidTypeException("The variable " + variable_name + " is not of type reference.");

        int address = ((ReferenceValue)variable_value).getHeapAddress();
        Type referencedType = ((ReferenceValue)variable_value).getReferencedType();
        if(!heap.isDefined(address))
            throw new UndeclaredVariableException("HeapWritingStatement: Undefined variable at address 0x" + Integer.toHexString(address));

        Value expression_value = expression.evaluate(symbolTable, heap);
        if(!expression_value.getType().equals(referencedType))
            throw new InvalidTypeException("The referenced type of " + variable_name + " does not match the expression");

        heap.update(address, expression_value);

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type typeOfVariable = typeEnvironment.getValue(variable_name);

        if(typeOfVariable == null)
            throw new UndeclaredVariableException("The variable " + variable_name + " is not defined!");

        Type typeOfExpression = expression.typeCheck(typeEnvironment);
        if(typeOfVariable.equals(new ReferenceType(typeOfExpression))){
            return typeEnvironment;
        }
        else
            throw new InvalidTypeException("Heap writing statement: The variable " + variable_name + " and the expression " + expression + " have different types!");
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("*(" + variable_name + ") = " + expression.toString());
        return representation;
    }
}
