package mvc.repository;

import mvc.common.JDBCTemplate;
import mvc.dto.ReservationDto;
import mvc.model.Member;
import mvc.model.genre.ConcertGenre;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
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


    public int isAlreadyReserved(Connection con) {
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        int memberId = 0;
        int resultNum = 0;
        try {
            String query1 = prop.getProperty("selectMemberId");
            pstmt1 = con.prepareStatement(query1);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                memberId = rs1.getInt("member_id");
            } else {
                System.out.println("회원을 찾지 못했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String query2 = prop.getProperty("isMemberAlreadyInReservation");
            pstmt2 = con.prepareStatement(query2);
            pstmt2.setInt(1, memberId);
            rs2 = pstmt2.executeQuery();
            if (rs2.next()) {
                //회원이 이미 예매를 했다면 1반환
                resultNum = rs2.getInt("result");
            } else {
                resultNum = 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultNum;
    }


    /**
     * 내 예매 정보 인서트
     *
     * @param con
     * @param member
     * @return
     */
    public int insertMyReservationInfo(Connection con, Member member, String mySeat) {

        int rowCount = 0;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        int memberId = -1;
        int concertId = -1;

        //멤버에 있는 콘서트의 아이디로 디비에 있는 id랑 맞춰서
        //concertId를 가져와야해
        try {

            //db에서 가장 최근에 가입한 멤버의 id 가져오기
            String query1 = prop.getProperty("selectMemberId");
            pstmt1 = con.prepareStatement(query1);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                memberId = rs1.getInt("member_id");
            } else {
                System.out.println("회원을 찾지 못했습니다.");
            }

            //예매하기로 선택한 콘서트의 이름을 넘겨 id를 받아오기
            String query3 = prop.getProperty("selectConcertId");
            pstmt3 = con.prepareStatement(query3);
            pstmt3.setString(1, member.getConcert().getName());
            rs2 = pstmt3.executeQuery();
            if (rs2.next()) {
                concertId = rs2.getInt("concert_id");
            } else {
                System.out.println("콘서트를 찾지 못했습니다.");
            }


            // 위 두 쿼리를 기반으로 내 예매정보를 테이블에 삽입
            String query2 = prop.getProperty("insertMyReservationInfo");
            pstmt2 = con.prepareStatement(query2);

            pstmt2.setInt(1, memberId);
            pstmt2.setInt(2, concertId);
            pstmt2.setString(3, mySeat);

            rowCount = pstmt2.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCTemplate.close(rs1);
            JDBCTemplate.close(rs2);
            JDBCTemplate.close(pstmt1);
            JDBCTemplate.close(pstmt2);
            JDBCTemplate.close(pstmt3);
        }


        return rowCount;
    }

    public ReservationDto selectReservationHistory(Connection con) {

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        int memberIdForQuery = 0;
        ReservationDto dto = null;
        try {
            String query1 = prop.getProperty("selectMemberId");
            pstmt1 = con.prepareStatement(query1);
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) {
                memberIdForQuery = rs1.getInt("member_id");
            } else {
                System.out.println("회원을 찾지 못했습니다.");
                return null;
            }

            String query2 = prop.getProperty("joinMyReservationInfo");
            pstmt2 = con.prepareStatement(query2);
            pstmt2.setInt(1, memberIdForQuery);
            rs2 = pstmt2.executeQuery();

            if (rs2.next()) {
                Timestamp jointTs = rs2.getTimestamp("join_date");
                Date joinDate = new Date(jointTs.getTime());

                // CANCELLED_DATE는 null일 수 있으므로 처리
                Timestamp cancelledTs = rs2.getTimestamp("CANCELLED_DATE");
                Date cancelledDate = cancelledTs != null ? new Date(cancelledTs.getTime()) : null;

                Timestamp reservationTs = rs2.getTimestamp("RESERVATION_DATE");
                Date reservationDate = new Date(reservationTs.getTime());


                dto = new ReservationDto(
                        rs2.getInt("RESERVATION_ID"),
                        rs2.getInt("MEMBER_ID"),
                        rs2.getInt("CONCERT_ID"),
                        reservationDate,
                        cancelledDate,
                        rs2.getString("USER_NAME"),
                        rs2.getInt("AGE"),
                        rs2.getString("MY_SEAT"),
                        joinDate,
                        rs2.getString("CONCERT_NAME"),
                        ConcertGenre.valueOf(rs2.getString("GENRE").toUpperCase()),
                        rs2.getInt("ACCESS_AGE")
                );
            } else {
                System.out.println("해당 회원의 예약 내역이 없습니다.");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCTemplate.close(pstmt1);
            JDBCTemplate.close(pstmt2);
            JDBCTemplate.close(rs1);
            JDBCTemplate.close(rs2);
        }

        return dto;

    }
}
