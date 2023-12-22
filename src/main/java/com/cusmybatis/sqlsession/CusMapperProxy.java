package com.cusmybatis.sqlsession;

import com.cusmybatis.config.Function;
import com.cusmybatis.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 动态代理生成Mapper对象，调用CusExecutor方法
 */
public class CusMapperProxy implements InvocationHandler {
    private CusSqlSession cusSqlSession;
    private String mapperFile;
    private CusConfiguration cusConfiguration;

    public CusMapperProxy(CusSqlSession cusSqlSession, Class clazz, CusConfiguration cusConfiguration) {
        this.cusSqlSession = cusSqlSession;
        this.cusConfiguration = cusConfiguration;
        this.mapperFile = clazz.getSimpleName() + ".xml";

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = cusConfiguration.readMapper(this.mapperFile);
        //判断是否是xml文件对应的接口
        if (!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())) {
            return null;
        }
        //取出mapbean functions
        List<Function> functions = mapperBean.getFunctions();
        if(functions != null && functions.size() != 0) {
            for (Function function : functions) {
                if (method.getName().equals(function.getFuncName())) {
                    if ("select".equals(function.getSqlType())) {
                        return cusSqlSession.SelectOne(function.getSql(),String.valueOf(args[0]));
                    }
                }
            }
        }
        return null;
    }



}
