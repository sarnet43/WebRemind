package com.example.webremind;

import javafx.collections.FXCollections;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunityController {

    @FXML
    private javafx.scene.control.Button btn_alarm;           // 알림 버튼 id

    @FXML
    private javafx.scene.control.Button btn_home;            // 홈 버튼 id

    @FXML
    private javafx.scene.control.Button btn_mypage;          // 마이페이지 버튼 id

    @FXML
    private javafx.scene.control.Button btn_add;             // 글쓰는 버튼 id

    @FXML
    private ListView<String> listView;

    private String loggedInUserName;

    // 사용자 이름을 커뮤니티 화면에 전달
    public void setLoggedInUserName(String userName) {
        this.loggedInUserName = userName;
        System.out.println("로그인한 사용자 이름: " + userName);
    }

    // 화면 초기화 시 호출되는 메서드
    public void initialize() {
        // MySQL에서 게시글 리스트를 가져와 ListView에 연결
        ObservableList<String> posts = fetchPostsFromDatabase();
        listView.setItems(posts); // ListView와 ObservableList를 동기화
    }

    // MySQL에서 게시글 제목만 가져와 ListView에 표시
    private ObservableList<String> fetchPostsFromDatabase() {
        ObservableList<String> posts = FXCollections.observableArrayList(); // ObservableList 생성
        String query = "SELECT title FROM posts ORDER BY id DESC"; // 게시글 제목만 가져오는 SQL 쿼리

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과를 ListView에 추가
            while (rs.next()) {
                String title = rs.getString("title");
                posts.add(title); // 게시글 제목만 ObservableList에 추가
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("오류", "게시글을 불러오는 데 실패했습니다.");
        }
        return posts;
    }

    // 알림 버튼 클릭 시 호출되는 메서드
    @FXML
    private void onalarm_comButtonClick() {
        try {
            // 알림 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Alarm.fxml"));
            Parent alarmScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_alarm.getScene().getWindow();      //알림 버튼을 누를 시 새로운 스테이지 생성

            // 알림 화면으로 Scene 교체
            Scene alarmScene = new Scene(alarmScreen);
            stage.setScene(alarmScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 홈 버튼 클릭 시 호출되는 메서드
    @FXML
    private void onhome_comButtonClick() {
        try {
            // 홈 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Community.fxml"));
            Parent homeScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_home.getScene().getWindow();

            // 홈 화면으로 Scene 교체
            Scene homeScene = new Scene(homeScreen);
            stage.setScene(homeScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 마이페이지 버튼 클릭 시 호출되는 메서드
    @FXML
    private void onmypage_comButtonClick() {
        try {
            // 마이페이지 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Mypage.fxml"));
            Parent mypageScreen = loader.load();

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_mypage.getScene().getWindow();

            // 마이페이지 화면으로 Scene 교체
            Scene mypageScene = new Scene(mypageScreen);
            stage.setScene(mypageScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 글쓰기 버튼 클릭 시 호출되는 메서드
    @FXML
    private void onadd_comButtonClick() {
        try {
            // 글쓰기 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Writing.fxml"));
            Parent writingScreen = loader.load();

            // WritingController에 사용자 이름 전달
            WritingController writingController = loader.getController();
            writingController.setLoggedInUserName(loggedInUserName); // 사용자 이름 전달

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_add.getScene().getWindow();
            stage.setScene(new Scene(writingScreen));  // 글쓰기 화면으로 전환
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("오류", "화면을 불러올 수 없습니다: ");
        }
    }

    // 알림 메시지를 표시하는 메서드
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
