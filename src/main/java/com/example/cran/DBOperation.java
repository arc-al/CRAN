package com.example.cran;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBOperation {
    private static final String URL="jdbc:mysql://localhost:3306/cran?&useSSL=false&serverTimezone=UTC";
    private static final String USERNAME="root";
    public static final String PASSWORD = "123456";

    private Connection conn=null;
    private Statement stmt=null;
    private ResultSet rs=null;

    //加载驱动
    public DBOperation () throws Exception{
        //mysql
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    //返回数据库连接
    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    //关闭数据库资源
    public void close(){
        try {
            if(rs!=null){
                rs.close();
            }
            if(stmt!=null){
                stmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询
    public ResultSet query(String sql) throws Exception{
        try {
            conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //conn.setAutoCommit(false);
            //conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            //conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //此处不能关闭rs，因为要返回rs
        }
        return rs;
    }

    //增、删、改
    public boolean update(String sql){
        boolean flag=true;
        try {
            conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt=conn.createStatement();
            stmt.executeUpdate(sql);	//返回受该sql语句影响的记录的数目
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        } finally {
            this.close();
        }
        return flag;
    }
}
