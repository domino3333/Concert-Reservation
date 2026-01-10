package mvc.repository;

import mvc.model.Member;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ReservationDao {
    private final Properties prop;

    public ReservationDao() {
        super();
        prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("resources/reservationQuery.xml"));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * 내 예매 정보 인서트
     *
     * @param con
     * @param member
     * @return
     */
    public int insertMyReservationInfo(Connection con, Member member) {

        int rowCount = 0;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        int memberId = -1;
        int concertId = -1;

//        멤버에 있는 콘서트의 아이디로 디비에 있는 id랑 맞춰서
//                concertId를 가져와야해
        try {

            //db에서 가장 최근에 가입한 멤버의 id 가져오기
            String query1 = prop.getProperty("selectMemberId");
            pstmt1 = con.prepareStatement(query1);
            rs1 = pstmt1.executeQuery();
            if(rs1.next()){
                memberId = rs1.getInt("member_id");
            }else {
                System.out.println("회원을 찾지 못했습니다.");
            }

            //예매하기로 선택한 콘서트의 이름을 넘겨 id를 받아오기
            String query3 = prop.getProperty("selectConcertId");
            pstmt3 = con.prepareStatement(query3);
            pstmt3.setString(1,member.getConcert().getName());
            rs2 = pstmt3.executeQuery();
            if(rs2.next()){
                concertId = rs2.getInt("concert_id");
            }else {
                System.out.println("콘서트를 찾지 못했습니다.");
            }


            // 위 두 쿼리를 기반으로 내 예매정보를 테이블에 삽입
            String query2 = prop.getProperty("insertMyReservationInfo");
            pstmt2 = con.prepareStatement(query2);

            pstmt2.setInt(1, memberId);
            pstmt2.setInt(2, concertId);
            pstmt2.setString(3, member.getMySeat());

            rowCount = pstmt2.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return rowCount;
    }
}
