package com.example.webremind;

import java.sql.*;

class DBConnectionEx {
    public static Connection getConnection() {
        Connection conn = null;
        String username = "root"; // DB접속시 아이디
        String password = "111111"; // DB접속시 비밀번호
        String url = "jdbc:mysql://localhost:3306/webreminddb";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("DB 연결 완료");
        } catch (ClassNotFoundException e) { //예외 처리
            System.out.println("드라이버 오류");
        } catch (SQLException e) {
            System.out.println("데이터베이스 접속 정보 오류");
        }
        return conn;
    }
}
