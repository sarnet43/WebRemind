package com.example.webremind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    // Singleton 패턴으로 UserData 관리
    private static UserData userDataInstance;

    private static final ObservableList<String> contents = FXCollections.observableArrayList();  // ObservableList
    private static final ObservableList<String> comments = FXCollections.observableArrayList();  // comments를 ObservableList로 설정

    // 데이터를 읽기 위한 메서드
    private static final List<String> titles = new ArrayList<>(); // 일반 List로 관리
    private static final List<String> dates = new ArrayList<>();  // 일반 List로 관리

    // 댓글 저장
    public static void addComment(String comment) {
        comments.add(comment);
    }

    // 저장된 댓글 가져오기
    public static List<String> getComments() {
        return new ArrayList<>(comments); // 복사본 반환
    }

    // 게시글 제목 저장
    public static void addTitle(String title) {
        titles.add(title);
    }

    // 게시글 날짜 저장
    public static void addDate(String date) {
        dates.add(date);
    }

    // 게시글 내용 저장
    public static void addContent(String content) {
        contents.add(content); // ObservableList에 추가
    }

    // 제목 리스트를 반환하는 메서드
    public static List<String> getTitles() {
        return new ArrayList<>(titles); // 복사본 반환
    }

    // 날짜 리스트를 반환하는 메서드
    public static List<String> getDates() {
        return new ArrayList<>(dates); // 복사본 반환
    }

    // 게시글 내용 리스트를 반환하는 메서드
    public static List<String> getContents() {
        return new ArrayList<>(contents); // 복사본 반환
    }

    // UserData 관련 메서드
    public static UserData getUserDataInstance() {
        if (userDataInstance == null) {
            userDataInstance = new UserData();
        }
        return userDataInstance;
    }

    public static class UserData {
        private String username; // 유저 닉네임

        private UserData() {}

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
