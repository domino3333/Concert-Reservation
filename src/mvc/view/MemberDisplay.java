package mvc.view;

import java.util.List;
import java.util.Scanner;

import mvc.controller.MemberController;
import mvc.dto.MemberListDto;
import mvc.util.DisplayUtil;

public class MemberDisplay extends DisplayUtil {

	public Scanner input = new Scanner(System.in);
	private final String[] MENU_ARR = new String[]{
			" 1. 회원 정보 입력"," 2. 회원 삭제"," 9. 종료"
	};

	public void startMainDisplay(){
		boolean exitFlag = false;
		while (!exitFlag) {
			mainUIofMemberDisplay();
			try {
				int menu = Integer.parseInt(input.nextLine());
				switch (menu) {
					case 1:
						// 회원 정보 입력
						insertMyInfo();
						break;
					case 2:
						// 회원 삭제
						deleteMyInfo();
						break;

					case 9:
						// 종료
						exitFlag = true;
						super.exitText("회원 페이지");
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.exceptionText();
				super.ln();
			}

		}
	}

	public void mainUIofMemberDisplay() {

		super.topBorder();
		super.mainTitleText("회원 관리 메뉴");
		super.bottomBorder();
		super.receiveMenuArrAndPrint(MENU_ARR);
		super.shortSolidLine();
		super.selectMenuView();

	}

	public void insertMyInfo() {
		System.out.print("이름 입력:");
		String name = input.nextLine();
		System.out.print("나이 입력:");
		int age = Integer.parseInt(input.nextLine());

		new MemberController().insertMember(name,age);
		System.out.println(name+"님 안녕하세요!");
	}

	public void deleteMyInfo() {
		List<MemberListDto> memberList = new MemberController().returnAllOfMembers();
		System.out.printf(
				"%-7s %-5s %-6s %-8s %-7s %-5s %-11s %-17s %-5s %-3s%n",
				"ID", "이름", "나이", "가입일",
				"예매ID", "좌석", "예매일",
				"콘서트 이름", "장르", "관람가"
		);
		super.longDottedLine();
		for (MemberListDto data : memberList) {
			System.out.printf(
					"%-6d %-10s %-4d %-12s %-8s %-6s %-12s %-15s %-10s %-6s%n",
					data.getMemberId(),
					data.getUserName(),
					data.getMyAge(),
					data.getJoinDate(),

					data.getReservationId() != null ? data.getReservationId() : "-",
					data.getMySeat() != null ? data.getMySeat() : "-",
					data.getReservationDate() != null ? data.getReservationDate() : "-",
					data.getConcertName() != null ? data.getConcertName() : "-",
					data.getConcertGenre() != null ? data.getConcertGenre() : "-",
					data.getAccessAge() != null ? data.getAccessAge() : "-"
			);
		}

		System.out.print("삭제하고자 하는 회원의 ID를 입력해주세요:");
		int userId = Integer.parseInt(input.nextLine());
		int rowCount = new MemberController().softDeleteMyInfo(userId);

		if(rowCount>0){
			System.out.println(userId+"번 회원을 삭제했습니다.");
		}else{
			System.out.println("회원 삭제 실패");
		}

	}


}
