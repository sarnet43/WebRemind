package com.example.webremind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunityController {

    @FXML
    private javafx.scene.control.Button btn_alarm;           //알림 버튼 id
    @FXML
    private javafx.scene.control.Button btn_home;          //홈 버튼 id
    @FXML
    private javafx.scene.control.Button btn_mypage;          //마이페이지 버튼 id
    @FXML
    private javafx.scene.control.Button btn_add;           //글쓰는 버튼 id
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

    private ObservableList<String> fetchPostsFromDatabase() {
        ObservableList<String> posts = FXCollections.observableArrayList(); // ObservableList 생성
        String query = "SELECT title FROM posts"; // 게시글의 제목만 가져오는 SQL 쿼리

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과를 ListView에 추가
            while (rs.next()) {
                String title = rs.getString("title");
                posts.add(title); // 게시글 제목을 ObservableList에 추가
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
        // 알림 화면 전환 코드
    }

    // 홈 버튼 클릭 시 호출되는 메서드
    @FXML
    private void onhome_comButtonClick() {
        // 홈 화면 전환 코드
    }

    // 마이페이지 버튼 클릭 시 호출되는 메서드
    @FXML
    private void onmypage_comButtonClick() {
        // 마이페이지 화면 전환 코드
    }

    // 글쓰기 버튼 클릭 시 호출되는 메서드
    @FXML
    private void onadd_comButtonClick() {
        // 글쓰기 화면 전환 코드
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
