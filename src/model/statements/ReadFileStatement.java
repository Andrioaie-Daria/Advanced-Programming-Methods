package model.statements;

import exceptions.InvalidTypeException;
import exceptions.UndeclaredVariableException;
import model.ADTs.DictionaryInterface;
import model.ProgramState;
import model.expressions.Expression;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ReadFileStatement implements Statement{
    private final Expression filePath;
    private final String variableName;

    public ReadFileStatement(Expression filePath, String variableName) {
        this.filePath = filePath;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = programState.getFileTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();

        Value variableValue = symbolTable.getValue(variableName);
        if(variableValue == null){
            throw new UndeclaredVariableException(variableName + " is not defined!");
        }
        if(!variableValue.getType().equals(new IntType())){
            throw new InvalidTypeException("Variable " + variableName + " is not of type integer!");
        }
        Value filePathValue = filePath.evaluate(symbolTable, heap);
        if(!filePathValue.getType().equals(new StringType())){
            throw new InvalidTypeException("The file path is not a string!");
        }
        if(!fileTable.isDefined((StringValue) filePathValue)){
            throw new UndeclaredVariableException("There is no open file associated to the path!");
        }
        BufferedReader file = fileTable.getValue((StringValue) filePathValue);
        String currentLine = file.readLine();
        if (currentLine == null) {
            symbolTable.update(this.variableName, new IntValue()); // default value for int = 0
        }
        else {
            symbolTable.update(this.variableName, new IntValue(Integer.parseInt(currentLine)));
        }
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type typeOfFilePath = filePath.typeCheck(typeEnvironment);
        Type typeOfVariable = typeEnvironment.getValue(variableName);

        if(!(typeOfVariable instanceof IntType)) /// if the variable is not mapped to any type in the
                                                 /// type environment, this line will return a null, and it is implicitly checked
            throw new InvalidTypeException("ReadFileStatement: variable is not of type Integer!");

        if(!(typeOfFilePath instanceof StringType))
            throw new InvalidTypeException("ReadFileStatement: file path is not of type String!");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("readFile(" + this.filePath + ", " + variableName + ")");
        return representation;
    }
}
