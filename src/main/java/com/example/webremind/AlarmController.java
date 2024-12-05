package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AlarmController {

    @FXML
    private javafx.scene.control.Button btn_alarm;           // 알림 버튼
    @FXML
    private javafx.scene.control.Button btn_home;            // 홈 버튼
    @FXML
    private javafx.scene.control.Button btn_mypage;          // 마이페이지 버튼
    @FXML
    private Text alarm_text;                                 // "알림" 텍스트
    @FXML
    private ListView<String> notificationList;               // 알림 리스트

    private Font FONT;                                       // 커스텀 폰트

    @FXML
    private void initialize() {
        FONT = Font.loadFont(getClass().getResourceAsStream("/font/SB 어그로 B.ttf"), 16);
        if (FONT == null) {
            System.out.println("폰트 로드 실패: 기본 폰트를 사용합니다.");
            FONT = Font.font("System", 16); // 기본 폰트로 대체
        }

        // 컴포넌트에 폰트 적용
        alarm_text.setFont(FONT);
        notificationList.setStyle("-fx-font-family: '" + FONT.getFamily() + "'; -fx-font-size: 14px;");

        // 알림 리스트에 저장된 댓글 불러오기
        loadNotifications();

        // 알림 항목을 두 번 클릭 시 해당 게시글로 이동
        notificationList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedComment = notificationList.getSelectionModel().getSelectedItem();
                if (selectedComment != null) {
                    //switchToPostContent(selectedComment);
                }
            }
        });
    }

    // 알림을 화면에 불러오기 (DB에서 불러온 데이터를 사용)
    private void loadNotifications() {
        List<String> notifications = DataStore.getNotifications();
        notificationList.getItems().setAll(notifications);
    }


    @FXML
    private void onalarm_alButtonClick() {
        try {
            // 알림 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Alarm.fxml"));
            Parent alarmScreen = loader.load();

            Stage stage = (Stage) btn_alarm.getScene().getWindow();
            stage.setScene(new Scene(alarmScreen));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onhome_alButtonClick() {
        try {
            // 홈 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Community.fxml"));
            Parent homeScreen = loader.load();

            Stage stage = (Stage) btn_home.getScene().getWindow();
            stage.setScene(new Scene(homeScreen));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onmypage_alButtonClick() {
        try {
            // 마이페이지 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Mypage.fxml"));
            Parent mypageScreen = loader.load();

            Stage stage = (Stage) btn_mypage.getScene().getWindow();
            stage.setScene(new Scene(mypageScreen));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 경고창 표시 메서드
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-family: '" + FONT.getFamily() + "'; -fx-font-size: 14px;");
        alert.showAndWait();
    }
}
