package exam08;

import java.io.*;
import java.sql.*;

public class BoardInsertExample {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://10.80.163.27:3306/thisisjava",
                    "java",
                    "mysql"
            );
            String sql = "+" +
                    "INSERT INTO boards (btitle, bcontent, bwriter, bdate, bfiledata)" + "VALUES(?,?,?,  now(), ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "눈 오는 날");
            pstmt.setString(2, "함박눈이 내려요");
            pstmt.setString(3, "winter");
            pstmt.setString(4, "snow.png");
            pstmt.setBlob(5, new FileInputStream("/Users/choesihun/Desktop/snow.png"));

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 수: " + rows);
//bno 값 얻기
            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int bno = rs.getInt(1);
                    System.out.println("저장된 bno: " + bno);
                }
                rs.close();
            }
//Preparedstatement 닫기
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn == null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}