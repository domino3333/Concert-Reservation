package mvc.controller;

import mvc.service.MemberService;
import mvc.view.MemberDisplay;

public class MemberController {

    public void insertMember(String name, int age) {
        if(new MemberService().insertMember(name,age)!=0){
            new MemberDisplay().resultMessage("멤버 insert 성공");
        }else{
            new MemberDisplay().resultMessage("멤버 insert 실패");
        }

    }
}
