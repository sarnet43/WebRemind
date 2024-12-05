package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PostcontentController {

    @FXML
    private javafx.scene.control.Button btn_alarm;
    @FXML
    private javafx.scene.control.Button btn_home;
    @FXML
    private javafx.scene.control.Button btn_mypage;
    @FXML
    private javafx.scene.control.Button btn_storage;
    @FXML
    private TextArea titleArea;
    @FXML
    private TextArea contentArea;
    @FXML
    private TextArea answerArea;
    @FXML
    private VBox commentsBox;
    @FXML
    private ScrollPane commentsScrollPane;

    private Font FONT;
    private int userId;
    private int postId;  // 동적으로 설정될 postId
    private String loggedInUserName;

    // 로그인한 사용자 이름 설정
    public void setLoggedInUserName(String userName) {
        this.loggedInUserName = userName;
        System.out.println(loggedInUserName);
    }

    // 게시글 ID 설정 (CommunityController에서 postId를 설정하는 메서드)
    public void setPostId(int postId) {
        this.postId = postId;
        fetchPostFromDatabase(postId);  // 게시글을 로드하는 메서드 호출
    }

    @FXML
    private void initialize() {
        FONT = Font.loadFont(getClass().getResourceAsStream("/font/SB 어그로 B.ttf"), 16);
        if (FONT == null) {
            FONT = Font.font("System", 16);
        }

        titleArea.setFont(FONT);
        contentArea.setFont(FONT);
        answerArea.setFont(FONT);

        //loadComments(); // 댓글 목록 초기화
    }

    @FXML
    private void onalarm_poButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Alarm.fxml"));
            Parent alarmScreen = loader.load();
            Stage stage = (Stage) btn_alarm.getScene().getWindow();
            stage.setScene(new Scene(alarmScreen));
        } catch (IOException e) {
            showAlert("오류", "알림 화면을 로드할 수 없습니다.");
        }
    }

    @FXML
    private void onhome_poButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Community.fxml"));
            Parent homeScreen = loader.load();
            Stage stage = (Stage) btn_home.getScene().getWindow();
            stage.setScene(new Scene(homeScreen));
        } catch (IOException e) {
            showAlert("오류", "홈 화면을 로드할 수 없습니다.");
        }
    }

    @FXML
    private void onmypage_poButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Mypage.fxml"));
            Parent mypageScreen = loader.load();
            Stage stage = (Stage) btn_mypage.getScene().getWindow();
            stage.setScene(new Scene(mypageScreen));
        } catch (IOException e) {
            showAlert("오류", "마이페이지 화면을 로드할 수 없습니다.");
        }
    }

    // 댓글 저장 및 화면에 추가
    @FXML
    private void onstorage_poButtonClick() {
        String content = answerArea.getText().trim();

        if (content.isEmpty()) {
            showAlert("오류", "댓글 내용을 입력해주세요.");
            return;
        }

        // 댓글을 DB에 저장하고 화면에 추가
        saveAndAddComment(loggedInUserName, content);

        answerArea.clear();  // 댓글 입력 필드 초기화
    }

    private void saveAndAddComment(String userName, String content) {
        saveCommentToDatabase(userName, content);  // 댓글 DB 저장
        addCommentToView(userName, content);      // 화면에 댓글 추가
    }

    private void saveCommentToDatabase(String userName, String content) {
        String query = "INSERT INTO comments (post_id, user_name, content, comment_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, postId);  // 동적으로 받아온 postId 사용
            pstmt.setString(2, userName);
            pstmt.setString(3, content);
            pstmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));  // 현재 날짜
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("DB 오류", "댓글 저장에 실패했습니다.");
        }
    }

    private void addCommentToView(String userName, String content) {
        Text comment = new Text(userName + ": " + content);
        comment.setFont(FONT);
        commentsBox.getChildren().add(comment);

        // 댓글 추가 후 스크롤을 맨 아래로 이동
        commentsScrollPane.layout();
        commentsScrollPane.setVvalue(1.0); // 맨 아래로 이동
    }


    private void loadComments() {
        commentsBox.getChildren().clear(); // 이전 댓글 목록 초기화
        String query = "SELECT user_name, content FROM comments WHERE post_id = ? ORDER BY comment_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String userName = rs.getString("user_name");
                    String content = rs.getString("content");
                    addCommentToView(userName, content);
                }
            }

        } catch (SQLException e) {
            showAlert("DB 오류", "댓글 목록을 불러올 수 없습니다.");
        }
    }


    // 게시글 내용 불러오기
    public void fetchPostFromDatabase(int postId) {
        String query = "SELECT * FROM posts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    String content = rs.getString("content");

                    titleArea.setText(title);
                    contentArea.setText(content);
                }
            }
        } catch (SQLException e) {
            showAlert("DB 오류", "게시글을 불러올 수 없습니다.");
        }

        titleArea.setEditable(false);
        contentArea.setEditable(false);

        // 댓글 로드 메서드 추가
        loadComments();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
