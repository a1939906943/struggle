package com.coolwe.start.util;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by otote Cotter on 2018/9/11.
 */
public class JDBCUtil {
    private static Logger logger = LoggerFactory.getLogger(JDBCUtil.class);
    public static Connection conn = null;
    public static PreparedStatement statement = null;
    public static ResultSet resultSet = null;
    public static String driver = null;
    public static String url = null;
    public static String user = null;
    public static String pwd = null;
    /***
     * 加载配置文件 初始化
     */
    static {
        try {
            Properties properties = new Properties();
            // 加载配置文件 通过类加载器
            properties.load(JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties"));
            driver = properties.getProperty("spring.datasource.driver-class-name");
            url = properties.getProperty("spring.datasource.url");
            user = properties.getProperty("spring.datasource.username");
            pwd = properties.getProperty("spring.datasource.password");
            // 加载驱动
            Class.forName(driver);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * 获得连接
     * 
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /***
     * 获取操作数据库的对象
     * 
     * @param sql sql语句
     * @param ob  参数 可变
     * @return PreparedStatement
     */
    public static PreparedStatement getStatement(String sql) {
        // 加载驱动
        try {
            // 创建连接对象
            conn = getConnection();
            // 创建执行对象
            statement = conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static List<Map<String, Object>> executeQuery(String sql) { // 可变参数 Object... args
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet set = null;

        List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            /* 执行 */
            set = ps.executeQuery();
            int count = set.getMetaData().getColumnCount();
            while (set.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                for (int i = 0; i < count; i++) {
                    String name = set.getMetaData().getColumnLabel(i + 1);
                    map.put(name, set.getObject(name));
                }
                /* 将每行的map存放到 List中 */
                result.add(map);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        } finally {
            closeAll();
        }
        return null;
    }

    /****
     * 对数据库的增、删、改
     * 
     * @param sql sql语句
     * @param ob  可变参数
     * @return 操作完成的sql语句数量
     */
    public static Boolean exeUpdate(String sql) {
        PreparedStatement statement = getStatement(sql);
        // 执行成功的条数
        int count = 0;
        try {
            count = statement.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return count == 0;
    }

    /***
     * 关闭连接 释放资源
     */
    public static void closeAll() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}