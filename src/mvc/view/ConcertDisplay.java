package mvc.view;

import mvc.controller.ConcertController;
import mvc.controller.ReservationController;
import mvc.dto.ReservationDto;
import mvc.model.Concert;
import mvc.util.DisplayUtil;

import java.util.List;
import java.util.Scanner;

public class ConcertDisplay extends DisplayUtil {


    Scanner input = super.getScanner();
    private final String[] MENU_ARR = new String[]{
            " 1. 콘서트 삽입", " 2. 콘서트 출력", " 3. 콘서트 검색", " 4. 예매하기", " 5. 좌석현황 확인하기"
            , " 6. 내 예매 내역 보기", " 9. 종료"
    };


    public void startMainDisplay() {

        boolean exitFlag = false;
        while (!exitFlag) {
            mainUIofConcertDisplay();
            try {
                int menu = Integer.parseInt(input.nextLine());
                switch (menu) {
                    case 1:
                        // 콘서트 삽입하기
                        insertConcert();
                        break;
                    case 2:
                        // 콘서트 출력하기
                        printAllOfConcerts();
                        break;
                    case 3:
                        // 콘서트 검색하기
                        searchConcert();
                        break;
                    case 4:
                        // 예매하기
                        new ReserveDisplay().callReserveOfReserveDisplay();
                        break;
                    case 5:
                        // 좌석 현황 보이기
                        new ReserveDisplay().seatDisplay();
                        break;
                    case 6:
                        // 예매 내역 출력
                        reservationHistory();
                        break;
                    case 9:
                        // 종료
                        exitFlag = true;
                        super.exitText("콘서트 페이지");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.exceptionText();
                super.ln();
            }

        }
    }


    public void reservationHistory() {
        new ReservationController().reservationHistory();
    }

    public void mainUIofConcertDisplay() {

        super.topBorder();
        super.mainTitleText("콘서트 페이지");
        super.bottomBorder();
        super.receiveMenuArrAndPrint(MENU_ARR);
        System.out.println();
        super.solidLine();
        super.selectMenuView();

    }

    public void insertConcert() {
        new ConcertController().insertConcert();
    }


    public void printAllOfConcerts() {

        for (Concert concert : new ConcertController().returnAllConcerts()) {
            System.out.println(concert.toString());
        }
    }

    public void searchConcert() {
        System.out.print("콘서트 이름으로 검색하기: ");
        String keyword = input.nextLine();
        List<Concert> concertList = new ConcertController().searchConcert(keyword);
        for (Concert concert : concertList) {
            System.out.println(concert.toString());
        }
    }

    public void viewReservationHistory(ReservationDto dto) {
        if (dto == null) {
            System.out.println("예매 내역이 없습니다.");
            return;
        }

        resultTopSeparator("예매 내역");
        System.out.println("예약 ID: " + dto.getReservationId());
        System.out.println("회원명: " + dto.getUserName());
        System.out.println("나이: " + dto.getMyAge());
        System.out.println("콘서트명: " + dto.getConcertName());
        System.out.println("장르: " + dto.getConcertGenre());
        System.out.println("좌석: " + dto.getMySeat());
        System.out.println("예약일: " + dto.getReservationDate());
        resultBottomSeparator();
    }
}