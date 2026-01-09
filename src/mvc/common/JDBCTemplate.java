package mvc.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {

    public static Connection getConnection(){
        Properties prop = new Properties();
        Connection con = null;
        try {
            prop.load(new FileInputStream("resources/driver.properties"));
            String DRIVER = prop.getProperty("DRIVER");
            String URL = prop.getProperty("URL");
            String USER = prop.getProperty("USER");
            String PASSWORD = prop.getProperty("PASSWORD");

            Class.forName(DRIVER);

            con = DriverManager.getConnection(URL, USER, PASSWORD);
            con.setAutoCommit(false);


            return con;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
        // 커밋과 롤백도 여기서 관리-----------------------------------------------
        public static void commit (Connection con){
            try {
                // con이 있고 && 열려 있는지
                if (con != null && !con.isClosed()) {
                    con.commit();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void rollback (Connection con){
            try {
                // con이 있고 && 열려 있는지
                if (con != null && !con.isClosed()) {
                    con.rollback();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ResultSet, stmt, pstmt, con 닫아주기 ------------------------------
        public static void close (Connection con){
            try {
                // con이 있고 && 열려 있는지
                if (con != null && !con.isClosed()) {
                    con.close();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Statement가 PreparedStatement의 부모이기 때문에 여기에 pstmt넣어도 됨
        public static void close (Statement stmt){
            try {
                // con이 있고 && 열려 있는지
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void close (ResultSet rset){
            try {
                // con이 있고 && 열려 있는지
                if (rset != null && !rset.isClosed()) {
                    rset.close();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
