package model.statements;

import model.ADTs.DictionaryInterface;
import model.ADTs.ListInterface;
import model.ProgramState;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class PrintStatement implements Statement{
    private final Expression expressionToBePrinted;

    public PrintStatement(Expression expression){
        expressionToBePrinted = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        ListInterface<Value> output = programState.getOutput();
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();

        Value result = expressionToBePrinted.evaluate(symbolTable, heap);
        output.addLast(result);
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        expressionToBePrinted.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "print (" + this.expressionToBePrinted.toString() + ")";
    }
}
