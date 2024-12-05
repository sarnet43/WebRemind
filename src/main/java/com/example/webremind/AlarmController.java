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

public class AlarmController {

    @FXML
    private javafx.scene.control.Button btn_alarm;           //알림 버튼 id
    @FXML
    private javafx.scene.control.Button btn_home;          //홈 버튼 id
    @FXML
    private javafx.scene.control.Button btn_mypage;          //마이페이지 버튼 id
    @FXML
    private Text alarm_text;          //"알림" 텍스트
    @FXML
    private ListView<String> notificationList;     //알림 리스트

    private Font FONT; // 커스텀 폰트


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

        // PasswordField에 포커스가 변경될 때마다 폰트 재적용
        alarm_text.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                alarm_text.setFont(FONT);
            }
        });

        notificationList.getItems().setAll(DataStore.getComments());

        // 항목을 두 번 클릭 시 PostContent 화면으로 이동
        notificationList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedComment = notificationList.getSelectionModel().getSelectedItem();
                switchToPostContent(selectedComment);
            }
        });

        // 알림 리스트에 저장된 댓글 불러오기
        notificationList.getItems().setAll(DataStore.getComments());

        // 항목을 두 번 클릭 시 PostContent 화면으로 이동
        notificationList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedComment = notificationList.getSelectionModel().getSelectedItem();
                if (selectedComment != null) {
                    switchToPostContent(selectedComment);
                }
            }
        });

    }

    // PostContent 화면으로 전환
    private void switchToPostContent(String comment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Postcontent.fxml"));
            Parent screen = loader.load();

            // PostcontentController로 선택된 댓글 전달
            PostcontentController controller = loader.getController();
            controller.loadComment(comment);

            Stage stage = (Stage) notificationList.getScene().getWindow();
            stage.setScene(new Scene(screen));
        } catch (IOException e) {
            showAlert("오류", "화면을 불러올 수 없습니다.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onalarm_alButtonClick() {            //알림 버튼을 클릭 시 호출되는 메서드
        try {
            // 알림 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Alarm.fxml"));
            Parent alarmScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_alarm.getScene().getWindow();      //알림 버튼을 누를 시 새로운 스테이지 생성

            // 알림 화면으로 Scene 교체
            Scene alarmScene = new Scene(alarmScreen);
            stage.setScene(alarmScene);
            // 알림 화면을 새로운 Scene으로 생성하고 Stage에 설정
        } catch (IOException e) {                           // IOException 발생 시 예외 처리
            e.printStackTrace();                            // 에러 메시지를 출력
        }
    }

    @FXML
    private void onhome_alButtonClick() {            //홈 버튼을 클릭 시 호출되는 메서드
        try {
            // 홈 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Community.fxml"));
            Parent homeScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_home.getScene().getWindow();      //홈 버튼을 누를 시 새로운 스테이지 생성

            // 홈 화면으로 Scene 교체
            Scene homeScene = new Scene(homeScreen);
            stage.setScene(homeScene);
            // 홈 화면을 새로운 Scene으로 생성하고 Stage에 설정
        } catch (IOException e) {                           // IOException 발생 시 예외 처리
            e.printStackTrace();                            // 에러 메시지를 출력
        }
    }

    @FXML
    private void onmypage_alButtonClick() {            //마이페이지 버튼을 클릭 시 호출되는 메서드
        try {
            // 마이페이지 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Mypage.fxml"));
            Parent mypageScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_mypage.getScene().getWindow();      //마이페이지 버튼을 누를 시 새로운 스테이지 생성

            // 마이페이지 화면으로 Scene 교체
            Scene mypageScene = new Scene(mypageScreen);
            stage.setScene(mypageScene);
            // 마이페이지 화면을 새로운 Scene으로 생성하고 Stage에 설정
        } catch (IOException e) {                           // IOException 발생 시 예외 처리
            e.printStackTrace();                            // 에러 메시지를 출력
        }
    }

    // 경고창 표시 메서드
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        // 경고창에도 커스텀 폰트 적용
        alert.getDialogPane().setStyle("-fx-font-family: '" + FONT.getFamily() + "'; -fx-font-size: 14px;");
        alert.showAndWait();
    }

}
