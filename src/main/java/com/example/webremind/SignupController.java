package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {

    @FXML
    private TextField username; // 유저 닉네임
    @FXML
    private TextField userid; // 아이디 필드
    @FXML
    private PasswordField passwd;   //유저 비밀번호
    @FXML
    private PasswordField checkPasswd;   //유저 비밀번호 확인
    @FXML
    private javafx.scene.control.Button btn_next;          //다음 버튼 id

    private Font FONT; // 커스텀 폰트

    @FXML
    private void initialize() {

        FONT = Font.loadFont(getClass().getResourceAsStream("/font/SB 어그로 B.ttf"), 16);
        if (FONT == null) {
            System.out.println("폰트 로드 실패: 기본 폰트를 사용합니다.");
            FONT = Font.font("System", 16); // 기본 폰트로 대체
        }

        // 컴포넌트에 폰트 적용
        userid.setFont(FONT);
        username.setFont(FONT);
        passwd.setFont(FONT);
        checkPasswd.setFont(FONT);
        btn_next.setFont(Font.font(FONT.getFamily(), 14));

        // 버튼 초기화 시 스타일 적용
        btn_next.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                btn_next.setStyle("-fx-font-family: '" + FONT.getFamily() + "';");
            }
        });

        // 포커스가 변경될 때마다 폰트 재적용
        passwd.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                passwd.setFont(FONT);
            }
        });

        // 포커스가 변경될 때마다 폰트 재적용
        checkPasswd.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                checkPasswd.setFont(FONT);
            }
        });

        // TextField에 포커스가 변경될 때마다 폰트 재적용
        userid.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                userid.setFont(FONT);
            }
        });

        // username textProperty에 Listener 추가
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            // 텍스트 길이가 10자 이상이면, 글자를 더 이상 추가할 수 없도록 함
            if (newValue.length() > 10) {
                username.setText(oldValue); // 이전 값으로 되돌림
            }
        });

        userid.textProperty().addListener((observable, oldValue, newValue) -> {
            // 텍스트 길이가 10자 이상이면, 글자를 더 이상 추가할 수 없도록 함
            if (newValue.length() > 10) {
                userid.setText(oldValue); // 이전 값으로 되돌림
            }
        });
    }

    @FXML
    private void onnextButtonClick() {

        String user_name = username.getText().trim(); // 닉네임을 가져옴
        String user_id = userid.getText().trim(); // id를 가져옴
        String user_passwd = passwd.getText().trim(); // 비밀번호를 가져옴
        String user_checkPasswd = checkPasswd.getText().trim(); // 비밀번호 확인을 가져옴

        // 제목과 내용이 모두 비어있을 경우 경고 표시
        if (user_name.isEmpty() || user_id.isEmpty() || user_passwd.isEmpty() || user_checkPasswd.isEmpty()) {
            showAlert("오류", "모두 입력해주세요.");
            return;
        }

        // 닉네임을 DataStore에 저장
        DataStore.getUserDataInstance().setUsername(user_name);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent communityScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_next.getScene().getWindow();      //다음 버튼을 누를 시 새로운 스테이지 생성

            Scene communityScene = new Scene(communityScreen);
            stage.setScene(communityScene);

        } catch (IOException e) {                           // IOException 발생 시 예외 처리
            e.printStackTrace();                            // 에러 메시지를 출력
        }
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
}