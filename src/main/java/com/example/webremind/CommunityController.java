package com.example.webremind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CommunityController {

    @FXML
    private javafx.scene.control.Button btn_alarm;           //알림 버튼 id

    @FXML
    private javafx.scene.control.Button btn_home;          //홈 버튼 id

    @FXML
    private javafx.scene.control.Button btn_mypage;          //마이페이지 버튼 id

    @FXML
    private  javafx.scene.control.Button btn_add;           //글쓰는 버튼 id

    @FXML
    private ListView<String> listView;

    @FXML
    private void onalarm_comButtonClick() {            //알림 버튼을 클릭 시 호출되는 메서드
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
    private void onhome_comButtonClick() {            //홈 버튼을 클릭 시 호출되는 메서드
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
    private void onmypage_comButtonClick() {            //마이페이지 버튼을 클릭 시 호출되는 메서드
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

    @FXML
    private void onadd_comButtonClick() {            // 글쓰기 버튼을 클릭 시 호출되는 메서드
        try {
            // 글쓰기 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Writing.fxml"));
            Parent writingScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // WritingController에 사용자 이름 전달
            WritingController writingController = loader.getController();
            writingController.setLoggedInUserName(loggedInUserName); // 커뮤니티 화면에서 전달된 이름을 사용

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_add.getScene().getWindow();
            stage.setScene(new Scene(writingScreen));  // 글쓰기 화면으로 전환
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("오류", "화면을 불러올 수 없습니다: ");
        }
    }


    // 화면 초기화 시 호출되는 메서드
    public void initialize() {
        // DataStore에서 게시글 리스트를 가져와 ListView에 연결
        ObservableList<String> posts = DataStore.getPosts();
        listView.setItems(posts); // ListView와 ObservableList를 동기화
    }

    private String loggedInUserName;

    public void setLoggedInUserName(String userName) {
        this.loggedInUserName = userName;
        System.out.println("로그인한 사용자 이름: " + userName);
        // 필요 시 화면에 사용자 이름 표시
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
