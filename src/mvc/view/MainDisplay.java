package mvc.view;

import java.util.Scanner;

public class MainDisplay {

    public Scanner input = new Scanner(System.in);

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
                        exitText();
                        break;


                }
            } catch (Exception e) {
                e.printStackTrace();
                new MemberDisplay().exceptionText();
                new MemberDisplay().ln();
            }

        }
    }
    public void exitText() {
        System.out.println("종료합니다.");
    }


    public void mainUIofMainDisplay() {
        System.out.println("┌──────────────────────────────┐");
        System.out.println("            메인 메뉴           ");
        System.out.println("└──────────────────────────────┘");
        System.out.println(" 1. 회원 메뉴");
        System.out.println(" 2. 콘서트 메뉴");
        System.out.println(" 3. 종료");

        System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print(" 메뉴 번호 입력:");

    }
}
