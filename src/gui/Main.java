package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader();

        // initialize the first window
        mainLoader.setLocation(getClass().getResource("ProgramsListView.fxml"));
        Parent mainWindow = mainLoader.load();

        ProgramsListView controller = mainLoader.getController();
        /// populate the list view with the available programs
        controller.populateListView();

        primaryStage.setTitle("Select Program");
        primaryStage.setScene(new Scene(mainWindow));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
