package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
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
    private TextArea answerArea; // 댓글 입력 필드
    @FXML
    private VBox commentsBox; // 댓글 표시 상자
    @FXML
    private ScrollPane commentsScrollPane; // 댓글 스크롤 창

    private Font FONT;
    private int userId;
    private int postId;  // 동적으로 설정될 postId
    private String loggedInUserName;

    // 로그인한 사용자 이름 설정
    public void setLoggedInUserName(String userName) {
        this.loggedInUserName = userName;
        System.out.println("로그인한 사용자: " + loggedInUserName);
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

        // 컴포넌트에 폰트 적용
        titleArea.setFont(FONT);
        contentArea.setFont(FONT);
        answerArea.setFont(FONT);
        btn_storage.setFont(Font.font(FONT.getFamily(), 14));
    }

    // 알림 화면으로 이동
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

    // 홈 화면으로 이동
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

    // 마이페이지로 이동
    @FXML
    private void onmypage_poButtonClick () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Mypage.fxml"));
            Parent mypage_myScreen = loader.load();
            Stage stage = (Stage) btn_mypage.getScene().getWindow();
            stage.setScene(new Scene(mypage_myScreen));
        } catch (IOException e) {
            e.printStackTrace();
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

        // 댓글을 작성한 후 알림도 생성
        addCommentWithNotification(postId, userName, content);
    }

    // 댓글을 DB에 저장
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

    // 댓글을 화면에 추가
    private void addCommentToView(String userName, String content) {
        Text comment = new Text(userName + ": " + content);
        comment.setFont(FONT);
        commentsBox.getChildren().add(comment);

        // 댓글 추가 후 스크롤을 맨 아래로 이동
        commentsScrollPane.layout();
        commentsScrollPane.setVvalue(1.0); // 맨 아래로 이동
    }

    // 알림을 생성하는 메서드
    private void addCommentWithNotification(int postId, String userName, String content) {
        // 게시글 작성자 정보 가져오기 (작성자의 userid를 반환해야 함)
        String authorUserId = getPostAuthor(postId);  // 수정된 메서드로 수정 필요

        if (authorUserId != null && !authorUserId.equals(userName)) {
            // 알림 생성
            String query = "INSERT INTO notifications (post_id, recipient_user_id, message, created_at) VALUES (?, ?, ?, ?)";
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                String message = userName + "님이 댓글을 달았습니다: " + content;
                statement.setInt(1, postId);
                statement.setString(2, authorUserId);  // 실제 userid를 사용
                statement.setString(3, message);
                statement.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // 알림 생성 날짜
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();  // 오류 메시지 출력
                showAlert("알림 오류", "알림을 저장하는 중 오류가 발생했습니다.");
            }
        }
    }


    // 게시글 작성자 가져오기
    private String getPostAuthor(int postId) {
        String query = "SELECT user_id FROM posts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 댓글 목록 로드
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

    // 경고창 표시 메서드
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
