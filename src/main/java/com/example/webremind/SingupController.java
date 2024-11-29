package com.example.webremind;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SingupController {

    @FXML
    private javafx.scene.control.Button btn_next;          //다음 버튼 id

    @FXML
    private void onnextButtonClick() {            //다음 버튼을 클릭 시 호출되는 메서드
        try {
            // 커뮤니티 화면 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Community.fxml"));
            Parent communityScreen = loader.load();            // FXML 파일을 로드하여 Parent 변환

            // 현재 Stage 가져오기
            Stage stage = (Stage) btn_next.getScene().getWindow();      //다음 버튼을 누를 시 새로운 스테이지 생성

            // 커뮤니티 화면으로 Scene 교체
            Scene communityScene = new Scene(communityScreen);
            stage.setScene(communityScene);
            // 커뮤니티 화면을 새로운 Scene으로 생성하고 Stage에 설정
        } catch (IOException e) {                           // IOException 발생 시 예외 처리
            e.printStackTrace();                            // 에러 메시지를 출력
        }
    }
}
