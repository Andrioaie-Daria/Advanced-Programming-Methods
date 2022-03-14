package model.statements;

import model.ADTs.*;
import model.ProgramState;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ForkStatement implements Statement{
    Statement childStatement;

    public ForkStatement(Statement childStatement) {
        this.childStatement = childStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        MyStack<Statement> childExecutionStack = new MyStack<>();
        MyList<Value> childStandardOutput = (MyList<Value>) programState.getOutput();
        MyDictionary<String, Value> childSymbolTable = (MyDictionary<String, Value>) programState.getSymbolTable().clone();
        MyDictionary<StringValue, BufferedReader> childFileTable = (MyDictionary<StringValue, BufferedReader>) programState.getFileTable();
        MyHeap<Integer, Value> childHeap = (MyHeap<Integer, Value>) programState.getHeap();

        ProgramState childProgram = new ProgramState(childExecutionStack, childSymbolTable, childStandardOutput, childHeap,
                childFileTable, childStatement);

        return childProgram;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        childStatement.typeCheck(typeEnvironment.clone());
        return typeEnvironment;
    }

    @Override
    public String toString(){
        String representation = "";
        representation += "fork(" + childStatement.toString() + ")";
        return representation;
    }
}
