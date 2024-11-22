package com.example.webremind;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // FXML 파일 로드
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/startscreen.fxml"));
            Parent root = fxmlLoader.load();

            // Scene 생성 및 Stage 설정
            Scene scene = new Scene(root);
            primaryStage.setTitle("Web Remind");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}