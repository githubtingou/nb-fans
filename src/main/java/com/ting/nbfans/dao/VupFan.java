package com.ting.nbfans.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vup_fans", indexes = {
        @Index(name = "idx_record_time", columnList = "record_time")
})
public class VupFan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(9)")
    private Integer id;

    @Column(name = "uid", length = 100)
    private String uid;

    @Column(name = "record_time", length = 100)
    private String recordTime;

    @Column(name = "follower", columnDefinition = "int(9) comment '关注数量'")
    private Integer follower;

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updateTime;

    @Column(name = "captain_num", columnDefinition = "int(9)  comment '舰长数量'")
    private Integer captainNum;

    @Column(name = "final_follower", columnDefinition = "int(9)  comment '舰长数量'")
    private Integer finalFollower;

    @Column(name = "final_captain_num", columnDefinition = "int(9)  comment '舰长数量'")
    private Integer finalCaptainNum;

}