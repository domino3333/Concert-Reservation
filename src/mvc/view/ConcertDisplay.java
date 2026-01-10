package mvc.view;

import mvc.controller.ConcertController;
import mvc.model.Concert;

import java.util.List;
import java.util.Scanner;

public class ConcertDisplay {

    public Scanner input = new Scanner(System.in);

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
                    case 9:
                        // 종료
                        exitFlag = true;
                        new MainDisplay().exitText();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                new MemberDisplay().exceptionText();
                new MemberDisplay().ln();
            }

        }
    }


    public void insertResultMessage(String message){
        System.out.println(message);

    }

    public void mainUIofConcertDisplay() {
        System.out.println("┌──────────────────────────────┐");
        System.out.println("         콘서트 메뉴     ");
        System.out.println("└──────────────────────────────┘");
        System.out.println(" 1. 콘서트 삽입");
        System.out.println(" 2. 콘서트 출력");
        System.out.println(" 3. 콘서트 검색");
        System.out.println(" 4. 예매하기");
        System.out.println(" 5. 좌석현황 확인하기");
        System.out.println(" 9. 종료");
        System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print(" 메뉴 번호 입력:");
    }

    public void insertConcert() {
        new ConcertController().insertConcert();
    }


    public void printAllOfConcerts() {

        for(Concert concert : new ConcertController().returnAllConcerts()){
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
}