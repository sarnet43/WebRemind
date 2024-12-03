package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MypageController {

    @FXML
    private javafx.scene.control.Button btn_alarm; // 알림 버튼 id
    @FXML
    private javafx.scene.control.Button btn_home; // 홈 버튼 id
    @FXML
    private javafx.scene.control.Button btn_mypage; // 마이페이지 버튼 id
    @FXML
    private Button btn_namechange; // 닉네임 변경 버튼
    @FXML
    private Text usernameText; // 닉네임을 표시하는 텍스트
    @FXML
    private ImageView userimage; // 유저의 이미지

    // 기본 이미지 URL
    private static final String DEFAULT_IMAGE_PATH = "/image/default image.jpg";

    @FXML
    public void initialize() {
        // 기본 이미지로 설정
        Image defaultImage = new Image(getClass().getResource(DEFAULT_IMAGE_PATH).toString());
        userimage.setImage(defaultImage);

        // 닉네임을 화면에 표시
        usernameText.setText(DataStore.getUserDataInstance().getUsername());
    }


    @FXML
    private void onalarm_myButtonClick() {            //알림 버튼을 클릭 시 호출되는 메서드
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
    private void onhome_myButtonClick() {            //홈 버튼을 클릭 시 호출되는 메서드
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
    private void onmypage_myButtonClick() {            //마이페이지 버튼을 클릭 시 호출되는 메서드
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
    private void onnamechange_myButtonClick(){
        // 현재 닉네임 텍스트를 TextField로 변경
        String currentUsername = usernameText.getText();
        TextField usernameField = new TextField(currentUsername);
        usernameField.setLayoutX(usernameText.getLayoutX());
        usernameField.setLayoutY(usernameText.getLayoutY());

        // 닉네임 변경 버튼을 TextField가 있는 곳에 배치
        btn_namechange.setLayoutX(usernameField.getLayoutX() + usernameField.getWidth() + 10);
        btn_namechange.setText("변경");

        // 닉네임 변경 후 TextField 사라지기
        usernameField.setOnAction(event -> {
            String newUsername = usernameField.getText().trim();
            if (!newUsername.isEmpty()) {
                // 닉네임을 DataStore에 저장
                DataStore.getUserDataInstance().setUsername(newUsername);
                usernameText.setText(newUsername);
                usernameField.setVisible(false); // TextField 숨기기
            }
        });

        // 화면에 TextField 추가
        usernameText.getScene().getRoot().getChildrenUnmodifiable().add(usernameField);
    }

    @FXML
    private void userimageChangeButtonClick() {
        // FileChooser 생성
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("이미지 선택");
        fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("이미지 파일", "*.png", "*.jpg", "*.jpeg"));

        // 파일 탐색기 열기
        File selectedFile = fileChooser.showOpenDialog(userimage.getScene().getWindow());

        // 파일 선택 시 이미지를 업데이트
        if (selectedFile != null) {
            Image selectedImage = new Image(selectedFile.toURI().toString());
            userimage.setImage(selectedImage);
        }
    }

    private void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent screen = loader.load();
            Stage stage = (Stage) btn_alarm.getScene().getWindow();
            stage.setScene(new Scene(screen));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
