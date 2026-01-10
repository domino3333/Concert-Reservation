package mvc.controller;

import mvc.dto.ReservationDto;
import mvc.model.Concert;
import mvc.model.Member;
import mvc.service.ReservationService;
import mvc.view.ConcertDisplay;
import mvc.view.ReserveDisplay;

import java.util.List;
import java.util.StringTokenizer;


public class ReservationController {

    //TODO ReservationService가 스레드를 가지고 있기 때문에 절대 new로 생성 X, 싱글톤으로 하기
    // 스레드가 있는 함수를 쓸 때만 싱글톤으로 갖고 있으면 되나?
    ReservationService rs = ReservationService.getInstance();



    public Member returnMember(){
        return new ReservationService().returnLatestMember();
    }

    public List<Character> selectSeatIsAvailable(){
        return new ReservationService().selectSeatIsAvailable();
    }

    public void callReserve(){
        /*'나'라는 사용자는 하나의 콘서트만 예매 가능하다
        reservationTable의 member_id(fk)가 unique인 것을 이용하여
        현재 멤버가 이미 예매를 했다면 돌려보내는 과정이 필요*/
        if(new ReservationService().isAlreadyReserved()>0){
            new ReserveDisplay().printMessage("한 번에 하나의 콘서트만 예매할 수 있습니다.");
            return;
        }

        new ReserveDisplay().displayConcertList(); // 디스플레이로 넘겨서목록 보여주는 코드
        Concert selectedConcert = new ReserveDisplay().selectConcertDisplay(); //선택한 걸 받아오고

        boolean isPossibleConcert = new ReservationService().canReserve(selectedConcert);


        if (isPossibleConcert) {
            new ReserveDisplay().seatDisplay();
            String myRequestSeat = new ReserveDisplay().requestSeatNumber(); // 원하는 자리 입력
            new ReserveDisplay().reserveCountDown(); // 321 카운트다운
            // 스트링 두개 받기) 억지로 +로 이어붙여서 넘겨받고 토큰화
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
                int rowCount = new ReservationService().insertMyReservationInfo(mySeat,myName,selectedConcert);

                if(rowCount >0){
                    new ConcertDisplay().resultMessage("내 예매 정보가 저장되었습니다.");
                }else{
                    new ConcertDisplay().resultMessage("내 예매 정보 저장 실패");

                }

                new ReserveDisplay().deadLineDisplay();
                Thread.sleep(1000); // 메시지 출력 후 1초 대기(사용자가 읽을 시간)

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println();
        }

    }

    public void reservationHistory() {
        ReservationDto dto = new ReservationService().reservationHistory();
        new ConcertDisplay().viewReservationHistory(dto);

    }
}
