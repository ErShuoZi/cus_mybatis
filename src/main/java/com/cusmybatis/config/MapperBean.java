package com.cusmybatis.config;

import java.util.List;

/**
 * 将Mapper信息进行封装
 */
public class MapperBean {
    private String interfaceName; //接口名
    private List<Function> functions; //接口下的所有方法

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    @Override
    public String toString() {
        return "MapperBean{" +
                "interfaceName='" + interfaceName + '\'' +
                ", functions=" + functions +
                '}';
    }
}
