package mvc.service;

import mvc.common.JDBCTemplate;
import mvc.dto.MemberListDto;
import mvc.model.Member;
import mvc.repository.MemberDao;

import java.sql.Connection;
import java.util.List;

public class MemberService {


    public int insertMember(String name, int age){
        Connection con = JDBCTemplate.getConnection();


        int rowCount = new MemberDao().insertMember(con,name,age);

        if(rowCount>0){
            JDBCTemplate.commit(con);
        }else{
            JDBCTemplate.rollback(con);
        }

        JDBCTemplate.close(con);
        return rowCount;
    }

    public List<MemberListDto> returnAllOfMembers() {
        Connection con = JDBCTemplate.getConnection();

        List<MemberListDto> memberList = new MemberDao().selectAllOfMembers(con);
        JDBCTemplate.close(con);

        return memberList;

    }

    public int softDeleteMyInfo(int userId) {
        int rowCount = 0;
        Connection con = JDBCTemplate.getConnection();
        rowCount = new MemberDao().softDeleteMyInfo(con,userId);
        if(rowCount>0){
            JDBCTemplate.commit(con);
        }else{
            JDBCTemplate.rollback(con);
        }
        JDBCTemplate.close(con);

        return rowCount;
    }
}
