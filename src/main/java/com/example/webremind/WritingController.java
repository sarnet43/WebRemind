package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class WritingController {

    @FXML
    private TextField titleField; // 제목 입력 필드

    @FXML
    private TextArea contentArea; // 내용 입력 필드

    @FXML
    private Button btn_save; // 저장 버튼

    @FXML
    private Button btn_back; // 뒤로 가기 버튼

    // 로그인한 사용자의 이름을 저장할 변수
    private String loggedInUserName;

    public void setLoggedInUserName(String userName) {
        this.loggedInUserName = userName;
        System.out.println("로그인한 사용자 이름: " + userName);
    }


    @FXML
    private void initialize() {
        // titleField의 textProperty에 Listener 추가
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20) {
                titleField.setText(oldValue); // 이전 값으로 되돌림
            }
        });
    }

    @FXML
    private void onsave_wrButtonClick() {
        String title = titleField.getText().trim(); // 제목을 가져옴
        String content = contentArea.getText().trim(); // 내용을 가져옴

        if (title.isEmpty() || content.isEmpty()) {
            showAlert("오류", "제목과 내용은 반드시 입력해야 합니다.");
            return;
        }

        LocalDate now = LocalDate.now(); // 현재 날짜

        // DB에 게시글 저장
        if (savePostToDatabase(title, content, now.toString())) {
            System.out.println("게시글이 성공적으로 저장되었습니다.");
            switchToScene("/fxml/Community.fxml"); // 저장 후 커뮤니티 화면으로 전환
        } else {
            showAlert("오류", "게시글 저장에 실패했습니다.");
        }
    }

    private boolean savePostToDatabase(String title, String content, String date) {
        System.out.println("DB에 저장되는 사용자 이름: " + loggedInUserName);
        String query = "INSERT INTO posts (user_name, title, content, post_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, loggedInUserName); // 로그인한 사용자 이름
            pstmt.setString(2, title); // 제목
            pstmt.setString(3, content); // 내용
            pstmt.setString(4, date); // 작성 날짜

            pstmt.executeUpdate(); // 쿼리 실행
            return true;
        } catch (SQLException e) {
            System.out.println("SQL 오류: " + e.getMessage());
            return false;
        }
    }


    @FXML
    private void onback_wrButtonClick() {
        switchToScene("/fxml/Community.fxml"); // 뒤로 가기 버튼 클릭 시 커뮤니티 화면으로 전환
    }


    // 화면 전환 메서드
    private void switchToScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent screen = loader.load();

            Stage stage = (Stage) btn_save.getScene().getWindow();
            stage.setScene(new Scene(screen));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("오류", "화면을 불러올 수 없습니다: " + fxmlFile);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}