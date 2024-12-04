package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

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

    // 기본 이미지 URL
    private static final String DEFAULT_IMAGE_PATH = "/image/default image.jpg";

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

    }
}