package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PostcontentController {
    @FXML
    private javafx.scene.control.Button btn_alarm; // 알림 버튼 id
    @FXML
    private javafx.scene.control.Button btn_home; // 홈 버튼 id
    @FXML
    private javafx.scene.control.Button btn_mypage; // 마이페이지 버튼 id
    @FXML
    private javafx.scene.control.Button btn_storage; //저장 버튼 id
    @FXML
    private TextArea titleArea; // 제목 입력 필드
    @FXML
    private TextArea contentArea; // 내용
    @FXML
    private ImageView userimage; // 유저의 이미지
    @FXML
    private TextArea answerArea;

    private Font FONT; // 커스텀 폰트

    // 기본 이미지 URL
    private static final String DEFAULT_IMAGE_PATH = "/image/default image.jpg";

    @FXML
    private void initialize() {
        FONT = Font.loadFont(getClass().getResourceAsStream("/font/SB 어그로 B.ttf"), 16);
        if (FONT == null) {
            System.out.println("폰트 로드 실패: 기본 폰트를 사용합니다.");
            FONT = Font.font("System", 16); // 기본 폰트로 대체
        }

        // 컴포넌트에 폰트 적용
        titleArea.setFont(FONT);
        contentArea.setFont(FONT);
        answerArea.setFont(FONT);
        btn_storage.setFont(Font.font(FONT.getFamily(), 14));

        // 버튼 초기화 시 스타일 적용
        btn_storage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                btn_storage.setStyle("-fx-font-family: '" + FONT.getFamily() + "';");
            }
        });

        // PasswordField에 포커스가 변경될 때마다 폰트 재적용
        titleArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                titleArea.setFont(FONT);
            }
        });

        // TextField에 포커스가 변경될 때마다 폰트 재적용
        contentArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                contentArea.setFont(FONT);
            }
        });

        // TextField에 포커스가 변경될 때마다 폰트 재적용
        contentArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                contentArea.setFont(FONT);
            }
        });
    }

        @FXML
        private void onalarm_poButtonClick() {            //알림 버튼을 클릭 시 호출되는 메서드
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
        private void onhome_poButtonClick () {            //홈 버튼을 클릭 시 호출되는 메서드
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
        private void onmypage_poButtonClick () {            //마이페이지 버튼을 클릭 시 호출되는 메서드
            try {
                // 마이페이지 화면 로드
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Mypage.fxml"));
                Parent mypage_myScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

                // 현재 Stage 가져오기
                Stage stage = (Stage) btn_mypage.getScene().getWindow();      //마이페이지 버튼을 누를 시 새로운 스테이지 생성

                // 마이페이지 화면으로 Scene 교체
                Scene mypage_myScene = new Scene(mypage_myScreen);
                stage.setScene(mypage_myScene);
                // 마이페이지 화면을 새로운 Scene으로 생성하고 Stage에 설정
            } catch (IOException e) {                           // IOException 발생 시 예외 처리
                e.printStackTrace();                            // 에러 메시지를 출력
            }
        }

    @FXML
    private void onstorage_poButtonClick() {
        String comment = answerArea.getText().trim(); // 댓글 가져오기

        if (comment.isEmpty()) {
            showAlert("오류", "댓글을 입력해주세요.");
            return;
        }

        // 댓글 저장
        DataStore.addComment(comment);
    }

    // 게시글 데이터를 설정하는 메서드
    public void setPostContent(String postContent) {
        // 전달받은 데이터를 파싱하여 제목과 내용을 설정
        String[] parts = postContent.split("\t", 2);
        if (parts.length >= 2) {
            titleArea.setText(parts[0].trim()); // 제목 설정
            contentArea.setText(parts[1].trim()); // 내용 설정
        } else {
            titleArea.setText(postContent.trim());
            contentArea.setText("내용 없음");
        }

        // 제목과 내용 필드를 읽기 전용으로 설정
        titleArea.setEditable(false);
        contentArea.setEditable(false);
    }

    // 경고창 표시 메서드
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        // 경고창에도 커스텀 폰트 적용
        alert.getDialogPane().setStyle("-fx-font-family: '" + FONT.getFamily() + "'; -fx-font-size: 14px;");
        alert.showAndWait();
    }

    public void loadComment(String comment) {
        contentArea.setText(comment);
    }
}