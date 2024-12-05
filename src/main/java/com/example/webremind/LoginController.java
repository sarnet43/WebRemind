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

public class LoginController {

    @FXML
    private TextField userid; // 아이디
    @FXML
    private PasswordField password; // 유저 닉네임
    @FXML
    private javafx.scene.control.Button btn_sing;           //회원가입 버튼 id
    @FXML
    private javafx.scene.control.Button btn_login;          //로그인 버튼 id

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
        password.setFont(FONT);
        btn_sing.setFont(Font.font(FONT.getFamily(), 14));
        btn_login.setFont(Font.font(FONT.getFamily(), 14));

        // 버튼 초기화 시 스타일 적용
        btn_sing.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                btn_sing.setStyle("-fx-font-family: '" + FONT.getFamily() + "';");
                btn_login.setStyle("-fx-font-family: '" + FONT.getFamily() + "';");
            }
        });

        // PasswordField에 포커스가 변경될 때마다 폰트 재적용
        password.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                password.setFont(FONT);
            }
        });

        // TextField에 포커스가 변경될 때마다 폰트 재적용
        userid.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // 포커스가 잡힐 때
                userid.setFont(FONT);
            }
        });

        //id 길이 조절
        userid.textProperty().addListener((observable, oldValue, newValue) -> {
            // 텍스트 길이가 10자 이상이면, 글자를 더 이상 추가할 수 없도록 함
            if (newValue.length() > 10) {
                userid.setText(oldValue); // 이전 값으로 되돌림
            }
        });
    }

    @FXML
    private void onSignupButtonClick() {            //회원가입 버튼을 클릭 시 호출되는 메서드

        try {
            // 회원가입 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SingUp.fxml"));
            Parent signupScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_sing.getScene().getWindow();      //회원가입 버튼을 누를 시 새로운 스테이지 생성

            // 회원가입 화면으로 Scene 교체
            Scene signupScene = new Scene(signupScreen);
            stage.setScene(signupScene);
            // 회원가입 화면을 새로운 Scene으로 생성하고 Stage에 설정
        } catch (IOException e) {                           // IOException 발생 시 예외 처리
            e.printStackTrace();                            // 에러 메시지를 출력
        }
    }

    @FXML
    private void onLoginButtonClick(){             //로그인 버튼을 클릭 시 호출되는 메서드

        String user_id = userid.getText().trim(); // 닉네임을 가져옴
        String uswer_password = password.getText().trim(); // 비밀번호를 가져옴

        // 제목과 내용이 모두 비어있을 경우 경고 표시
        if (user_id.isEmpty() || uswer_password.isEmpty()) {
            showAlert("오류", "모두 입력하였는 지 확인하세요.");
            return;
        }

        try {
            // 로그인 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Community.fxml"));
            Parent loginScreen = loader.load();             // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_login.getScene().getWindow();      //로그인 버튼을 누를 시 새로운 스테이지 생성

            // 로그인 화면으로 Scene 교체
            Scene loginScene = new Scene(loginScreen);
            stage.setScene(loginScene);
            // 로그인 화면을 새로운 Scene으로 생성하고 Stage에 설정
        } catch (IOException e) {                   // IOException 발생 시 예외 처리
            e.printStackTrace();                    // 에러 메시지를 출력
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
