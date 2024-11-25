package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SingupController {
    private Stage primaryStage;

    // Stage를 설정하기 위한 메서드
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleSignupButton() {
        try {
            // 회원가입 화면 로드
            FXMLLoader signupLoader = new FXMLLoader(getClass().getResource("/fxml/SignUp.fxml"));
            Parent signupScreen = signupLoader.load();
            Scene signupScene = new Scene(signupScreen);

            // Scene 교체
            primaryStage.setScene(signupScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}