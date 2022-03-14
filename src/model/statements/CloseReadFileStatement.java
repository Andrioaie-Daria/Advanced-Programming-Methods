package model.statements;

import exceptions.InvalidTypeException;
import exceptions.UndeclaredVariableException;
import model.ADTs.DictionaryInterface;
import model.ProgramState;
import model.expressions.Expression;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class CloseReadFileStatement implements Statement{
    private Expression filePath;

    public CloseReadFileStatement(Expression exp){
        filePath = exp;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = programState.getFileTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();

        Value filePathValue = this.filePath.evaluate(symbolTable, heap);
        if(!filePathValue.getType().equals(new StringType())){
            throw new InvalidTypeException("The file path is not a string!");
        }
        String filePathString = ((StringValue)filePathValue).getValue();
        BufferedReader fileBuffer = fileTable.getValue((StringValue)filePathValue);
        if(fileBuffer == null){
            throw new UndeclaredVariableException("The file from path " + filePathString + " is not open.");
        }
        fileBuffer.close();
        fileTable.remove((StringValue)filePathValue);
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type typeOfFilePath = filePath.typeCheck(typeEnvironment);
        if(typeOfFilePath instanceof StringType)
            return typeEnvironment;
        else
            throw new InvalidTypeException("CloseReadFileStatement: file path is not of type String!");
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("closeRead(" + this.filePath + ")");
        return representation;
    }
}
