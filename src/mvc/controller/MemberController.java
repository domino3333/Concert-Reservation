package mvc.controller;

import mvc.dto.MemberListDto;
import mvc.model.Member;
import mvc.service.MemberService;
import mvc.view.MemberDisplay;

import java.util.List;

public class MemberController {

    public void insertMember(String name, int age) {
        if(new MemberService().insertMember(name,age)!=0){
            new MemberDisplay().resultMessage("멤버 insert 성공");
        }else{
            new MemberDisplay().resultMessage("멤버 insert 실패");
        }
    }

    public List<MemberListDto> returnAllOfMembers(){
        List<MemberListDto> memeberList = new MemberService().returnAllOfMembers();

        return memeberList;
    }

    public int softDeleteMyInfo(int userId) {
        return new MemberService().softDeleteMyInfo(userId);
    }
}
