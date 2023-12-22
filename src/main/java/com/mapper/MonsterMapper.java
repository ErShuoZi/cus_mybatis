package com.mapper;

import com.cusmybatis.entity.Monster;

/**
 *声明数据库的crud方法
 */
public interface MonsterMapper {
    public Monster getMonsterById(Integer id);
}
