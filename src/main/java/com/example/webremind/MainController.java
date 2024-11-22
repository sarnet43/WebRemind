package com.example.webremind;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class MainController {

    @FXML
    private Label welcomeText;

    @FXML
    private ListView<String> notificationList;

    // 알림 데이터를 저장할 리스트
    private final ObservableList<String> notifications = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // ListView와 ObservableList를 연결
        notificationList.setItems(notifications);

        // 초기 알림 예시 추가
        addNotification("guest님께서 댓글을 다셨습니다.\n커뮤니티 - 이 웹툰 아시는 분..");
        addNotification("user123님께서 댓글을 다셨습니다.\n커뮤니티 - 좋아하는 음식에 대해..");
    }

    // 새로운 알림 추가 메서드
    public void addNotification(String message) {
        notifications.add(message);
    }
}
