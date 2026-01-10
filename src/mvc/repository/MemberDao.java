package mvc.repository;

import mvc.common.JDBCTemplate;
import mvc.model.Member;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class MemberDao {

    private Properties prop;

    public MemberDao() {
        super();
        prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("resources/memberQuery.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Member returnLatestRegisterMember(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member temp = null;

        try {
            con = JDBCTemplate.getConnection();

            String query = prop.getProperty("returnLatestRegisterMember").trim();
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("member_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                Timestamp ts = rs.getTimestamp("join_date");
                Date joinDate = new Date(ts.getTime());

                temp = new Member(name, age, joinDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) JDBCTemplate.close(rs);
            if (pstmt != null) JDBCTemplate.close(pstmt);
            if (con != null) JDBCTemplate.close(con);
        }
        return temp;
    }

    public int insertMember(Connection con, String name, int age) {

        int rowCount = 0;
        PreparedStatement pstmt = null;

        try {

            String query = prop.getProperty("insertMember");
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setInt(2, age);

            rowCount = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) JDBCTemplate.close(pstmt);
        }

        return rowCount;
    }


}
