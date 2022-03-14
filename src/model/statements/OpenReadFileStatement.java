package model.statements;

import exceptions.InvalidTypeException;
import exceptions.VariableAlreadyDefinedException;
import model.ADTs.DictionaryInterface;
import model.ProgramState;
import model.expressions.Expression;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenReadFileStatement implements Statement{
    private final Expression filePath;
    public OpenReadFileStatement(Expression exp){
        filePath = exp;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = programState.getFileTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();

        Value filePathValue = filePath.evaluate(symbolTable, heap);
        if(!filePathValue.getType().equals(new StringType())){
            throw new InvalidTypeException("The file path is not a string!");
        }
        String filePathString = ((StringValue)filePathValue).getValue();

        if(fileTable.isDefined((StringValue)filePathValue)){
            throw new VariableAlreadyDefinedException("OpenReadFileStatement: File path " + filePathString + " is already in the file table");
        }

        BufferedReader fileBuffer = new BufferedReader(new FileReader(filePathString));
        fileTable.insert((StringValue)filePathValue, fileBuffer);
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type typeOfFilePath = filePath.typeCheck(typeEnvironment);
        if(typeOfFilePath instanceof StringType)
            return typeEnvironment;
        else
            throw new InvalidTypeException("OpenReadFileStatement: file path is not of type String!");
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("openRead(" + this.filePath + ")");
        return representation;
    }
}
