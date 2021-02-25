package com.mysqlTest.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    // 声明一个线程池
    private static ComboPooledDataSource dataSource = null;
    // 声明一个静态代码块，使dataSource成为单例的存在
    static {
        try {
            dataSource = new ComboPooledDataSource();
//            dataSource.setDriverClass( "com.mysql.jdbc.Driver" );
//            dataSource.setJdbcUrl( "jdbc:mysql://localhost:3306/bookstore" );
//            dataSource.setUser("root");
//            dataSource.setPassword("123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        Connection connection = getConnectionByConnectionPool();
    }

    /**
     * 获取数据库连接，在jdbc.properties中修改数据库的配置
     * @return
     */
    public static Connection getConnection(){
        Connection conn = null;
        try {
            // 加载配置文件
            InputStream is = ClassLoader.getSystemClassLoader()
                                        .getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(is);

            // 加载驱动并自动注册到DriverManager中
            Class.forName(properties.getProperty("driver"));

            // 从DriverManager中获取Connection对象
            conn = DriverManager.getConnection(properties.getProperty("url"),
                                        properties.getProperty("username"),
                                        properties.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 通过线程池获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getConnectionByConnectionPool() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    /**
     * 对维持数据库连接的相关资源进行关闭
     * @param conn Connection
     * @param ps  PreparedStatement
     * @param rs  ResultSet
     */
    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(ps != null){
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(rs != null){
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
