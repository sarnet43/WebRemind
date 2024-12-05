package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
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

    private Font FONT;

    @FXML
    private void initialize() {

        FONT = Font.loadFont(getClass().getResourceAsStream("/font/SB 어그로 B.ttf"), 16);
        if (FONT == null) {
            System.out.println("폰트 로드 실패: 기본 폰트를 사용합니다.");
            FONT = Font.font("System", 16); // 기본 폰트로 대체
        }

        // 컴포넌트에 폰트 적용
        titleField.setFont(FONT);
        contentArea.setFont(FONT);
        btn_save.setFont(Font.font(FONT.getFamily(), 14));

        // 버튼 초기화 시 스타일 적용
        btn_save.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                btn_save.setStyle("-fx-font-family: '" + FONT.getFamily() + "';");
            }
        });

        // 포커스가 변경될 때마다 폰트 재적용
        titleField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                titleField.setFont(FONT);
            }
        });

        // 포커스가 변경될 때마다 폰트 재적용
        contentArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                contentArea.setFont(FONT);
            }
        });

        // titleField의 textProperty에 Listener 추가
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            // 텍스트 길이가 10자 이상이면, 글자를 더 이상 추가할 수 없도록 함
            if (newValue.length() > 20) {
                titleField.setText(oldValue); // 이전 값으로 되돌림
            }
        });
    }

    @FXML
    private void onsave_wrButtonClick() {
        String title = titleField.getText().trim(); // 제목을 가져옴
        String content = contentArea.getText().trim(); // 내용을 가져옴

        // 제목과 내용이 모두 비어있을 경우 경고 표시
        if (title.isEmpty() || content.isEmpty()) {
            showAlert("오류", "제목과 내용은 반드시 입력해야 합니다.");
            return;
        }

        // 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
        LocalDate now = LocalDate.now();

        // 게시글 저장 (각각 따로 저장)
        DataStore.addTitle(title);
        DataStore.addDate(now.toString());
        DataStore.addContent(content);

        // 게시글 정보 출력 (디버깅용)
        System.out.println("저장된 게시글: " + title + ", " + now + ", " + content);

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