package mvc.repository;

import mvc.common.JDBCTemplate;
import mvc.dto.MemberListDto;
import mvc.model.Member;
import mvc.model.genre.ConcertGenre;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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


    /**
     * 가장 최근에 등록된 회원을 반환하는 함수
     * @param
     * @return joinDate를 기준으로 역정렬하여 rownum=1 인 member를 반환
     */
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


    /**
     * 회원삭제 기능에서 멤버 목록을 쫙 보여주는 함수
     * 참조과정: (member->reservation->concert) left join
     * @param
     * @return memberListDto 로 객체를 만들어 반환
     */
    public List<MemberListDto> selectAllOfMembers(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MemberListDto> memberDtoList = new ArrayList<>();

        try {
            con = JDBCTemplate.getConnection();

            String query = prop.getProperty("joinMemberWithReservationAndConcert").trim();
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MemberListDto dto = new MemberListDto();
                dto.setMemberId(rs.getInt("MEMBER_ID"));          // NOT NULL
                dto.setUserName(rs.getString("NAME"));            // NOT NULL
                dto.setMyAge(rs.getInt("AGE"));                    // NOT NULL

                Timestamp joinTs = rs.getTimestamp("JOIN_DATE");
                dto.setJoinDate(joinTs != null ? new Date(joinTs.getTime()) : null);

                dto.setReservationId(rs.getObject("RESERVATION_ID", Integer.class));
                dto.setMySeat(rs.getString("MY_SEAT"));

                Timestamp resTs = rs.getTimestamp("RESERVATION_DATE");
                dto.setReservationDate(resTs != null ? new Date(resTs.getTime()) : null);

                dto.setConcertName(rs.getString("CONCERT_NAME"));

                String genreStr = rs.getString("GENRE");
                dto.setConcertGenre(
                        genreStr != null ? ConcertGenre.valueOf(genreStr) : null
                );

                dto.setAccessAge(rs.getObject("ACCESS_AGE", Integer.class));

                memberDtoList.add(dto);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) JDBCTemplate.close(rs);
            if (pstmt != null) JDBCTemplate.close(pstmt);
            if (con != null) JDBCTemplate.close(con);
        }
        return memberDtoList;

    }

    /**
     * id를 입력해 회원을 삭제하는 함수(soft delete)
     * @param con
     * @param userId: 디스플레이로부터 userId를 넘겨받기
     * @return rowCount
     */
    public int softDeleteMyInfo(Connection con, int userId) {
        int rowCount = 0;
        PreparedStatement pstmt = null;

        try {

            String query = prop.getProperty("softDelete");
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, userId);

            rowCount = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) JDBCTemplate.close(pstmt);
        }

        return rowCount;

    }
}
