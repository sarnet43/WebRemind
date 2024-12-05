package com.example.webremind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    private String loggedInUserName;

    // 사용자 이름을 커뮤니티 화면에 전달
    public void setLoggedInUserName(String userName) {
        this.loggedInUserName = userName;
        System.out.println("로그인한 사용자 이름: " + userName);
    }

    // 화면 초기화 시 호출되는 메서드
    public void initialize() {
        // MySQL에서 게시글 리스트를 가져와 ListView에 연결
        ObservableList<Post> posts = fetchPostsFromDatabase();

        // ListView에 표시할 텍스트 설정
        ObservableList<String> displayTexts = FXCollections.observableArrayList();
        for (Post post : posts) {
            displayTexts.add(post.getDisplayText());
        }
        listView.setItems(displayTexts);
        // 더블 클릭 이벤트 추가
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    Post selectedPost = posts.get(selectedIndex);
                    switchToScene("/fxml/Postcontent.fxml", selectedPost.getId());
                }
            }
        });
    }

    // MySQL에서 게시글을 가져오는 메서드
    private ObservableList<Post> fetchPostsFromDatabase() {
        ObservableList<Post> posts = FXCollections.observableArrayList();
        String query = "SELECT id, title, post_date, user_name FROM posts ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String postDate = rs.getString("post_date").substring(0, 10); // 날짜만 표시
                String userName = rs.getString("user_name");

                posts.add(new Post(id, title, userName, postDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("오류", "게시글을 불러오는 데 실패했습니다.");
        }
        return posts;
    }


    // 알림 버튼 클릭 시 호출되는 메서드
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
    private void onadd_comButtonClick() {            //글쓰기 버튼을 클릭 시 호출되는 메서드
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

    // ListView 아이템 더블 클릭 처리
    private void onListViewItemDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // 더블 클릭 감지
            String selectedPost = listView.getSelectionModel().getSelectedItem();
            if (selectedPost != null) {
                // 선택된 아이템에서 ID 추출
                int id = Integer.parseInt(selectedPost.split("\t")[0]);
                // 화면 전환 시 ID 전달
                switchToScene("/fxml/Postcontent.fxml", id);
            }
        }
    }


    private void switchToScene(String fxmlFile, int postId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent screen = loader.load();

            // PostcontentController에 postId 전달
            PostcontentController controller = loader.getController();
            controller.fetchPostFromDatabase(postId);

            Stage stage = (Stage) btn_home.getScene().getWindow();
            stage.setScene(new Scene(screen));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}