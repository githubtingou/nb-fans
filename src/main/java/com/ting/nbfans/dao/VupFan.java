package com.ting.nbfans.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vup_fans", indexes = {
        @Index(name = "idx_recordTime_uid", columnList = "record_time"),
        @Index(name = "idx_recordTime_uid", columnList = "uid")
})
public class VupFan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(9) comment '主键'")
    private Integer id;

    @Column(name = "uid", length = 100, columnDefinition = "varchar(100) comment 'uid'")
    private String uid;

    @Column(name = "record_time", length = 8, columnDefinition = "char(8) comment '时间格式：yyyyMMdd'")
    private String recordTime;

    @Column(name = "follower", columnDefinition = "int(9) DEFAULT 0   comment '关注数量'")
    private Integer follower;

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间'")
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP '更新时间'")
    private Date updateTime;

    @Column(name = "captain_num", columnDefinition = "int(9) DEFAULT '0'  comment '舰长数量'")
    private Integer captainNum;

    @Column(name = "final_follower", columnDefinition = "int(9) DEFAULT '0'  comment '昨天关注数量'")
    private Integer finalFollower;

    @Column(name = "final_captain_num", columnDefinition = "int(9) DEFAULT '0'   comment '昨天舰长数量'")
    private Integer finalCaptainNum;

    @Column(name = "fans_group_num", columnDefinition = "int(9) DEFAULT '0'  comment '粉丝团数量'")
    private Integer fansGroupNum;

    @Column(name = "final_fans_group_num", columnDefinition = "int(9) DEFAULT '0'   comment '昨天粉丝团数量'")
    private Integer finalFansGroupNum;

}