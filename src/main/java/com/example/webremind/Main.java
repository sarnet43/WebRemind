package com.example.webremind;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // FXML 파일 로드
            FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/fxml/Startscreen.fxml"));
            Parent root = startLoader.load();

            // Scene 생성 및 Stage 설정
            Scene scene = new Scene(root);
            primaryStage.setTitle("Web Remind");
            primaryStage.setScene(scene);
            primaryStage.show();

            // 2초 대기 후 로그인 화면으로 전환
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> {

                try {
                    // 로그인 화면 로드
                    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
                    Parent loginScreen = loginLoader.load();
                    Scene loginScene = new Scene(loginScreen);

                    // Scene 교체
                    primaryStage.setScene(loginScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            delay.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}