package controller;

import model.ADTs.DictionaryInterface;
import model.ProgramState;
import model.values.ReferenceValue;
import model.values.Value;
import repository.RepositoryInterface;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    protected RepositoryInterface repository;
    protected ExecutorService executor;

    public Controller(RepositoryInterface newRepository) throws Exception {
        repository = newRepository;
        repository.clearLogFile();
    }

/*    public ProgramState oneStepExecution(ProgramState currentProgram) throws Exception {
        StackInterface<Statement> executionStack = currentProgram.getExecutionStack();
        if(executionStack.isEmpty())
            throw new EmptyCollectionException("The execution stack is empty.");
        Statement topStatement = executionStack.pop();
        topStatement.execute(currentProgram);
        return currentProgram;
    }*/

/*
    public ProgramState completeExecution() throws Exception{
        ProgramState currentProgramState = repository.getCurrentProgram();
        repository.clearLogFile();
        repository.logProgramStateExecution();

        collectGarbage(currentProgramState);

        StackInterface<Statement> executionStack = currentProgramState.getExecutionStack();

        while(!executionStack.isEmpty()){
            currentProgramState= oneStepExecution(currentProgramState);
            repository.logProgramStateExecution();
        }
        System.out.println("Program is now complete.");

        return currentProgramState;
    }
*/

    public void completeExecution() throws Exception {
        //repository.clearLogFile();

        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStatesList = removeCompletedProgram(repository.getProgramList());

        repository.getProgramList().forEach(programState -> {
            try {
                repository.logProgramStateExecution(programState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        while(programStatesList.size()>0){
            collectGarbage();
            oneStepForAllPrograms(programStatesList);

            //remove the completed programs
            programStatesList = removeCompletedProgram(repository.getProgramList());
        }
        executor.shutdownNow();

        // update the repository state
        repository.setProgramList(programStatesList);
    }

    public void oneStepExecution() throws Exception {
        //repository.clearLogFile();

        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStatesList = removeCompletedProgram(repository.getProgramList());


        if (programStatesList.size() > 0) {
            try {
                collectGarbage();
                oneStepForAllPrograms(programStatesList);

                //remove the completed programs
                programStatesList = removeCompletedProgram(repository.getProgramList());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            programStatesList.forEach(p -> {
                try {
                    repository.logProgramStateExecution(p);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        executor.shutdownNow();
        // update the repository state
        repository.setProgramList(programStatesList);
    }

    void oneStepForAllPrograms(List<ProgramState> programStates) throws Exception {

        /// prepare the list of callables
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState program) -> (Callable<ProgramState>)(()->{return program.oneStepExecution();}))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created program states (namely threads)
        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                })
                .filter(program -> program != null)
                .collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        programStates.addAll(newProgramList);

        //after the execution, print the PrgState List into the log file
        programStates.forEach(programState -> {
            try {
                repository.logProgramStateExecution(programState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //Save the current programs in the repository
        repository.setProgramList(programStates);
    }

    private void collectGarbage(){
        List<ProgramState> programStates = repository.getProgramList();
        DictionaryInterface<Integer, Value> heap = programStates.get(0).getHeap();
        List<Integer> symbolTableAddresses = getAddressesFromSymbolTableAndHeap(programStates, heap.getAllPairs().values());

        heap.setContent((HashMap<Integer, Value>) safeGarbageCollector(symbolTableAddresses,
                heap.getAllPairs()));
    }

    public void addProgram(ProgramState newProgram){
        repository.addProgram(newProgram);
    }

    public void displayCurrentProgramState(ProgramState currentProgram){
        System.out.print(currentProgram.toString());
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> addressesInTheSymbolTable, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->addressesInTheSymbolTable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddressesFromSymbolTableAndHeap(List<ProgramState> programStateList, Collection<Value> heap){
        Collection<Value> symbolTablesValues = new ArrayList<>();
        programStateList.forEach(programState -> symbolTablesValues.addAll(programState.getSymbolTable().getAllPairs().values()));

        return Stream.concat(
                        heap.stream()
                                .filter(v -> v instanceof ReferenceValue)
                                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getHeapAddress();}),
                        symbolTablesValues.stream()
                                .filter(v -> v instanceof ReferenceValue)
                                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getHeapAddress();})
                )
                .collect(Collectors.toList());
    }

    List<ProgramState> removeCompletedProgram(List<ProgramState> inputProgramList){
        return inputProgramList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public RepositoryInterface getRepository(){
        return repository;
    }
}
