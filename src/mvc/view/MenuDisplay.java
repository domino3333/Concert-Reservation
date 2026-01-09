package mvc.view;

import java.util.List;
import java.util.Scanner;

import mvc.controller.ConcertController;
import mvc.controller.MemberController;
import mvc.model.Concert;
import mvc.run.Run;

public class MenuDisplay {

	public Scanner input = new Scanner(System.in);

	public void insertConcert() {
		new ConcertController().insertConcert();
	}

	public void mainmainmain(){
		boolean exitFlag = false;
		while (!exitFlag) {
			mainMenu();
			try {
				int menu = Integer.parseInt(input.nextLine());
				switch (menu) {
					case 1:
						// 회원 정보 입력
						insertMyInfo();
						break;
					case 2:
						// 콘서트 삽입하기
						insertConcert();
						break;
					case 3:
						// 콘서트 출력하기
						printAllOfConcerts();
						break;
					case 4:
						// 콘서트 검색하기
						searchConcert();
						break;
					case 5:
						// 예매하기
						new ReserveDisplay().callReserveOfReserveDisplay();
						break;
					case 6:
						// 좌석 현황 보이기
						new ReserveDisplay().seatDisplay();
						break;

					case 9:
						// 종료
						exitFlag = true;
						new MenuDisplay().exitText();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				new MenuDisplay().exceptionText();
				new MenuDisplay().ln();
			}

		}
	}

	public void mainMenu() {

		System.out.println("┌──────────────────────────────┐");
		System.out.println("         콘서트 예매 시스템         ");
		System.out.println("└──────────────────────────────┘");
		System.out.println(" 1. 회원 정보 입력");
		System.out.println(" 2. 콘서트 삽입하기");
		System.out.println(" 3. 콘서트 정보 출력하기");
		System.out.println(" 4. 콘서트 검색하기");
		System.out.println(" 5. 예매하기");
		System.out.println(" 6. 좌석현황 보기");
		System.out.println(" 9. 종료");
		System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		System.out.print(" 메뉴 번호 입력:");

	}
	
	public void exitText() {
		System.out.println("종료합니다.");
	}
	public void exceptionText() {
		System.out.println("유효한 키를 입력해주세요. 부탁드립니다ㅠㅜ");
	}
	public void ln() {
		System.out.println();
	}


	public void insertResultMessage(String message){
		System.out.println(message);

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

	public void insertMyInfo() {
		System.out.print("이름 입력:");
		String name = input.nextLine();
		System.out.print("나이 입력:");
		int age = Integer.parseInt(input.nextLine());

		new MemberController().insertMember(name,age);
		System.out.println(name+"님 안녕하세요!");
		
		
	}

}
