package com.example.webremind;

public class CommentService {

    private final MainController controller;

    public CommentService(MainController controller) {
        this.controller = controller;
    }

    // 댓글이 달릴 때 호출되는 메서드
    public void onNewComment(String commenter, String postTitle) {
        String notification = commenter + "님께서 댓글을 다셨습니다.\n커뮤니티 - " + postTitle;
        controller.addNotification(notification);  // MainController의 알림 추가 메서드 호출
    }
}
