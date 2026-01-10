package mvc.view;

import java.util.List;
import java.util.Scanner;

import mvc.controller.ConcertController;
import mvc.controller.MemberController;
import mvc.model.Concert;

public class MemberDisplay {

	public Scanner input = new Scanner(System.in);


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
					case 9:
						// 종료
						exitFlag = true;
						new MainDisplay().exitText();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				exceptionText();
				ln();
			}

		}
	}

	public void mainUIofMemberDisplay() {

		System.out.println("┌──────────────────────────────┐");
		System.out.println("         회원 관리 메뉴     ");
		System.out.println("└──────────────────────────────┘");
		System.out.println(" 1. 회원 정보 입력");
		System.out.println(" 9. 종료");
		System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		System.out.print(" 메뉴 번호 입력:");

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


	public void insertMyInfo() {
		System.out.print("이름 입력:");
		String name = input.nextLine();
		System.out.print("나이 입력:");
		int age = Integer.parseInt(input.nextLine());

		new MemberController().insertMember(name,age);
		System.out.println(name+"님 안녕하세요!");
		
		
	}

}
