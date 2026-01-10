package mvc.service;

import mvc.common.JDBCTemplate;
import mvc.model.BalladConcert;
import mvc.model.Concert;
import mvc.model.DanceConcert;
import mvc.model.genre.ConcertGenre;
import mvc.repository.ConcertDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ConcertService {


    public List<Concert> returnAllConcerts(){
        Connection con = JDBCTemplate.getConnection();

        return new ConcertDao().returnAllOfConcerts(con);
    }

    public int insertConcert() {

        Connection con = JDBCTemplate.getConnection();


        int rowCountValue = 0; //

        List<Concert> concertList = new ArrayList<>();

        concertList.add(new DanceConcert("케이팝 윈터 페스타", ConcertGenre.DANCE, 0));
        concertList.add(new DanceConcert("스트릿 댄스 나이트", ConcertGenre.DANCE, 12));
        concertList.add(new DanceConcert("힙합 그루브 라이브", ConcertGenre.DANCE, 15));
        concertList.add(new DanceConcert("글로벌 댄스 크루 쇼", ConcertGenre.DANCE, 0));
        concertList.add(new BalladConcert("윈터 발라드 콘서트", ConcertGenre.BALLAD, 0));
        concertList.add(new BalladConcert("이모셔널 보이스 라이브", ConcertGenre.BALLAD, 12));
        concertList.add(new BalladConcert("미드나잇 발라드 스테이지", ConcertGenre.BALLAD, 15));
        concertList.add(new BalladConcert("어덜트 발라드 나이트", ConcertGenre.BALLAD, 19));
        concertList.add(new DanceConcert("이디엠 댄스 파티", ConcertGenre.DANCE, 19));


        // 인서트 실패시 false 반환
        for (Concert concert : concertList){
            int rowCount = new ConcertDao().insertConcert(con,concert);
            if(rowCount>0){
                rowCountValue += rowCount;
                JDBCTemplate.commit(con);
            }else{
                JDBCTemplate.rollback(con);

            }
        }

        JDBCTemplate.close(con);
        return rowCountValue;
    }


    public List<Concert> searchConcert(String keyword) {
        Connection con = JDBCTemplate.getConnection();
        List<Concert> tempList = new ArrayList<>();

        for (Concert concert : new ConcertDao().returnAllOfConcerts(con)) {
            if(concert.getName().contains(keyword)) {
                tempList.add(concert);
            }
        }

        JDBCTemplate.close(con);

        return tempList;

    }
}
