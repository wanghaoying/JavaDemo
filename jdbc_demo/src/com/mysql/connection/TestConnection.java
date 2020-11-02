package com.mysql.connection;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnection {

    @Test
    public void testConnection1() throws SQLException {
        // 配置数据库驱动
        Driver driver = new com.mysql.jdbc.Driver();
        // 配置数据库连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        Properties properties = new Properties();
        properties.put("user","root");
        properties.put("password","123456");

        // 获取数据库连接
        Connection connection = driver.connect(url,properties);
        System.out.println(connection);
    }

    @Test
    public void testConnection2() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, SQLException {
        // 利用反射机制配置数据库驱动
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver =(Driver) clazz.newInstance();

        // 配置数据库连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        Properties properties = new Properties();
        properties.put("user","root");
        properties.put("password","123456");

        // 获取数据库连接
        Connection connection = driver.connect(url,properties);
        System.out.println(connection);


    }

    @Test
    public void testConnection3() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, SQLException {

        // 配置数据库连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";

        // 利用反射机制配置数据库驱动,使用DriverManager来管理驱动
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        DriverManager.registerDriver((Driver) clazz.newInstance());

        // 获取数据库连接
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);

    }

    @Test
    public void testConnection4() throws ClassNotFoundException, SQLException {

        // 配置数据库连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";

        // 利用反射机制配置数据库驱动,使用DriverManager来管理驱动
        // 对于mysql的jdbc来说，其实下面这行也可以注释掉，但只有mysql可以这么做
        Class.forName("com.mysql.jdbc.Driver");
//        DriverManager.registerDriver((Driver) clazz.newInstance());

        /*
        可以注释掉上述代码的原因，是因为在mysql的Driver类中声明有：
        static {
            try {
                DriverManager.registerDriver(new Driver());
            } catch (SQLException var1) {
                throw new RuntimeException("Can't register driver!");
            }
        }

        DriverManager中声明了一个元素为DriverInfo类型的数组registeredDrivers，这个数组为
        静态常量(final static)，为单例模式的，为所有的DriverManager对象共有，所以在下面直接
        调用getConnection的时候，可以直接调用成功
         */


        // 获取数据库连接
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);

    }

    /**
     * 使用配置文件的好处
     * ①实现了代码和数据的分离，如果需要修改配置信息，直接在配置文件中修改，不需要深入代码
     * ②如果修改了配置信息，省去重新编译的过程。
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void testConnection5() throws IOException, ClassNotFoundException, SQLException {
        // 读取配置信息
        InputStream inputStream = ClassLoader.getSystemClassLoader()
                                    .getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        // 初始化驱动，driver自动加载到DriverManager中
        Class.forName(properties.getProperty("driver"));

        // 获取数据库连接
        Connection connection = DriverManager.getConnection(
                                    properties.getProperty("url"),
                                    properties.getProperty("username"),
                                    properties.getProperty("password"));

        System.out.println(connection);

    }
}
