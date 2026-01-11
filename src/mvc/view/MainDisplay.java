package mvc.view;

import mvc.util.DisplayUtil;

import java.util.Scanner;

public class MainDisplay extends DisplayUtil {

    public Scanner input = super.getScanner();
    private final String[] MENU_ARR = new String[]{
            " 1. 회원 메뉴"," 2. 콘서트 메뉴"," 3. 종료"};
    public void startMainDisplay() {
        boolean exitFlag = false;

        while (!exitFlag) {
            mainUIofMainDisplay();
            try {
                int menu = Integer.parseInt(input.nextLine());
                switch (menu) {
                    case 1:
                        //회원 메뉴
                        new MemberDisplay().startMainDisplay();
                        break;
                    case 2:
                        //콘서트 메뉴
                        new ConcertDisplay().startMainDisplay();
                        break;

                    case 3:
                        //종료
                        exitFlag = true;
                        super.exitText("메인 페이지");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.exceptionText();
                super.ln();
            }

        }
    }



    public void mainUIofMainDisplay() {

        super.topBorder();
        super.mainTitleText("메인 화면");
        super.bottomBorder();
        super.receiveMenuArrAndPrint(MENU_ARR);
        super.shortSolidLine();
        super.selectMenuView();

    }
}
