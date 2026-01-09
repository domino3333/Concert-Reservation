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

    public int updateAvailable(String seatNumber, String customerName) {
        int rowCount = 0;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = JDBCTemplate.getConnection();
            con.setAutoCommit(false);

            String query = prop.getProperty("updateAvailable");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, customerName);
            pstmt.setString(2, seatNumber);

            rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                con.commit();
            } else {
                con.rollback();
            }

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
