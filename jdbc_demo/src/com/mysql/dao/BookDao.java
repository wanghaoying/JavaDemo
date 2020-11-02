package com.mysql.dao;

import com.mysql.bean.Book;
import com.mysql.util.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;

import javax.sql.rowset.JdbcRowSet;
import java.sql.Connection;

public class BookDao extends BaseDao<Book>{

    public Book getBook(int id){
        Connection conn = null;
        Book book = null;

        try {
            conn = JDBCUtils.getConnectionByConnectionPool();
            String sql = "select id,name,author,price,sale,stack from book where id=?";
            book = query(conn,sql,1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn,null,null);
        }

        return book;
    }

    public int deleteBookByID(int id){
        Connection conn = null;
        int result = 0;

        try {
            conn = JDBCUtils.getConnectionByConnectionPool();
            String sql = "delete from book where id=?";
            result = update(conn,sql,id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn,null,null);
        }

        return result;
    }

    public long getCount(){
        Connection conn = null;
        long count = 0;
        try {
            conn = JDBCUtils.getConnectionByConnectionPool();
            String sql = "select count(*) from book";
            count =(Long) queryValue(conn,sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn,null,null);
        }

        return count;

    }
}
