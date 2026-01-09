package mvc.service;

import mvc.common.JDBCTemplate;
import mvc.repository.MemberDao;

import java.sql.Connection;

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
}
