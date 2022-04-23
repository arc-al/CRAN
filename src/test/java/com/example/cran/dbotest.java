package com.example.cran;


import java.sql.ResultSet;

public class dbotest {
    public static void main(String[] args) throws Exception {
        DBOperation dbo = new DBOperation();
        String sql = "select * from sla";
        ResultSet rs = dbo.query(sql);
        while(rs.next()){
            int id = rs.getInt("id");
            System.out.println(id);
        }
    }
}
