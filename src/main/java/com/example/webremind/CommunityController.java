package com.example.webremind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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

    private Font FONT;

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
            Parent writingScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_add.getScene().getWindow();      //글쓰기 버튼을 누를 시 새로운 스테이지 생성

            // 글쓰기 화면으로 Scene 교체
            Scene writingScene = new Scene(writingScreen);
            stage.setScene(writingScene);
            // 글쓰기 화면을 새로운 Scene으로 생성하고 Stage에 설정
        } catch (IOException e) {                           // IOException 발생 시 예외 처리
            e.printStackTrace();                            // 에러 메시지를 출력
        }
    }

    // 화면 초기화 시 호출되는 메서드
    @FXML
    public void initialize() {

        FONT = Font.loadFont(getClass().getResourceAsStream("/font/SB 어그로 B.ttf"), 16);
        if (FONT == null) {
            System.out.println("폰트 로드 실패: 기본 폰트를 사용합니다.");
            FONT = Font.font("System", 16); // 기본 폰트로 대체
        }

        // 컴포넌트에 폰트 적용
        listView.setStyle("-fx-font-family: '" + FONT.getFamily() + "'; -fx-font-size: 14px;");

        // DataStore에서 저장된 제목과 날짜를 가져와 ListView에 표시
        updatePostList();

        // 더블 클릭 이벤트 추가
        listView.setOnMouseClicked(this::onListViewItemDoubleClick);
    }

    // 게시글 제목과 날짜를 ListView에 업데이트
    private void updatePostList() {
        listView.getItems().clear(); // 기존 항목을 지운다.

        // 제목과 날짜 리스트를 가져옴
        List<String> titles = DataStore.getTitles();
        List<String> dates = DataStore.getDates();

        // 제목과 날짜를 합쳐서 리스트에 추가
        for (int i = 0; i < titles.size(); i++) {
            String postItem = titles.get(i) + " - " + dates.get(i); // 제목과 날짜 결합
            listView.getItems().add(postItem); // ListView에 추가
        }
    }

    // ListView 아이템 더블 클릭 처리
    private void onListViewItemDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // 더블 클릭 감지
            String selectedPost = listView.getSelectionModel().getSelectedItem();
            if (selectedPost != null) {
                // Postcontent.fxml로 이동하며 데이터 전달
                switchToScene("/fxml/Postcontent.fxml", selectedPost);
            }
        }
    }

    // 화면 전환 메서드
    private void switchToScene(String fxmlFile, String postContent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent screen = loader.load();

            // PostcontentController에 데이터 전달
            if (postContent != null) {
                PostcontentController controller = loader.getController();
                controller.setPostContent(postContent);
            }

            Stage stage = (Stage) btn_home.getScene().getWindow();
            stage.setScene(new Scene(screen));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}