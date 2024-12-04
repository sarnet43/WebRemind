package com.example.webremind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStore {
    // Singleton 패턴으로 UserData 관리
    private static UserData userDataInstance;

    private static final ObservableList<String> posts = FXCollections.observableArrayList();

    // 데이터를 읽기 위한 메서드
    public static ObservableList<String> getPosts() {
        return posts;
    }

    // 데이터를 추가하기 위한 메서드
    public static void addPost(String post) {
        posts.add(post); // 새로운 게시글을 리스트에 추가
    }

    // UserData 관련 메서드
    public static UserData getUserDataInstance() {
        if (userDataInstance == null) {
            userDataInstance = new UserData();
        }
        return userDataInstance;
    }

    public static class UserData {
        private String username;

        private UserData() {}

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}