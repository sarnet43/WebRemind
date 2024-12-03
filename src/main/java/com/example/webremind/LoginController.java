package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField userid; // 사용자 ID 입력 필드

    @FXML
    private PasswordField password; // 사용자 비밀번호 입력 필드

    @FXML
    private javafx.scene.control.Button btn_sing; // 회원가입 버튼

    @FXML
    private javafx.scene.control.Button btn_login; // 로그인 버튼

    @FXML
    private void initialize() {
        // ID 길이 제한 (최대 10자)
        userid.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) {
                userid.setText(oldValue);
            }
        });
    }

    @FXML
    private void onSignupButtonClick() {
        try {
            // 회원가입 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SingUp.fxml"));
            Parent signupScreen = loader.load();

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_sing.getScene().getWindow();

            // 회원가입 화면으로 Scene 전환
            Scene signupScene = new Scene(signupScreen);
            stage.setScene(signupScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoginButtonClick() {
        String user_id = userid.getText().trim(); // 입력된 사용자 ID
        String user_password = password.getText().trim(); // 입력된 비밀번호

        // 필드가 비어 있으면 경고 표시
        if (user_id.isEmpty() || user_password.isEmpty()) {
            showAlert("오류", "모두 입력하였는지 확인하세요.");
            return;
        }

        // 사용자 인증
        if (authenticateUser(user_id, user_password)) {
            try {
                // 로그인 성공 시 Community 화면 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Community.fxml"));
                Parent loginScreen = loader.load();

                Stage stage = (Stage) btn_login.getScene().getWindow();
                Scene loginScene = new Scene(loginScreen);
                stage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 로그인 실패 시 경고 표시
            showAlert("로그인 실패", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

    private boolean authenticateUser(String userId, String userPassword) {
        String query = "SELECT password FROM users WHERE userid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId); // userid 값을 바인딩

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // 입력된 비밀번호와 DB에 저장된 비밀번호를 비교
                    return storedPassword.equals(userPassword);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류: " + e.getMessage());
        }
        return false; // 사용자 인증 실패
    }

    // 경고창 표시 메서드
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}