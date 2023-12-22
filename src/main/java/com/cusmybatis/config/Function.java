package com.cusmybatis.config;

import org.dom4j.io.SAXReader;

/**
 * 记录接口对应的方法信息
 */
public class Function {
    //
    private String sqlType; //select/update/delete/insert
    private String funcName;//方法名
    private String sql;//sql 语句
    private Object resultType; //返回类型
    private String parameterType;//参数类型

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object getResultType() {
        return resultType;
    }

    public void setResultType(Object resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    @Override
    public String toString() {
        return "Function{" +
                "sqlType='" + sqlType + '\'' +
                ", funcName='" + funcName + '\'' +
                ", sql='" + sql + '\'' +
                ", resultType=" + resultType +
                ", parameterType='" + parameterType + '\'' +
                '}';
    }
}
