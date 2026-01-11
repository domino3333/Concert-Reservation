package mvc.view;

import mvc.controller.ReservationController;
import mvc.model.Concert;
import mvc.service.ConcertService;
import mvc.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static mvc.service.ReservationService.*;

public class ReserveDisplay extends DisplayUtil {

	public Scanner input = super.getScanner();
	List<Concert> concertList = new ArrayList<>(new ConcertService().returnAllConcerts());

	public Concert selectConcertDisplay(){

		System.out.print("예매할 콘서트를 선택해주세요(0~8):");
		int choice = Integer.parseInt(input.nextLine());
		return concertList.get(choice);
	}

	public String requestSeatNumber(){
		// 좌석은 입력을 받고, 멤버는 레포에서 꺼내오기
		System.out.print("원하는 좌석 번호를 미리 입력하세요 (예: A1): "); // 내가 예매해야 돼
		String mySeat = input.nextLine().toUpperCase();
		String myName = new ReservationController().returnMember().getName();
		return mySeat+" "+myName;

	}

	public void deadLineDisplay(){
		System.out.println("예매가 마감되었습니다.\n좌석을 확인해주세요.");
	}

	public void reserveCountDown(){
		System.out.println("\n예매가 곧 시작됩니다!\n예매는 15초간 진행합니다!");
		System.out.println("3초 후 예매 오픈...");
		for (int i = 3; i > 0; i--) {
			System.out.println(i + "...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("🎉 예매 오픈 🎉");
	}

	public void callReserveOfReserveDisplay(){
		new ReservationController().callReserve();
	}


	public void seatDisplay() {
		// 좌석 화면 보여주기
		System.out.println();
		super.shortDottedLine();
		super.mainTitleText("현재 좌석 현황");
		super.shortDottedLine();
		System.out.println(" □ : 사용 가능");
		System.out.println(" ■ : 사용 불가");
		System.out.println();

		for (int i = 0; i < SEAT_COL; i++) {
			if (i > 0) {
				System.out.print(" " + (i + 1) + "\t");
			} else
				System.out.print("  " + (i + 1) + "\t");

		}
		System.out.println();
		char rowChar = 'A';

		int count = 0;
		List<Character> cList= new ReservationController().selectSeatIsAvailable();
		for (int i = 0; i < SEAT_ROW; i++) {
			System.out.print(rowChar++);
			for (int j = 0; j < SEAT_COL; j++) {

				if(cList.get(count++)=='T'){
					System.out.print(" □\t");
				} else {
					System.out.print(" ■\t");
				}
			}
			System.out.println();
		}
		System.out.println();

	}

	public void displayConcertList() {
		System.out.printf("%-5s %-15s %-10s %-10s%n",
				"번호", "이름", "장르", "관람가");

		super.longDottedLine();
		for (int i = 0; i < concertList.size(); i++) {
			Concert c = concertList.get(i);

			System.out.printf("%-5d %-15s %-10s %-10s%n",
					i,
					c.getName(),
					c.getGenre(),
					c.getAccessAge()
			);
		}
	}

}
