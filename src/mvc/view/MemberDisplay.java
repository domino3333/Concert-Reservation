package mvc.view;

import java.util.Scanner;

import mvc.controller.MemberController;
import mvc.util.DisplayUtil;

public class MemberDisplay extends DisplayUtil {

	public Scanner input = new Scanner(System.in);
	private final String[] MENU_ARR = new String[]{
			" 1. 회원 정보 입력"," 9. 종료"
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
		super.solidLine();
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

}
