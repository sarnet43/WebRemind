package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoingController {

    @FXML
    private javafx.scene.control.Button btn_sing;

    @FXML
    private void onSignupButtonClick() {
        try {
            // 회원가입 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SingUp.fxml"));
            Parent signupScreen = loader.load();

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_sing.getScene().getWindow();

            // 회원가입 화면으로 Scene 교체
            Scene signupScene = new Scene(signupScreen);
            stage.setScene(signupScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
