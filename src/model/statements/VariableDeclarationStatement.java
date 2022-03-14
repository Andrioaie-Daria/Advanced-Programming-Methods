package model.statements;
import exceptions.VariableAlreadyDefinedException;
import model.ADTs.DictionaryInterface;
import model.types.Type;
import model.ProgramState;
import model.values.Value;

public class VariableDeclarationStatement implements Statement {
    private final Type variableType;
    private final String variableName;

    public VariableDeclarationStatement(String name, Type type){
        variableType = type;
        variableName = name;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        if(symbolTable.isDefined(variableName)){
            throw new VariableAlreadyDefinedException("Variable already exists.");
        }
        else
            symbolTable.insert(variableName, variableType.defaultValue());
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        typeEnvironment.insert(variableName, variableType);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return variableType.toString() + " " + variableName;
    }
}
