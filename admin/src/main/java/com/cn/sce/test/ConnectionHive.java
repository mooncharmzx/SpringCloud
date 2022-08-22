package com.cn.sce.test;

import java.sql.*;

public class ConnectionHive {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver");

        Connection conn = DriverManager.getConnection("jdbc:hive://192.168.0.103:11000/default","hive","");

        Statement stmt = conn.createStatement();

        String querySQL = "select id from hello";



        ResultSet rs = stmt.executeQuery(querySQL);



        while(rs.next()){


        }



        rs.close();

        stmt.close();

        conn.close();
    }
}
