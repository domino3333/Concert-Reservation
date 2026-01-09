package mvc.repository;

import mvc.common.JDBCTemplate;
import mvc.model.BalladConcert;
import mvc.model.Concert;
import mvc.model.DanceConcert;
import mvc.model.genre.ConcertGenre;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConcertDao {

    Properties prop;

    public ConcertDao() {
        prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("resources/concertQuery.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public int insertConcert(Connection con, Concert concert) {

        int rowCount = 0;
        PreparedStatement pstmt = null;

        try {
            String query = prop.getProperty("insertConcert");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, concert.getName());
            pstmt.setString(2, concert.getGenre().toString());
            pstmt.setInt(3, concert.getAccessAge());

            rowCount = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (pstmt != null) JDBCTemplate.close(pstmt);

        }

        return rowCount;
    }

    public List<Concert> returnAllOfConcerts(Connection con) {

        List<Concert> concertList = new ArrayList<>();
        if (concertList.size() != 0) concertList.clear();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String query = prop.getProperty("returnAllOfConcerts");
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("concert_id");
                String name = rs.getString("name");
                String genreStr = rs.getString("genre");
                int accessAge = rs.getInt("access_age");

                ConcertGenre genre = ConcertGenre.valueOf(genreStr.toUpperCase());
                Concert concert;

                if (genre == ConcertGenre.DANCE) {
                    concert = new DanceConcert(name, genre, accessAge);
                } else {
                    concert = new BalladConcert(name, genre, accessAge);
                }
                concertList.add(concert);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) JDBCTemplate.close(rs);
            if (pstmt != null) JDBCTemplate.close(pstmt);

        }
        return concertList;
    }
}
