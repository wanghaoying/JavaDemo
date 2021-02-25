package com.mysqlTest.preparedStatement;

import com.mysqlTest.bean.Book;
import com.mysqlTest.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementTest {

    @Test
    public void updateTest(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionByConnectionPool();
            String sql = "delete from book where id=?";
            int result = update(conn,sql,104);
            if(result > 0 ){
                System.out.println("删除成功");
            }else{
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
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,author,price,sale,stack from book where id=?";
            Book book = query(Book.class,conn,sql,1);
            System.out.println(book);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn,null,null);
        }
    }

    @Test
    public void queryListTest(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,author,price,sale,stack from book where id<?";
            List<Book> list = queryList(Book.class,conn,sql,13);
            list.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn,null,null);
        }
    }

    /**
     * 增删改查的通用方法
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public int update(Connection conn,String sql, Object ...args){
        // 声明一个PreparedStatement对象，这是预编译的对象，可以避免拼串以及SQL注入的问题
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // ps.executeUpdate方法返回影响的行数
            int result = ps.executeUpdate();
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(null,ps,null);
        }

        return 0;

    }

    /**
     * 查询操作的通用方法，（泛型方法）需要传入一个定义好的Bean
     * @param clazz
     * @param conn
     * @param sql
     * @param args
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T query(Class<T> clazz,Connection conn, String sql, Object ...args) throws Exception{
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for(int i=0; i<args.length; i++){
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            // 获取结果集元数据，结果集元数据中存储有返回集的相关信息，如列数，列名等
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            if(rs.next()){
                T t = clazz.newInstance();
                for(int i = 0; i<columnCount; i++){
                    // 利用反射机制对数据进行填充
                    Object columnValue = rs.getObject(i+1);
                    String columnName = rsmd.getColumnLabel(i+1);

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(null,ps,rs);
        }

        return null;
    }

    /**
     * 返回多条查询结果的通用方法，与query类似，也是泛型方法。需要传入一个声明的Bean
     * @param clazz
     * @param conn
     * @param sql
     * @param args
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> queryList(Class<T> clazz, Connection conn, String sql, Object ...args) throws Exception{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<T> arrayList = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for(int i=0; i<args.length; i++){
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            // 获取结果集元数据，结果集元数据中存储有返回集的相关信息，如列数，列名等
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while(rs.next()){
                T t = clazz.newInstance();
                for(int i = 0; i<columnCount; i++){
                    // 利用反射机制填充数据
                    Object columnValue = rs.getObject(i+1);
                    String columnName = rsmd.getColumnLabel(i+1);

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                arrayList.add(t);
            }

            return arrayList;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(null,ps,rs);
        }

        return  arrayList;
    }
}
