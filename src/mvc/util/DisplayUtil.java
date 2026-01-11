package mvc.util;

import java.util.Scanner;

public class DisplayUtil {

    private Scanner input = new Scanner(System.in);

    public Scanner getScanner(){
        return this.input;
    }
    public void exitText(){
        System.out.println("종료합니다..");
    }
    public void exitText(String pageName){
        System.out.println(pageName+" 종료");
    }

    public void topBorder() {
        System.out.println("┌──────────────────────────────┐");
    }

    public void bottomBorder() {
        System.out.println("└──────────────────────────────┘");
    }

    public void mainTitleText(String title) {
        if(title.length()>7){
            System.out.println("         "+title+"            ");
        }else if(title.length()>5){
            System.out.println("          "+title+"            ");
        }else{
            System.out.println("           "+title+"            ");
        }
    }

    public void shortDottedLine() {
        System.out.println("------------------------------");
    }

    public void shortSolidLine() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    public void longDottedLine(){
        System.out.println("-------------------------------------------------------------------------------------------------------");

    }

    public void selectMenuView() {
        System.out.print(" 메뉴 번호 입력:");
    }
    public void receiveMenuArrAndPrint(String[] arr){
        for (String s : arr) {
            System.out.println(s);
        }
    }
    public void ln() {
        System.out.println();
    }

    public void resultMessage(String message){
        System.out.println(message);
    }
    public void exceptionText() {
        System.out.println("유효한 키를 입력해주세요. 부탁드립니다ㅠㅜ");
    }

    public void resultTopSeparator(String miniTitle) {
        System.out.println("=========="+miniTitle+"==========");
    }
    public void resultBottomSeparator() {
        System.out.println("=============================");
    }

    public void printMessage(String s) {
        System.out.println(s);
    }

}
