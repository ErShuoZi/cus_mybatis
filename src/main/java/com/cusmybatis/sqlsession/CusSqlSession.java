package com.cusmybatis.sqlsession;

import java.lang.reflect.Proxy;

/**
 * 搭建Configuration(连接)和 Executor之间桥梁
 */
public class CusSqlSession {
    private CusExecutor cusExecutor = new CusExecutor();
    private CusConfiguration cusConfiguration = new CusConfiguration();

    //操作DB的方法
    public <T> T SelectOne(String sql, Object parameter) {
        return cusExecutor.query(sql, parameter);
    }

    //返回动态代理对象
    public <T> T getMapper(Class<T> clazz) {
        System.out.println(clazz.getClassLoader());

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new CusMapperProxy(this, clazz, cusConfiguration));
    }
}
