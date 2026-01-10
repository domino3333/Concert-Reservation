package mvc.controller;

import mvc.model.Concert;
import mvc.model.Member;
import mvc.repository.ConcertDao;
import mvc.repository.MemberDao;
import mvc.service.ConcertService;
import mvc.service.ReservationService;
import mvc.view.ReserveDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class ReservationController {

    //TODO ReservationService가 스레드를 가지고 있기 때문에 절대 new로 생성 X, 싱글톤으로 하기
    // 스레드가 있는 함수를 쓸 때만 싱글톤으로 갖고 있으면 되나?
    ReservationService rs = ReservationService.getInstance();



    public Member returnMember(){
        return new MemberDao().returnLatestRegisterMember();
    }

    public List<Character> selectSeatIsAvailable(){
        return new ReservationService().selectSeatIsAvailable();
    }

    public void callReserve(){
        // 목록을 repo에서 가져와 보여주기

        new ReserveDisplay().displayConcertList(); // 디스플레이로 넘겨서목록 보여주는 코드
        Concert selectedConcert = new ReserveDisplay().selectConcertDisplay(); //선택한 걸 받아오고

        boolean isPossibleConcert = new ReserveDisplay().canReserve(selectedConcert);


        if (isPossibleConcert) {
            new ReserveDisplay().seatDisplay();
            String myRequestSeat= new ReserveDisplay().requestSeatNumber(); // 원하는 자리 입력
            new ReserveDisplay().reserveCountDown(); // 321 카운트다운
            // 스트링 두개 받기) 따로 만들기 싫어서 억지로 +로 이어붙여서 넘겨받고 토큰화
            StringTokenizer st = new StringTokenizer(myRequestSeat);
            String mySeat = st.nextToken().trim().toUpperCase();
            String myName = st.nextToken().trim();

            //손님입장, 나 입장
            Thread myThread = rs.myReservationThreadStart(mySeat,myName); // 내 스레드 시작
            List<Thread> customerThread = rs.customerReservationThreadStart(); // 손님 스레드 시작
            try {
                myThread.join(); // 내 스레드 기다려주기
                for(Thread t :customerThread){
                    t.join(); // 손님 스레드 기다려주기
                }

                //todo 내 콘서트 예매 정보 reservation 테이블에 넣기
                new ReservationService().insertMyReservationInfo(mySeat,myName,selectedConcert);


                new ReserveDisplay().deadLineDisplay();
                Thread.sleep(1000); // 메시지 출력 후 1초 대기(사용자가 읽을 시간)

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public int checkAge(Concert concert) {
        return new ReservationService().checkAgeBeforeReserve(concert);
    }
}
