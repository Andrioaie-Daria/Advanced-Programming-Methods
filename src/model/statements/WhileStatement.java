package model.statements;

import exceptions.InvalidTypeException;
import model.ADTs.DictionaryInterface;
import model.ADTs.StackInterface;
import model.ProgramState;
import model.expressions.Expression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStatement implements Statement{
    Expression conditionalExpression;
    Statement statement;

    public WhileStatement(Expression conditionalExpression, Statement statement) {
        this.conditionalExpression = conditionalExpression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        StackInterface<Statement> stack = programState.getExecutionStack();
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        DictionaryInterface<Integer, Value> heap = programState.getHeap();

        Value valueOfConditionalExpression = this.conditionalExpression.evaluate(symbolTable, heap);

        // if the condition is not a boolean expression
        if(!valueOfConditionalExpression.getType().equals(new BoolType())){
            throw new InvalidTypeException("The conditional expression is not boolean");
        }

        // if the condition is true
        if(((BoolValue) valueOfConditionalExpression).getValue()){
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws Exception {
        Type typeOfCondition = conditionalExpression.typeCheck(typeEnvironment);
        if(typeOfCondition.equals(new BoolType())){
            statement.typeCheck(typeEnvironment.clone());
            return typeEnvironment;
        }
        throw new InvalidTypeException("While statement: the condition is not of type Bool!");
    }

    public String toString() {
        String representation = "";

        representation += ("while (" + this.conditionalExpression.toString() + ") { " +this.statement.toString() + " }");
        return representation;
    }

}
