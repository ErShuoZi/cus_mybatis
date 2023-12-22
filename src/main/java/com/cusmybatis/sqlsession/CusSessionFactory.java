package com.cusmybatis.sqlsession;

/**
 * 会话工厂返回会话
 */
public class CusSessionFactory {
    public static CusSqlSession openSession() {
        return new CusSqlSession();
    }
}
