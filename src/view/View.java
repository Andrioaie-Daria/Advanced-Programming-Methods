package view;

import controller.Controller;
import model.ADTs.MyDictionary;
import model.ADTs.MyHeap;
import model.ADTs.MyList;
import model.ADTs.MyStack;
import model.ProgramState;
import model.expressions.ArithmeticExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.util.Scanner;

public class View {
    private final Controller controller;
    private final Statement program1;
    private final Statement program2;
    private final Statement program3;
    private final Statement program4;

    public View(Controller newController){
        controller = newController;

        // int v; v=2; Print(v);
        program1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        // int a; int b; a=2+3*5; b=a+1; Print(b)
        program2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression("+",new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression("*",new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression("+" ,new VariableExpression("a"), new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        program3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));

        program4 =  new CompoundStatement(new VariableDeclarationStatement("filePath", new StringType()),
                            new CompoundStatement(new AssignmentStatement("filePath", new ValueExpression(new StringValue("C:\\Users\\daria\\Documents\\study\\facultate\\anul 2\\Semestrul 1\\Metode de programare\\Lab\\ToyInterpreter\\test.in"))),
                                    new CompoundStatement(new OpenReadFileStatement(new VariableExpression("filePath")),
                                            new CompoundStatement(new VariableDeclarationStatement("variable", new IntType()),
                                                    new CompoundStatement(new ReadFileStatement(new VariableExpression("filePath"), "variable"),
                                                            new CompoundStatement(new PrintStatement(new VariableExpression("variable")),
                                                                                  new CloseReadFileStatement(new VariableExpression("filePath"))))))));
    }

    public void printMenu(){
        System.out.print("0. Exit.\n");
        System.out.print("1. Choose a program to be executed.\n");
        System.out.print("2. Complete execution of the program.\n");
    }

    public void printAvailablePrograms(){
        System.out.print("0. Exit.\n");
        System.out.print("1. int v; v=2; Print(v)\n");
        System.out.print("2. int a; int b; a=2+3*5; b=a+1; Print(b)\n");
        System.out.print("3. bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)\n");
        System.out.print("4. file reading\n");
    }

    public void inputAProgram(){
        int choice;
        String choiceAsString;
        Scanner myInput = new Scanner(System.in);

        MyStack<Statement> executionStack = new MyStack<>();
        MyList<Value> standardOutput = new MyList<>();
        MyDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyHeap<Integer, Value> heap = new MyHeap<>();
        MyDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        Statement originalProgram;
        ProgramState programState;

        // while(!chosenProgram){
            printAvailablePrograms();
            System.out.print("Your choice: ");
            choiceAsString = myInput.next();
            System.out.println();

            try{
                choice = Integer.parseInt(choiceAsString);
                if(choice == 1){
                    System.out.println("Program 1 chosen.\n");
                    originalProgram = program1;
                    programState = new ProgramState(executionStack, symbolTable, standardOutput, heap, fileTable, originalProgram);
                    controller.addProgram(programState);

                }
                else if(choice == 2){
                    System.out.println("Program 2 chosen.\n");
                    originalProgram = program2;
                    programState = new ProgramState(executionStack, symbolTable, standardOutput, heap, fileTable, originalProgram);
                    controller.addProgram(programState);

                }
                else if(choice == 3){
                    System.out.println("Program 3 chosen.\n");
                    originalProgram = program3;
                    programState = new ProgramState(executionStack, symbolTable, standardOutput, heap, fileTable, originalProgram);
                    controller.addProgram(programState);

                }
                else if(choice == 4){
                    System.out.println("Program 4 chosen.\n");
                    originalProgram = program4;
                    programState = new ProgramState(executionStack, symbolTable, standardOutput, heap, fileTable, originalProgram);
                    controller.addProgram(programState);

                }

                /// if this point is reached, the input was not valid
                else
                    System.out.println("Your choice is invalid. You have to choose 0, 1, 2 or 3.\n");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
    //    }

    }

    public void completeExecution() throws Exception{
        controller.completeExecution();
    }

    public void run(){
        int choice;
        String choiceAsString;
        Scanner myInput = new Scanner(System.in);

        while(true){
            printMenu();
            System.out.print("Your choice: ");
            choiceAsString = myInput.next();
            System.out.println();
            try{
                choice = Integer.parseInt(choiceAsString);
                if(choice == 0){
                    System.out.println("Program execution has ended");
                    break;
                }
                else if (choice == 1){
                    inputAProgram();
                }

                else if (choice == 2){
                    completeExecution();
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

}
