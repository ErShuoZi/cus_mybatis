package com.cusmybatis.entity;

import lombok.*;

import java.util.Date;

/**
 * 实体类和表有映射的关系
 * 当加入lombok的@Getter注解后就会生成对应的getter方法
 */
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class Monster {
    private Integer id;
    private Integer age;
    private String name;
    private String email;
    private Date birthday;
    private double salary;
    private Integer gender;
}
