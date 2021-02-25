package com.mysqlTest.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

public class BaseDao<T> {
    private QueryRunner queryRunner = new QueryRunner();
    private Class<T> type;

    public BaseDao(){
        Class clazz = this.getClass();
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        type = (Class<T>) types[0];
    }

    public int update(Connection conn, String sql, Object... args) throws Exception{
        int result = queryRunner.update(conn, sql, args);
        return result;
    }

    public T query(Connection conn, String sql, Object... args) throws Exception{
        T t = null;
        t = queryRunner.query(conn,sql,new BeanHandler<T>(type),args);
        return t;
    }

    public List<T> queryList(Connection conn, String sql, Object... args) throws Exception{
        List<T> list = null;
        list = queryRunner.query(conn,sql,new BeanListHandler<>(type),args);
        return list;
    }

    public Object queryValue(Connection conn, String sql, Object... args) throws Exception{
        Object value = null;
        value = queryRunner.query(conn,sql,new ScalarHandler(),args);
        return value;
    }
}
