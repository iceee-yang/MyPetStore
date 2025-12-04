package com.csu.petstore.persistence;

import java.sql.*;

public class DBUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mypetstore";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ice20051109";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void closeStatement(Statement statement){
        if(statement != null){
            try {
                statement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void closePreparedStatement(PreparedStatement preparedStatement){
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void closeResultSet(ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("数据库连接成功！");

            // 测试查询数据
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM CATEGORY");
                if (rs.next()) {
                    System.out.println("CATEGORY表记录数: " + rs.getInt(1));
                }

                rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                if (rs.next()) {
                    System.out.println("PRODUCT表记录数: " + rs.getInt(1));
                }

                DBUtil.closeResultSet(rs);
                DBUtil.closeStatement(stmt);
            } catch (Exception e) {
                e.printStackTrace();
            }

            DBUtil.closeConnection(conn);
        } else {
            System.out.println("数据库连接失败！");
        }
    }
}
