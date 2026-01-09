package mvc.controller;

import mvc.model.Member;
import mvc.service.MemberService;
import mvc.view.MenuDisplay;

public class MemberController {

    public void insertMember(String name, int age) {
        if(new MemberService().insertMember(name,age)!=0){
            new MenuDisplay().insertResultMessage("멤버 insert 성공");
        }else{
            new MenuDisplay().insertResultMessage("멤버 insert 실패");
        }

    }
}
