package com.cusmybatis.sqlsession;

public interface Executor {
    public <T> T query(String statement, Object parameter);
}
