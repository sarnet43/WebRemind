package com.example.webremind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    // Singleton 패턴으로 UserData 관리
    private static UserData userDataInstance;

    private static final ObservableList<String> posts = FXCollections.observableArrayList();

    // 데이터를 읽기 위한 메서드
    private static List<String> titles = new ArrayList<>();
    private static List<String> dates = new ArrayList<>();
    private static List<String> contents = new ArrayList<>();

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
        contents.add(content);
    }

    // 제목 리스트를 반환하는 메서드
    public static List<String> getTitles() {
        return titles; // DataStore에서 제목 리스트를 반환
    }

    // 날짜 리스트를 반환하는 메서드
    public static List<String> getDates() {
        return dates; // DataStore에서 날짜 리스트를 반환
    }

    // UserData 관련 메서드
    public static UserData getUserDataInstance() {
        if (userDataInstance == null) {
            userDataInstance = new UserData();
        }
        return userDataInstance;
    }

    public static class UserData {
        private String username;        // 유저 닉네임

        private UserData() {}

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
