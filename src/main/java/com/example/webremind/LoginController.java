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
        String user_id = userid.getText().trim();
        String user_password = password.getText().trim();

        if (user_id.isEmpty() || user_password.isEmpty()) {
            showAlert("오류", "모두 입력하였는지 확인하세요.");
            return;
        }

        // LoginController.java
        if (authenticateUser(user_id, user_password)) {
            String userName = fetchUserName(user_id); // 사용자 이름 가져오기
            if (userName != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Community.fxml"));
                    Parent communityScreen = loader.load();

                    // CommunityController에 사용자 이름 전달
                    CommunityController communityController = loader.getController();
                    communityController.setLoggedInUserName(userName);

                    // 현재 Stage 가져오기
                    Stage stage = (Stage) btn_login.getScene().getWindow();
                    stage.setScene(new Scene(communityScreen));  // 커뮤니티 화면으로 전환
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("오류", "사용자 정보를 가져오는 데 실패했습니다.");
            }
        } else {
            showAlert("로그인 실패", "아이디 또는 비밀번호가 잘못되었습니다.");
        }

    }

    private String fetchUserName(String userId) {
        String query = "SELECT username FROM users WHERE userid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId); // 전달받은 userId로 검색
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("username");
                System.out.println("DB에서 조회된 사용자 이름: " + userName); // 디버깅용
                return userName;
            } else {
                System.out.println("DB에서 사용자 이름을 찾지 못했습니다."); // 디버깅용
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류: " + e.getMessage());
        }
        return null; // 사용자를 찾지 못한 경우 null 반환
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