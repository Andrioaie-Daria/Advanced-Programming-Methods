package model.statements;
import model.ADTs.DictionaryInterface;
import model.ProgramState;
import model.types.Type;

public class NoOperationStatement implements Statement {
    public String toString(){
        return "no operation statement";
    }
    public ProgramState execute(ProgramState currentProgram) {
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        return typeEnvironment;
    }
}
