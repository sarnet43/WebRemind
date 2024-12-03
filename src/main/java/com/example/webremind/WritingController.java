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

public class WritingController {

    @FXML
    private TextField titleField; // 제목 입력 필드

    @FXML
    private TextArea contentArea; // 내용 입력 필드

    @FXML
    private Button btn_save; // 저장 버튼

    @FXML
    private Button btn_back; // 뒤로 가기 버튼

    @FXML
    private void onsave_wrButtonClick() {
        String title = titleField.getText().trim(); // 제목을 가져옴
        String content = contentArea.getText().trim(); // 내용을 가져옴

        // 제목과 내용이 모두 비어있을 경우 경고 표시
        if (title.isEmpty() || content.isEmpty()) {
            showAlert("오류", "제목과 내용은 반드시 입력해야 합니다.");
            return;
        }

        // 게시글 저장 (실제 저장 로직으로 교체 가능)
        String post = title.isEmpty() ? content : title + " - " + content;
        DataStore.addPost(post);  // 가상의 데이터 저장 메서드 호출
        System.out.println("저장된 게시글: " + post);

        // 커뮤니티 화면으로 전환
        switchToScene("/fxml/Community.fxml");
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

    // 경고창 표시 메서드
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
