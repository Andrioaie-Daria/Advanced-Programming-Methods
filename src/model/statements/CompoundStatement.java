package model.statements;

import model.ADTs.DictionaryInterface;
import model.ADTs.StackInterface;
import model.ProgramState;
import model.types.Type;

public class CompoundStatement implements Statement {
    private final Statement firstStatement;
    private final Statement secondStatement;

    public CompoundStatement(Statement first, Statement second){
        firstStatement = first;
        secondStatement = second;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        StackInterface<Statement> executionStack = programState.getExecutionStack();
        executionStack.push(secondStatement);
        executionStack.push(firstStatement);
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnvironment));
    }

    @Override
    public String toString(){
        return "(" + firstStatement.toString() + ";" + secondStatement.toString() + ")";
    }
}
