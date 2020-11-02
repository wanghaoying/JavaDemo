package com.mysql.queryRunner;

import com.mysql.bean.Book;
import com.mysql.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class QueryRunnerTest {

    @Test
    public void updateTest(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionByConnectionPool();
            String sql = "delete from book where id=?";
            int result = update(conn,sql,103);
            if(result >0){
                System.out.println("删除成功");
            }else {
                System.out.println("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn,null,null);
        }
    }

    @Test
    public void queryTest(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionByConnectionPool();
            String sql = "select id,name,author,price,sale,stack from book where id=?";

            Book book = query(Book.class,conn,sql,1);
            System.out.println(book);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn,null,null);
        }
    }

    @Test
    public void queryListTest(){
        Connection conn = null;

        try {
            conn = JDBCUtils.getConnectionByConnectionPool();
            String sql = "select id,name,author,price,sale,stack from book where id<?";
            List<Book> res = queryList(Book.class,conn,sql,3);
            res.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,null,null);
        }

    }

    public int update(Connection conn, String sql, Object... args) throws Exception{
        QueryRunner queryRunner = new QueryRunner();

        int result = queryRunner.update(conn, sql, args);

        return result;
    }

    public <T> T query(Class<T> clazz,Connection conn, String sql, Object... args) throws Exception{
        QueryRunner queryRunner = new QueryRunner();

        T result = queryRunner.query(conn, sql,new BeanHandler<T>(clazz), args);

        return result;
    }

    public <T> List<T> queryList(Class<T> clazz, Connection conn, String sql, Object... args) throws Exception{

        QueryRunner queryRunner = new QueryRunner();

        List<T> result = queryRunner.query(conn,sql,new BeanListHandler<T>(clazz),args);

        return result;
    }

}
