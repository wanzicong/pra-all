package com.pra.mysql.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "canal_entity")
@Data
public class CanalEntity implements Serializable {
    private String id;

    private String name;

    private String description;

    private Date createDatetime;

    private Integer age;
}
