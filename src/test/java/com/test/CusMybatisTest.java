package com.test;

import com.cusmybatis.config.MapperBean;
import com.cusmybatis.entity.Monster;
import com.cusmybatis.sqlsession.*;
import com.mapper.MonsterMapper;
import org.junit.Test;

import java.sql.Connection;


public class CusMybatisTest {
    @Test
    public void build() {
        CusConfiguration cusConfiguration = new CusConfiguration();
        Connection connection = cusConfiguration.build("cus_mybatis.xml");
        System.out.println("connection = " + connection);
    }

    @Test
    public void query() {
        Executor cusExecutor = new CusExecutor();
        Object query = cusExecutor.query("select * from monster where id=?", 1);
        System.out.println(query);
    }

    @Test
    public void selectOne() {
        CusSqlSession cusSqlSession = new CusSqlSession();
        Monster monster = cusSqlSession.SelectOne("select * from monster where age=?", 20);
        System.out.println(monster);
    }

    @Test
    public void readMapper() {
        CusConfiguration cusConfiguration = new CusConfiguration();
        MapperBean mapperBean = cusConfiguration.readMapper("MonsterMapper.xml");
        System.out.println(mapperBean);
    }


    @Test
    public void getMapper() {
        CusSqlSession cusSqlSession = new CusSqlSession();
        MonsterMapper mapper = cusSqlSession.getMapper(MonsterMapper.class);
        Monster monsterById = mapper.getMonsterById(1);
        System.out.println(monsterById);
    }


    @Test
    public void CusSessionFactory() {
        CusSqlSession cusSqlSession = CusSessionFactory.openSession();
        MonsterMapper mapper = cusSqlSession.getMapper(MonsterMapper.class);
        Monster res = mapper.getMonsterById(1);
        System.out.println(res);
    }
}
