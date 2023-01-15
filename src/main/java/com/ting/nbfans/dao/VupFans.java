package com.ting.nbfans.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "vup_fans")
public class VupFans {
    /**
     *
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     *
     */
    @Column(name = "uid")
    private String uid;

    /**
     *
     */
    @Column(name = "record_time")
    private String recordTime;

    /**
     *
     */
    @Column(name = "follower")
    private Integer follower;

    /**
     *
     */
    @Column(name = "final_follower")
    private Integer finalFollower;

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

