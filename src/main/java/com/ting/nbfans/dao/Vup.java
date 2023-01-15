package com.ting.nbfans.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "vup")
public class Vup {
    /**
     *
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * uid
     */
    @Column(name = "uid")
    private String uid;

    /**
     *
     */
    @Column(name = "user_name")
    private String userName;

    /**
     *
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     *
     */
    @Column(name = "update_time")
    private Date updateTime;

}

