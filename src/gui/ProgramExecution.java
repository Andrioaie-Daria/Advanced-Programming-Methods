package gui;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.ADTs.DictionaryInterface;
import model.ADTs.ListInterface;
import model.ProgramState;
import model.statements.Statement;
import model.values.Value;

import java.util.*;
import java.util.stream.Collectors;

public class ProgramExecution {
    private Controller controller;
    private ProgramState selectedProgram;

    @FXML
    private ListView<String> outputTable = new ListView<>();
    @FXML
    private ListView<String> fileListView = new ListView<>();
    @FXML
    private ListView<Integer> listOfConcurrentProgramStates = new ListView<>();
    @FXML
    private TableView<Map.Entry<String, String>> symbolTableView = new TableView<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symbolTableValueColumn = new TableColumn<>();
    @FXML
    private TableColumn<Map.Entry<String, String>, String> symbolTableVariableNameColumn = new TableColumn<>();
    @FXML
    private ListView<String> executionStack = new ListView<>();
    @FXML
    private TextField numberOfPrograms = new TextField("");
    @FXML
    private TableView<HashMap.Entry<Integer, String>> heapTableView = new TableView<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, Integer> heapAddressColumn=new TableColumn<>();
    @FXML
    private TableColumn<HashMap.Entry<Integer, String>, String> heapValueColumn=new TableColumn<>();

    public void setController(Controller controller) {
        this.controller = controller;
        selectedProgram = this.controller.getRepository().getProgramList().get(0);
        loadData();
    }

    private void loadData(){
        listOfConcurrentProgramStates.getItems().setAll(controller.getRepository().getProgramList().stream()
                .map(ProgramState::getThreadId).collect(Collectors.toList()));

        if (selectedProgram != null) {
            numberOfPrograms.setText(Integer.toString(controller.getRepository().getProgramList().size()));

            outputTable.getItems().setAll(selectedProgram.getOutput().getList().stream()
                    .map(Object::toString).collect(Collectors.toList()));

            fileListView.getItems().setAll(String.valueOf(selectedProgram.getFileTable().getAllPairs().keySet()));

            executionStack.getItems().setAll(selectedProgram.getExecutionStack().getStack().stream()          // we can use directly .setItems() only when we have an ObservableList
                    .map(Statement::toString).collect(Collectors.toList()));

            DictionaryInterface<Integer, Value> heapTable = selectedProgram.getHeap();
            List<Map.Entry<Integer, String>> heapTableList = new ArrayList<>();
            for (Map.Entry<Integer, Value> elem : heapTable.getAllPairs().entrySet()) {
                Map.Entry<Integer, String> el = new AbstractMap.SimpleEntry<>(elem.getKey(), elem.getValue().toString());
                heapTableList.add(el);
            }
            heapTableView.setItems(FXCollections.observableList(heapTableList));
            heapTableView.refresh();

            heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
            heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));

            DictionaryInterface<String, Value> symbolTable = selectedProgram.getSymbolTable();
            List<Map.Entry<String, String>> symbolTableList = new ArrayList<>();
            for (Map.Entry<String, Value> elem : symbolTable.getAllPairs().entrySet()) {
                Map.Entry<String, String> el = new AbstractMap.SimpleEntry<>(elem.getKey(), elem.getValue().toString());
                symbolTableList.add(el);
            }
            symbolTableView.setItems(FXCollections.observableList(symbolTableList));
            symbolTableView.refresh();

            symbolTableVariableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
            symbolTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        }
    }

    public void oneStepProgramExecution(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        if(Integer.parseInt(numberOfPrograms.getText()) == 0){
            alert.setHeaderText("The program is over");
            alert.setContentText("Select a new program to execute!");
            alert.showAndWait();
        }

        controller.oneStepExecution();
        loadData();
    }

    public void setSelectedProgram(MouseEvent mouseEvent) {
        if(listOfConcurrentProgramStates.getSelectionModel().getSelectedIndex() >= 0){
            selectedProgram = controller.getRepository().getProgramList().get(listOfConcurrentProgramStates.getSelectionModel().getSelectedIndex());
            loadData();
        }
    }
}
