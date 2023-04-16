package com.ting.nbfans.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vup")
public class Vup {

    @Id
    @Column(name = "id", columnDefinition = "varchar(64)")
    private String id;

    @Column(name = "live_id", length = 64)
    private String liveId;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updateTime;

}