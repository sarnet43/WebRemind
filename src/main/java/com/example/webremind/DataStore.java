package com.example.webremind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStore {
    // ObservableList를 사용해 ListView와 데이터 동기화 가능
    private static final ObservableList<String> posts = FXCollections.observableArrayList();

    // 데이터를 읽기 위한 메서드
    public static ObservableList<String> getPosts() {
        return posts;
    }

    // 데이터를 추가하기 위한 메서드
    public static void addPost(String post) {
        posts.add(post); // 새로운 게시글을 리스트에 추가
    }
}
