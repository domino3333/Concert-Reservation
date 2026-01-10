package mvc.controller;

import mvc.model.Concert;
import mvc.service.ConcertService;
import mvc.view.ConcertDisplay;

import java.util.List;

public class ConcertController {

    public void insertConcert() {
        if (new ConcertService().insertConcert()>0) {
            new ConcertDisplay().resultMessage("콘서트 Insert 성공");
        } else {
            new ConcertDisplay().resultMessage("콘서트 Insert 실패");
        }
    }

    public List<Concert> returnAllConcerts(){
        return new ConcertService().returnAllConcerts();
    }


    public List<Concert> searchConcert(String keyword) {
        return new ConcertService().searchConcert(keyword); // 검색된 애들 반환
    }
}
