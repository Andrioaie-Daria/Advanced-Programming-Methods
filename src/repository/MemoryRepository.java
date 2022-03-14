package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import model.ProgramState;

public class MemoryRepository implements RepositoryInterface{
    private List<ProgramState> programList;
    private final String logFilePath;

    public MemoryRepository(List<ProgramState> threadList, String filePath){
        this.programList = threadList;
        logFilePath = filePath;
    }

    public List<ProgramState> getProgramList() {
        return programList;
    }

    @Override
    public void setProgramList(List<ProgramState> newThreadList) {
        this.programList = newThreadList;
    }

    /*@Override
    public ProgramState getCurrentProgram() throws Exception{
        if(threadList.isEmpty())
            throw new EmptyCollectionException("The thread list is empty");
        return threadList.get(0);
    }*/

    @Override
    public void logProgramStateExecution(ProgramState programState) throws Exception {
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.append(programState.toString());
        logFile.append("\n");
        logFile.append("=================================================================================\n");
        logFile.append("\n");
        logFile.close();
    }

    @Override
    public void clearLogFile() throws Exception {
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.write("");
        logFile.close();
    }

    @Override
    public void addProgram(ProgramState newProgram) {
        programList.add(newProgram);
    }
}
