package ru.vidimka;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Main extends Application{

    public static Stage stage;
    private final ScreenController controller = new ScreenController();

    @Override
    public void start(Stage stage) throws IOException{
        Main.stage = stage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/MainStyle.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }
    public static void main(String[] args){ Application.launch(); }
}
