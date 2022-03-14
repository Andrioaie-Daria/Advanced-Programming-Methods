package model.expressions;
import model.types.Type;
import model.values.Value;
import model.ADTs.DictionaryInterface;

public interface Expression {
    Value evaluate(DictionaryInterface<String, Value> symbolTable, DictionaryInterface<Integer, Value> heap) throws Exception;
    //Type getType();
    String toString();
    Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception;
}
