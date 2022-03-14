package repository;
import model.ProgramState;

import java.util.List;

public interface RepositoryInterface {
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> newThreadList);
    //ProgramState getCurrentProgram() throws Exception;
    void logProgramStateExecution(ProgramState programState) throws Exception;
    void clearLogFile() throws Exception;
    void addProgram(ProgramState newProgram);
}
