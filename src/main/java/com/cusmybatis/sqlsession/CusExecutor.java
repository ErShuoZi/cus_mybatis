package com.cusmybatis.sqlsession;

import com.cusmybatis.entity.Monster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CusExecutor implements Executor {
    // 属性
    private CusConfiguration cusConfiguration = new CusConfiguration();

    @Override
    // 根据sql查询
    public <T> T query(String sql, Object parameter) {
        // 得到连接
        Connection connection = getConnectionByCusConfiguration();
        // 查询返回的结果集
        ResultSet set = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1, parameter.toString());

            set = preparedStatement.executeQuery();
            // 把set数据封装-monster
            Monster monster = new Monster();
            while (set.next()) {
                monster.setId(set.getInt("id"));
                monster.setName(set.getString("name"));
                monster.setEmail(set.getString("email"));
                monster.setAge(set.getInt("age"));
                monster.setBirthday(set.getDate("birthday"));
                monster.setGender(set.getInt("gender"));
                monster.setSalary(set.getDouble("salary"));
            }
            return (T) monster; // 返回查询结果
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Connection getConnectionByCusConfiguration() {
        Connection connection = cusConfiguration.build("cus_mybatis.xml");
        return connection;
    }
}
