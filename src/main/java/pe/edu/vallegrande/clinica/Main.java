package pe.edu.vallegrande.clinica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("/view/pacientes.fxml"));
        stage.setTitle("DentalCare - Gestión de Pacientes");
        stage.setScene(new Scene(root, 960, 670));
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
