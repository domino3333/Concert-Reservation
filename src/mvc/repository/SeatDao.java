package mvc.repository;

import mvc.common.JDBCTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SeatDao {

    private Properties prop;

    public SeatDao(){
        super();
        prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("resources/seatQuery.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * view에 현재 좌석 현황을 그리기 위해 seat이 이용 가능한지 체크하는 함수
     * @return db로부터 받아온 T/F 를 배열로 만들어 반환
     */
    public List<Character> isAvailableSeat() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Character> charList = new ArrayList<>();

        try {
            con = JDBCTemplate.getConnection();
            con.setAutoCommit(false);

            String query = prop.getProperty("isAvailableSeat");
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                char a = rs.getString("is_available").charAt(0);
                charList.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return charList;
    }


    /**
     * 자리 예매 완료 시, 자리를 F로 update 해주는 함수
     * @param seatNumber 자리 string (예:A3)
     * @param customerName 고객의 이름
     * @param con
     * @return rowCount
     */
    public int updateAvailable(String seatNumber, String customerName,Connection con) {
        int rowCount = 0;
        PreparedStatement pstmt = null;

        try {

            String query = prop.getProperty("updateAvailable");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, customerName);
            pstmt.setString(2, seatNumber);

            rowCount = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rowCount;
    }
}
