package model;
import exceptions.EmptyCollectionException;
import model.ADTs.*;
import model.statements.Statement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ProgramState {
    private StackInterface<Statement> executionStack;
    private DictionaryInterface<String, Value> symbolTable;
    private ListInterface<Value> standardOutput;
    private DictionaryInterface<Integer, Value> heap;
    private DictionaryInterface<StringValue, BufferedReader> fileTable;
    private Statement originalProgram;

    private static int globalThreadCount = 1;
    private int id;

    public static synchronized int manageThreadId(){
        int newThreadId = ProgramState.globalThreadCount;
        ProgramState.globalThreadCount += 1;
        return newThreadId;
    }

    public int getThreadId(){
        return this.id;
    }


    public ProgramState(StackInterface<Statement> stack,
                        DictionaryInterface<String,Value> symbol_table,
                        ListInterface<Value> output,
                        DictionaryInterface<Integer, Value> memory_heap,
                        DictionaryInterface<StringValue, BufferedReader> file_table,
                        Statement program){
        executionStack=stack;
        symbolTable=symbol_table;
        standardOutput = output;
        heap = memory_heap;
        fileTable = file_table;
        originalProgram = program; //recreate the entire original prg
        executionStack.push(originalProgram);
        this.id = ProgramState.manageThreadId();
    }

    public StackInterface<Statement> getExecutionStack() {
        return executionStack;
    }


    public DictionaryInterface<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public ListInterface<Value> getOutput() {
        return standardOutput;
    }

    public DictionaryInterface<Integer, Value> getHeap() {
        return heap;
    }

    public DictionaryInterface<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setSymbolTable(DictionaryInterface<String, Value> newTable){
        symbolTable = newTable;
    }

    public void setStandardOutput(ListInterface<Value> newOutput){
        standardOutput = newOutput;
    }

    public void setExecutionStack(StackInterface<Statement> newStack){
        executionStack = newStack;
    }

    public void setHeap(DictionaryInterface<Integer, Value> newHeap){
        heap = newHeap;
    }

    public void setFileTable(DictionaryInterface<StringValue, BufferedReader> newTable){
        fileTable = newTable;
    }

    public void addStatement(Statement statement) {
        // we don't add null values to the exeStack because the Deque doesn't accept it
        if (statement != null) {
            this.executionStack.push(statement);
        }
    }

    public String toString() {
        String representation = "";

        representation += "Id :" + this.id + "\n";

        representation += "ExecutionStack:\n";
        representation += this.executionStack.toString();
        representation += "SymbolTable:\n";
        representation += this.symbolTable.toString();
        representation += "OutputTable:\n";
        representation += this.standardOutput.toString();
        representation += "Heap:\n";
        representation += this.heap.toString();
        representation += "FileTable:\n";
        representation += this.fileTable.toString();

        return representation;
    }
    public boolean isNotCompleted(){
        return !(executionStack.size() == 0);
    }

    public ProgramState oneStepExecution() throws Exception {
        if(executionStack.isEmpty())
            throw new EmptyCollectionException("The execution stack is empty.");
        Statement topStatement = executionStack.pop();
        return topStatement.execute(this);
    }
}

