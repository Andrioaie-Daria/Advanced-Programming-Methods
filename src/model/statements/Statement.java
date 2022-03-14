package model.statements;
import model.ADTs.DictionaryInterface;
import model.ProgramState;
import model.types.Type;

public interface Statement {
    ProgramState execute(ProgramState programState) throws Exception;
    DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception;
    String toString();
}
