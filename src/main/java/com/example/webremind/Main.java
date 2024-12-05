package com.example.webremind;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    @FXML
    private Text start_text;

    private Font FONT; // 커스텀 폰트

    @FXML
    private void initialize() {

        FONT = Font.loadFont(getClass().getResourceAsStream("/font/SB 어그로 B.ttf"), 16);
        if (FONT == null) {
            System.out.println("폰트 로드 실패: 기본 폰트를 사용합니다.");
            FONT = Font.font("System", 16); // 기본 폰트로 대체
        }

        // 컴포넌트에 폰트 적용
        start_text.setFont(FONT);

        // 포커스가 변경될 때마다 폰트 재적용
        start_text.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                start_text.setFont(FONT);
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // FXML 파일 로드
            FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/fxml/startscreen.fxml"));
            Parent root = startLoader.load();

            // Scene 생성 및 Stage 설정
            Scene scene = new Scene(root);
            primaryStage.setTitle("Web Remind");
            primaryStage.setScene(scene);

            //아이콘 설정
            Image icon = new Image(getClass().getResourceAsStream("/image/logo.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setResizable(false); // 윈도우 크기 고정
            primaryStage.show();

            // 2초 대기 후 로그인 화면으로 전환
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> {

                try {
                    // 로그인 화면 로드
                    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
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