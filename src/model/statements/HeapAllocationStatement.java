package model.statements;

import exceptions.InvalidTypeException;
import exceptions.UndeclaredVariableException;
import model.ADTs.DictionaryInterface;
import model.ADTs.MyHeap;
import model.ProgramState;
import model.expressions.Expression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class HeapAllocationStatement implements Statement {
    private final String variableName;
    private final Expression expression;

    public HeapAllocationStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();

        if(!symbolTable.isDefined(variableName))
            throw new UndeclaredVariableException("The variable " + variableName + " is not defined.");

        Value variable_value = symbolTable.getValue(variableName);
        Type variable_type = variable_value.getType();
        if(!(variable_type instanceof ReferenceType))
            throw new InvalidTypeException("The variable " + variableName + " is not of type reference");

        Value expression_value = expression.evaluate(symbolTable, heap);
        Type expression_type = expression_value.getType();

        Type referencedType = ((ReferenceValue)variable_value).getReferencedType();

        if(!expression_type.equals(referencedType))
            throw new InvalidTypeException("Expression cannot be evaluated to a " + referencedType.toString());

        int positionInHeap = ((MyHeap<Integer, Value>)heap).getFirstAvailablePosition();
        heap.insert(positionInHeap, expression_value);

        symbolTable.update(variableName, new ReferenceValue(positionInHeap, referencedType));

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type typeOfVariable = typeEnvironment.getValue(variableName);

        if(typeOfVariable == null)
            throw new UndeclaredVariableException("The variable " + variableName + " is not defined!");

        Type typeOfExpression = expression.typeCheck(typeEnvironment);
        if(typeOfVariable.equals(new ReferenceType(typeOfExpression))){
            return typeEnvironment;
        }
        else
            throw new InvalidTypeException("Heap allocation statement: The variable " + variableName + " and the expression " + expression +" have different types!");
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("new(" + this.variableName + ", " + this.expression.toString() + ")");
        return representation;
    }
}
